package com.fisglobal.taxreporting.configuration.security;

import org.springframework.security.core.GrantedAuthority;


/**
 * This enum class holds all the API authorities
 */
public enum Permission implements GrantedAuthority {
    SEARCH_TAXREPORTING,

    DETAILS_TAXREPORTING,

    UPDATE_TAXREPORTING,

    REVIEW_TAXREPORTING,

    APPROVE_TAXREPORTING,

    DELETE_TAXREPORTING,

    ADJUST_TAXREPORTING,

    CREATE_TAXREPORTING,

    CANCEL_TAXREPORTING,

    HISTORICAL_TAXREPORTING,

    MENU,

    /**
     * Permission to see Spring Boot Actuator endpoints, Open-API docs and
     * Swagger-UI
     */
    TECHNICAL_ENDPOINTS;

    @Override
    public String getAuthority() {
        return name();
    }
}
