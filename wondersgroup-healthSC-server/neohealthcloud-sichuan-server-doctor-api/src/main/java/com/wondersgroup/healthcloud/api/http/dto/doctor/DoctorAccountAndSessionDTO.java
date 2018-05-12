package com.wondersgroup.healthcloud.api.http.dto.doctor;

import com.wondersgroup.healthcloud.common.http.support.misc.SessionDTO;
import com.wondersgroup.healthcloud.services.doctor.entity.DoctorAccountInfoAndSession;
import com.wondersgroup.healthcloud.services.doctor.entity.DoctorInfo;

/**
 * Created by longshasha on 16/8/1.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorAccountAndSessionDTO {
    public DoctorInfo doctorInfo;
    public SessionDTO session;

    public DoctorAccountAndSessionDTO(DoctorAccountInfoAndSession doctorAccountInfoAndSession) {
        this.doctorInfo = doctorAccountInfoAndSession.doctorInfo;
        if (doctorAccountInfoAndSession.session != null) {
            this.session = new SessionDTO(doctorAccountInfoAndSession.session);
        }
    }

}