package com.wondersgroup.healthcloud.services.evaluate;

import com.wondersgroup.healthcloud.jpa.entity.evaluate.EvaluateDoctor;
import com.wondersgroup.healthcloud.services.evaluate.dto.AppEvaluateDoctorListDTO;
import com.wondersgroup.healthcloud.services.evaluate.dto.EvaluateDoctorDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by longshasha on 16/11/2.
 */
public interface EvaluateDoctorService {

    List<EvaluateDoctorDTO> findEvaluateDoctorListByPager(int pageNum, int size, Map parameter);

    int countEvaluateDoctorListByPager(Map parameter);

    /**
     * 返回该医生审核通过的评价列表
     *
     * @return 返回数据为pageSize+1条数据
     */
    List<AppEvaluateDoctorListDTO> findValidListByDoctorId(int doctorId, int page, int pageSize);

    void publishEvaluateDoctor(EvaluateDoctor evaluateDoctor);

    Boolean setIsTopStatusById(Integer isTop, Integer id);

    /**
     * 医院有效评价数
     */
    int countValidEvaluateDoctor(int doctorId);

}
