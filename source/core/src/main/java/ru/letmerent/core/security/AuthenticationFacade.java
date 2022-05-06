package ru.letmerent.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    @Override
    public String getLogin() {
        return getAuthentication().getName();
    }
    
    @Override
    public boolean isAdmin() {
        return getAuthentication().getAuthorities().toString().contains("ADMIN");
    }
}
