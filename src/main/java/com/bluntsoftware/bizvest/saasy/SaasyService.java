package com.bluntsoftware.bizvest.saasy;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SaasyService {

    public static List<NameId> listMyTenants(){
        return listMyTenants(getAccessToken());
    }

    public static List<NameId> listMyTenants(String token){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> params = new HashMap<>();
        params.put("appId",SaasyConfig.SASSY_APP_ID);
        String customerAPIUrl = SaasyConfig.SASSY_URI + "/rest/tenant-user/my-tenants";
        HttpEntity<Map<String,String>> request = new HttpEntity<>(params,buildHeaders(token));
        ParameterizedTypeReference<List<NameId>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<NameId>> customerEntity = restTemplate.exchange(customerAPIUrl, HttpMethod.POST, request, responseType );
        return customerEntity.getBody();
    }

    public static TenantUser getTenantUser(String token){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> params = new HashMap<>();
        params.put("appId",SaasyConfig.SASSY_APP_ID);
        params.put("tenantId",getTenantId(token));
        String customerAPIUrl = SaasyConfig.SASSY_URI + "/rest/tenant-user/me";
        HttpEntity<Map<String,String>> request = new HttpEntity<>(params,buildHeaders(token));
        ResponseEntity<TenantUser> customerEntity = restTemplate.exchange(customerAPIUrl, HttpMethod.POST, request, TenantUser.class );
        return customerEntity.getBody();
    }

    private static HttpHeaders buildHeaders(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token + "SASSYAPPID" + SaasyConfig.SASSY_APP_ID);
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
    public static String getTenantId() {
        return getTenantId(getAccessToken());
    }
    public static String getTenantId(String token) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null){
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String tenantId = request.getHeader("TENANT_KEY");
            if(tenantId != null && !tenantId.isEmpty()){
                return tenantId;
            }
        }
        List<NameId> list = SaasyService.listMyTenants(token);
        if(list != null && list.size() > 0){
            return list.get(0).getId();
        }
        throw new RuntimeException("Tenant Not Found");
    }

    public static String getAccessToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if (auth instanceof AbstractOAuth2TokenAuthenticationToken) {
            AbstractOAuth2TokenAuthenticationToken tok = (AbstractOAuth2TokenAuthenticationToken)auth ;
            return tok.getToken().getTokenValue();
        }
        return null;
    }

}
