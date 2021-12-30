package com.bluntsoftware.bizvest.tenant;

import com.bluntsoftware.bizvest.saasy.SaasyService;

public class TenantResolver {
    public static String resolve(){
        return SaasyService.getTenantId();
    }
}
