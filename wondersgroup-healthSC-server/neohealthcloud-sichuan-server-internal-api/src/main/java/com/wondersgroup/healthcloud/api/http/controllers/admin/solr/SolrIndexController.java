package com.wondersgroup.healthcloud.api.http.controllers.admin.solr;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.solr.document.SolrHospital;
import com.wondersgroup.healthcloud.solr.service.index.SolrHospitalService;

@RestController
@RequestMapping("/api/solr")
@Admin
public class SolrIndexController {

    @Autowired
    SolrHospitalService solrHospitalService;

    @RequestMapping(value = "/hospital/fullImport", method = RequestMethod.GET)
    public JsonResponseEntity<String> hospitalFullImport() {
        JsonResponseEntity<String> responseEntity = new JsonResponseEntity<String>();
        solrHospitalService.fullImport();
        responseEntity.setMsg("OK");
        return responseEntity;
    }

    @RequestMapping(value = "/hospital/deltaImport", method = RequestMethod.GET)
    public JsonResponseEntity<String> hospitalDeltaImport() {
        JsonResponseEntity<String> responseEntity = new JsonResponseEntity<String>();
        solrHospitalService.deltaImport();
        responseEntity.setMsg("OK");
        return responseEntity;
    }

    @RequestMapping(value = "/hospital/import", method = RequestMethod.GET)
    public JsonResponseEntity<String> doctorImport(@RequestBody SolrHospital hospital) {
        JsonResponseEntity<String> responseEntity = new JsonResponseEntity<String>();
        solrHospitalService.save(hospital);
        responseEntity.setMsg("OK");
        return responseEntity;
    }

}
