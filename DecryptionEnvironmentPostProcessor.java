package com.fisglobal.taxreporting.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import de.kordoba.framework.common.log.KORLogger;
import de.kordoba.framework.crypto.KorCryptoFileAccessException;
import de.kordoba.framework.crypto.KorCryptoGeneralException;
import de.kordoba.framework.crypto.KorCryptoNoValueFoundException;
import de.kordoba.framework.crypto.KorDecrypter;


/**
 * This class is used to integrate the FIS "kordecrypt-lib". Since there is no
 * official documentation on how to use the library, you can fortunately contact
 * Michael Renz if you have further questions.
 *
 * @author Stefan Corban
 */
public class DecryptionEnvironmentPostProcessor implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private static final KORLogger LOGGER = KORLogger.getLogger(DecryptionEnvironmentPostProcessor.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();

        String cryptoFilePath = getCryptoFilePath();

        Map<String, Object> decryptedProperties = new HashMap<String, Object>();
        decryptedProperties.put("spring.security.user.password", UUID.randomUUID().toString());

        Map<String, Object> encryptedProperties = new HashMap<String, Object>();
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            if (propertySource instanceof MapPropertySource) {
                encryptedProperties.putAll(((MapPropertySource) propertySource).getSource());
            }
        }

        for (Map.Entry<String, Object> entry : encryptedProperties.entrySet()) {
            try {
                String decryptedValue = KorDecrypter.decrypt(entry.getValue().toString(), cryptoFilePath);
                decryptedProperties.put(entry.getKey(), decryptedValue);
            } catch (KorCryptoNoValueFoundException e) {
                // do nothing here - obviously it's an unencrypted value
            } catch (KorCryptoFileAccessException | KorCryptoGeneralException e) {
                LOGGER.error("Could not decrypt property '" + entry.getKey() + "/" + entry.getValue()
                        + "' using kordecrypt-lib.", e);
                throw new RuntimeException("Could not decrypt property '" + entry.getKey() + "/" + entry.getValue()
                        + "' using kordecrypt-lib.");
            }
        }

        PropertySource<?> propertySource = new MapPropertySource("kordecrypt", decryptedProperties);
        environment.getPropertySources().addFirst(propertySource);
    }

    /**
     * The key {@code de.kordoba.crypto.file} to read the path for the crypto file
     * is had-wired in "kordecrypt-lib".
     * <p>
     * We will have to use System.getenv(...) for reading the path, because Spring
     * Boot has not yet set up the environment at the time this code is executed. So
     * we cannot simply inject the value with {@code @Value}.
     *
     * @return path to crypto file as String
     */
    private String getCryptoFilePath() {
        String cryptoFilePath = System.getProperty("de.kordoba.crypto.file");
        if (StringUtils.isBlank(cryptoFilePath)) {
            LOGGER.warn(
                    "Could not find settings for crypto properties file, so I will disable encrypted properties. ATTENTION: If this service runs in production mode, fix this: add '-Dde.kordoba.crypto.file=/path/to/crypto.properties'");

            cryptoFilePath = "crypto.properties";
        }
        return cryptoFilePath;
    }
}
