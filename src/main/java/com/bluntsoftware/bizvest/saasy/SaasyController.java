package com.bluntsoftware.bizvest.saasy;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/rest/saasy")
public class SaasyController {

    @GetMapping(value = "/my-tenants",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NameId> listMyTenants(){
        return SaasyService.listMyTenants();
    }

}
