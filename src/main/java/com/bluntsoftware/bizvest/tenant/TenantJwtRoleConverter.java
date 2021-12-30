package com.bluntsoftware.bizvest.tenant;

import com.bluntsoftware.bizvest.saasy.SaasyConfig;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TenantJwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return getRoles(jwt).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public List<String> getRoles(Jwt jwt){
        Map<String, Object> claims = jwt.getClaims();
        System.out.println(SaasyConfig.SASSY_URI);
        System.out.println(SaasyConfig.SASSY_APP_ID);
        System.out.println(jwt.getTokenValue());
        return  new ArrayList<>();
    }
}

