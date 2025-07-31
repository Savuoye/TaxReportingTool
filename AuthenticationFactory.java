package com.fisglobal.taxreporting.controller.authentication;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import de.kordoba.framework.common.log.KORLogger;


/**
 * Authentication factory will created authentication and authorization for LDAP
 * and JWT
 */
public class AuthenticationFactory {
    private static final KORLogger LOG = KORLogger.getLogger(AuthenticationFactory.class);

    /**
     * Authentication instance will be intialized for LDAP or JWT authorization
     *
     * @param authenticationType
     *                               Specifies the authentication type
     * 
     * @return Authentication response
     */
    public static AuthenticationResponse getAuthenticationResponse(String authenticationType) {
        LOG.trace("[AuthenticationFactory] [getAuthenticationResponse] Enter into authentication process");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        AuthenticationResponse authenticationResponse = null;

        switch (authenticationType) {
            case "LDAP":
                authenticationResponse = new LdapAuthentication();
                if (authentication != null && authentication.isAuthenticated()
                        && !"anonymousUser".equals(authentication.getPrincipal())) {
                    authenticationResponse = new LdapAuthentication(Boolean.TRUE, username, authorities);
                }
                break;

            case "JWT":
                authenticationResponse = new JwtAuthentication();
                if (authentication != null && authentication.isAuthenticated()
                        && !"anonymousUser".equals(authentication.getPrincipal())) {
                    authenticationResponse = new JwtAuthentication(Boolean.TRUE, username, authorities);
                }
                break;
        }
        LOG.trace("[AuthenticationFactory] [getAuthenticationResponse] Exit authentication process");
        return authenticationResponse;
    }
}
