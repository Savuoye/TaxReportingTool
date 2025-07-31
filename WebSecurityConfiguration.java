package com.fisglobal.taxreporting.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import com.fisglobal.kgs.wp_auth.common.WPAuthWebSecurityConfiguration;
import com.fisglobal.taxreporting.util.CommonConstants;

import de.kordoba.framework.common.log.KORLogger;


/**
 * Enables Spring Security and authorization
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration {
    private static final KORLogger LOG = KORLogger.getLogger(WebSecurityConfiguration.class);
    /**
     * The list of properties will be accessed without any authorization
     */
    @Value("${taxreporting.authorization.whitelist.urls:/v3/api-docs/**}")
    private String[] authorizationWhiteListUrls;

    @Autowired
    WPAuthWebSecurityConfiguration wpAuthWebSecurityConfiguration;

    @Autowired
    @Qualifier("customAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;

    @Value("${wpauth.authentication.method:LDAP}")
    private String authenticationType;

    /**
     * This method will define the authorization. Http instance will control the
     * authorization process from http request.
     *
     * @param http
     *                 http instance for security configuration
     *
     * @return kordoba web security configuration
     * 
     * @throws Exception
     *                       throws unauthorized exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LOG.info("[WebSecurityConfiguration] [filterChain] Enabled authentication type : {}", authenticationType);

        if (CommonConstants.LOGIN_TYPE_JWT.equals(authenticationType)) {
            http.authorizeHttpRequests().requestMatchers("/jstb-correction/v1/**").permitAll().and()
                    .exceptionHandling(auth -> auth.authenticationEntryPoint(authEntryPoint));
        } else {
            http.httpBasic(basic -> basic.authenticationEntryPoint(authEntryPoint)).authorizeHttpRequests()
                    .requestMatchers(authorizationWhiteListUrls).permitAll();
        }

        return wpAuthWebSecurityConfiguration.filterChain(http).build();
    }
}
