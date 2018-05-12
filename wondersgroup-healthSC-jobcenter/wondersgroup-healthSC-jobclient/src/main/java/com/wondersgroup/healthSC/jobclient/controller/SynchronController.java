package com.wondersgroup.healthSC.jobclient.controller;

import com.wondersgroup.common.utils.JsonKeyReader;
import com.wondersgroup.healthSC.common.json.JsonResponseEntity;
import com.wondersgroup.healthSC.services.interfaces.DepartmentService;
import com.wondersgroup.healthSC.services.interfaces.DoctorService;
import com.wondersgroup.healthSC.services.interfaces.HospitalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuchunliu on 2016/11/2.
 */
@RestController
@RequestMapping("/synchron")
public class SynchronController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DoctorService doctorService;

    @RequestMapping(value="/hospital",method = RequestMethod.POST)
    public JsonResponseEntity synchronHospital(@RequestBody String request){
        JsonKeyReader reader = new JsonKeyReader(request);
        String ids =  reader.readString("ids", true);
        if(StringUtils.isEmpty(ids)){
            hospitalService.synchronInfo();
        }else{
            hospitalService.synchronInfo(ids.split(","));
        }

        return new JsonResponseEntity(0,"同步医院信息成功");
    }

    @RequestMapping(value="/department",method = RequestMethod.POST)
    public JsonResponseEntity synchronDepartment(){
        departmentService.synchronInfo();
        return new JsonResponseEntity(0,"同步科室信息成功");
    }

    @RequestMapping(value="/doctor",method = RequestMethod.POST)
    public JsonResponseEntity synchronDoctor(@RequestBody String request){
        JsonKeyReader reader = new JsonKeyReader(request);
        String ids =  reader.readString("ids", true);
        if(StringUtils.isEmpty(ids)){
            doctorService.synchronInfo();
        }else{
            doctorService.synchronInfo(ids.split(","));
        }
        return new JsonResponseEntity(0,"同步医生信息成功");
    }

}

