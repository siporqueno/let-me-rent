package ru.letmerent.core.security;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    
    Authentication getAuthentication();
    
    String getLogin();
    
    boolean isAdmin();
}
