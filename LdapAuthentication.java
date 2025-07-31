package com.fisglobal.taxreporting.controller.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;


/**
 * This class holds all the LdapAuthentication fields
 */
public class LdapAuthentication extends AuthenticationResponse {
    private Boolean login = Boolean.FALSE;

    private String username;

    private Collection<? extends GrantedAuthority> authorities;

    LdapAuthentication(Boolean login, String username, Collection<? extends GrantedAuthority> authorities) {
        this.login = login;
        this.username = username;
        this.authorities = authorities;
    }

    public LdapAuthentication() {
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
