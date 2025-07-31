package com.fisglobal.taxreporting.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fisglobal.kgs.wp_auth.ldap.Ldap;
import com.fisglobal.kgs.wp_auth.ldap.LdapAndRolePermissionProperties;


/**
 * This class used to configure LDAP roles and its permissions
 */
@Configuration
public class LdapAndRolePermissionConfiguration {
    @Autowired
    LdapConfiguration ldapConfiguration;

    /**
     * Initilaize LDAP bean using ldapConfiguration properties and assign LDAP
     * instance to LdapAndRolePermissionProperties class
     *
     * @return It return LdapAndRolePermissionProperties instance
     */
    @Bean
    public LdapAndRolePermissionProperties ldapConfigurationProperties() {
        Ldap ldap = new Ldap(ldapConfiguration.getDnPatterns(), ldapConfiguration.getManagerDn(),
                ldapConfiguration.getManagerPassword(), ldapConfiguration.getSearchBase(),
                ldapConfiguration.getTimeToRefreshAuthentication(), ldapConfiguration.getUrl(),
                ldapConfiguration.getConnectTimeout());
        return new LdapAndRolePermissionProperties(ldap, ldapConfiguration.getRolePermissions(),
                ldapConfiguration.getRolePrefix());
    }
}
