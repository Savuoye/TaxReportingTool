package com.fisglobal.taxreporting.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fisglobal.taxreporting.interceptor.LoggingInterceptor;

import de.kordoba.framework.common.log.KORLogger;


@Configuration
@Profile("localx")
public class MvcConfig implements WebMvcConfigurer {
    private static final KORLogger LOGGER = KORLogger.getLogger(MvcConfig.class);

    @Autowired
    LoggingInterceptor yourInjectedInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        if (this.yourInjectedInterceptor != null) {
            // registry.addInterceptor(this.yourInjectedInterceptor);
        }
    }
}
