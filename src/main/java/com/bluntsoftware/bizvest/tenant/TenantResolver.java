package com.bluntsoftware.bizvest.tenant;

import com.bluntsoftware.bizvest.saasy.NameId;
import com.bluntsoftware.bizvest.saasy.SaasyService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import java.util.List;

public class TenantResolver {

    public static String resolve(){
        List<NameId> list = SaasyService.listMyTenants();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null){
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String tenantId = request.getHeader("tenant");
            if(tenantId != null && !tenantId.isEmpty()){
                list = list.stream().filter(n->n.getId().equalsIgnoreCase(tenantId)).collect(Collectors.toList());
            }
        }

        if(list != null && list.size() > 0){
            return list.get(0).getId();
        }
        return "all";
    }
}
