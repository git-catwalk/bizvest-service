package com.bluntsoftware.bizvest.service;


import com.bluntsoftware.bizvest.model.User;
import com.bluntsoftware.bizvest.tenant.TenantJwtRoleConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserInfoService {

    public User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal instanceof Jwt) {
            return getUser((Jwt)principal);
        }
        return null;
    }

    public Collection<GrantedAuthority> getRoles(Jwt jwt){
        TenantJwtRoleConverter converter = new TenantJwtRoleConverter();
        return converter.convert(jwt);
    }

    public User getUser(Jwt jwt){
        return User.builder()
                .username(jwt.getClaimAsString("preferred_username"))
                .email(jwt.getClaimAsString("email"))
                .name(jwt.getClaimAsString("name"))
                .roles(getRoles(jwt).stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())).build();
    }

}
