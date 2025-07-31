package com.fisglobal.taxreporting.configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.servers.ServerVariable;
import io.swagger.v3.oas.models.servers.ServerVariables;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.kordoba.framework.common.log.KORLogger;


/**
 * Swagger docs will be configured and OpenAPI will be instantiated
 */
@Configuration
public class OpenApiConfiguration {
    private static final KORLogger log = KORLogger.getLogger(OpenApiConfiguration.class);

    @Value("${server.port:18081}")
    private String serverPort;

    @Value("${server.servlet.context-path:/api/taxreporting/}")
    private String contextPath;

    /**
     * Open API bean will be configured using static details. Server details will be
     * fetched from InetAddress class.
     *
     * @return Open API object contains all the details and configuration
     */
    @Bean
    public OpenAPI api() {
        String httpUrl = "";
        String httpsUrl = "";
        String environment = "localhost";
        try {
            environment = InetAddress.getLocalHost().getHostName();
            httpUrl = constructUrl(environment, "http://");
            httpsUrl = constructUrl(environment, "https://");
        } catch (UnknownHostException e) {
            log.error("Error occurred in swagger config ", e);
        }
        return new OpenAPI().info(new Info().title("api_taxreportingtool").description("APIs for Tax Reporting Tool")
                .contact(new Contact().email("DE_taxreporting@fisglobal.com")).version("v1")
                .license(new License()
                        .name("FIS COPYRIGHT (C) 2019 Fidelity National Information Services, Inc. and/or\r\n"
                                + "      its subsidiaries - All Rights Reserved worldwide. This document is\r\n"
                                + "      protected under the trade secret and copyright laws as the property of\r\n"
                                + "      Fidelity National Information Services, Inc. and/or its subsidiaries.\r\n"
                                + "      Copying, reproduction or distribution should be limited and only to\r\n"
                                + "      employees with a ( need to know ) to do their job. Any disclosure of this\r\n"
                                + "      document to third parties is strictly prohibited.")))
                .addServersItem(new Server().url(httpUrl)
                        .variables(new ServerVariables()
                                .addServerVariable("port", new ServerVariable()._default(serverPort))
                                .addServerVariable("environment", new ServerVariable()._default(environment))))
                .addServersItem(new Server().url(httpsUrl)
                        .variables(new ServerVariables()
                                .addServerVariable("port", new ServerVariable()._default(serverPort))
                                .addServerVariable("environment", new ServerVariable()._default(environment))))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components(new Components().addSecuritySchemes("basicAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));
    }

    /**
     * This method will construct the URL by passing host details
     *
     * @param environment
     *                        Host details
     * @param scheme
     *                        http or https
     * 
     * @return url string
     */
    private String constructUrl(String environment, String scheme) {
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(scheme);
        urlBuffer.append(environment);
        urlBuffer.append(":");
        urlBuffer.append(serverPort);
        urlBuffer.append(contextPath);
        return urlBuffer.toString();
    }
}
