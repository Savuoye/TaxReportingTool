package com.fisglobal.taxreporting.service.infrastructure;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * This implementation class used to get the logged user name
 */
@Service
public class UserNameProviderImpl implements UserNameProvider {
    /**
     * This method used to get the logged username from authentication class
     *
     * @return User name
     */
    public String getAuthenticatedUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (authentication != null) ? authentication.getName() : "";
    }
}
