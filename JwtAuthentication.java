package com.fisglobal.taxreporting.controller.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;


/**
 * This class holds all the JwtAuthentication fields
 */
public class JwtAuthentication extends AuthenticationResponse {
    private Boolean login = Boolean.FALSE;

    private String username;

    private Collection<? extends GrantedAuthority> authorities;

    JwtAuthentication(Boolean login, String username, Collection<? extends GrantedAuthority> authorities) {
        this.login = login;
        this.username = username;
        this.authorities = authorities;
    }

    public JwtAuthentication() {
    }

    @Override
    public Boolean getLogin() {
        return this.login;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}
