package com.wondersgroup.healthcloud.services.evaluate;

import com.wondersgroup.healthcloud.jpa.entity.evaluate.EvaluateHospital;
import com.wondersgroup.healthcloud.services.evaluate.dto.AppEvaluateHospitalListDTO;
import com.wondersgroup.healthcloud.services.evaluate.dto.EvaluateHospitalDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by longshasha on 16/11/2.
 */
public interface EvaluateHospitalService {

    List<EvaluateHospitalDTO> findEvaluateHospitalListByPager(int pageNum, int size, Map parameter);

    /**
     * 返回该医院审核通过的评价列表
     *
     * @return 返回数据为pageSize+1条数据
     */
    List<AppEvaluateHospitalListDTO> findValidListByHospitalId(int hospitalId, int page, int pageSize);

    /**
     * 医院有效评价数
     */
    int countValidEvaluateHospital(int hospitalId);

    int countEvaluateHospitalListByPager(Map parameter);

    void publishEvaluateHospital(EvaluateHospital evaluateHospital);

    Boolean setIsTopStatusById(Integer isTop, Integer id);

}
