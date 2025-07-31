/**
 *
 */
package com.fisglobal.taxreporting.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import com.fisglobal.taxreporting.configuration.security.Permission;


/**
 * LDAP configuration class used to map the application properties
 */
@Configuration
@ConfigurationProperties(prefix = "taxreporting.ldap")
@Validated
public class LdapConfiguration {
    /**
     * Username (DN) of the "manager" user identity (i.e. "uid=admin,ou=system")
     * which will be used to authenticate to a (non-embedded) LDAP server
     */
    private String managerDn;

    /** The password for the manager DN */
    private String managerPassword;

    /**
     * Ldap url of the server.
     */
    private String url;

    /**
     * LDAP fail over timeout in milliseconds.
     */
    private Long connectTimeout = (long) 5000;

    /**
     * Ldap searchBase of the server.
     */
    private String searchBase;

    /**
     * Ldap rolePrefix of the server.
     */
    private String rolePrefix;

    /**
     * Cache timeToRefreshAuthentication of the server.
     */
    private long timeToRefreshAuthentication;

    private String dnPatterns;

    private Map<String, List<String>> rolePermissions;

    public String getManagerDn() {
        return managerDn;
    }

    public void setManagerDn(String managerDn) {
        this.managerDn = managerDn;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *                the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the connectTimeout
     */
    public Long getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * @param connectTimeout
     *                           the connectTimeout to set in milliseconds
     */
    public void setConnectTimeout(Long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * @return the searchBase
     */
    public String getSearchBase() {
        return searchBase;
    }

    /**
     * @param searchBase
     *                       the searchBase to set
     */
    public void setSearchBase(String searchBase) {
        this.searchBase = searchBase;
    }

    /**
     * @return the rolePrefix
     */
    public String getRolePrefix() {
        return rolePrefix;
    }

    /**
     * @param rolePrefix
     *                       the rolePrefix to set
     */
    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    /**
     * @return the timeToRefreshAuthentication
     */
    public long getTimeToRefreshAuthentication() {
        return timeToRefreshAuthentication;
    }

    /**
     * @param timeToRefreshAuthentication
     *                                        the timeToRefreshAuthentication to set
     */
    public void setTimeToRefreshAuthentication(long timeToRefreshAuthentication) {
        this.timeToRefreshAuthentication = timeToRefreshAuthentication;
    }

    public String getDnPatterns() {
        return dnPatterns;
    }

    public void setDnPatterns(String dnPatterns) {
        this.dnPatterns = dnPatterns;
    }

    public Map<String, List<String>> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(Map<String, List<String>> rolePermissions) {
        if (rolePermissions != null) {
            this.rolePermissions = new HashMap<String, List<String>>();
            rolePermissions.forEach((role, permissions) -> {
                List<String> upperCasePermissions = new ArrayList<>(permissions.size());
                permissions.forEach(permission -> {

                    if (EnumUtils.isValidEnum(Permission.class, permission.toUpperCase())) {
                        upperCasePermissions.add(permission.toUpperCase());
                    } else {
                        throw new RuntimeException("Unknown Permission '" + permission + "' for role '" + role
                                + "' detected. Please check rolePermission mapping.");
                    }
                });
                this.rolePermissions.put(role.toUpperCase(), upperCasePermissions);
            });
        }
    }
}
