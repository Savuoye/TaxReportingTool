package com.fisglobal.taxreporting.service.infrastructure;

/**
 * Interface used to get the authorized user name
 */
public interface UserNameProvider {
    public String getAuthenticatedUserName();
}
