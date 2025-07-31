package com.fisglobal.taxreporting.controller.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;


/**
 * Abstract class will holds username, authorities and login status
 */
public abstract class AuthenticationResponse {
    public abstract Boolean getLogin();

    public abstract String getUsername();

    public abstract Collection<? extends GrantedAuthority> getAuthorities();
}
