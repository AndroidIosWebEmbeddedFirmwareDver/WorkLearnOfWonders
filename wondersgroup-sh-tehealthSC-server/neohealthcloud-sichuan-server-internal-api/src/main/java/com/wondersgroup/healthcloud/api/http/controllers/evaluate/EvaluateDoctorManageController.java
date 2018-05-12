package com.wondersgroup.healthcloud.api.http.controllers.evaluate;

import com.wondersgroup.healthcloud.api.utils.Pager;
import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonKeyReader;
import com.wondersgroup.healthcloud.jpa.entity.evaluate.EvaluateDoctor;
import com.wondersgroup.healthcloud.jpa.repository.evaluate.EvaluateDoctorRepository;
import com.wondersgroup.healthcloud.services.evaluate.EvaluateDoctorService;
import com.wondersgroup.healthcloud.services.evaluate.dto.EvaluateDoctorDTO;
import com.wondersgroup.healthcloud.services.evaluate.exception.ErrorEvaluateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by longshasha on 16/11/2.
 */
@RestController
@RequestMapping("/admin/evaluate/doctor")
public class EvaluateDoctorManageController {

    @Autowired
    private EvaluateDoctorService evaluateDoctorService;

    @Autowired
    private EvaluateDoctorRepository evaluateDoctorRepository;

    /**
     * 用户对医生的评价列表
     *
     * @param pager
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Admin
    public Pager findDoctorEvaluateList(@RequestBody Pager pager) {
        int pageNum = 1;
        if (pager.getNumber() != 0)
            pageNum = pager.getNumber();

        List<EvaluateDoctorDTO> evaluateDoctorList =
                evaluateDoctorService.findEvaluateDoctorListByPager(pageNum, pager.getSize(), pager.getParameter());

        int totalSize = evaluateDoctorService.countEvaluateDoctorListByPager(pager.getParameter());
        pager.setTotalElements(totalSize);
        pager.setData(evaluateDoctorList);
        return pager;
    }

    /**
     * 批量删除(批量设置成审核不通过)
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @Admin
    public JsonResponseEntity batchDelete(@RequestBody List<Integer> ids) {
        JsonResponseEntity<String> response = new JsonResponseEntity<>();
        if (ids != null && ids.size() > 0) {
            Integer status = 2;
            evaluateDoctorRepository.batchSetStatuByIds(status, ids);
            response.setMsg("删除成功");
        } else {
            response.setCode(3010);
            response.setMsg("删除失败");
        }
        return response;
    }


    /**
     * status=1是通过,statue=2是删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @Admin
    public JsonResponseEntity updateStatus(@RequestBody String request) {
        JsonResponseEntity<String> response = new JsonResponseEntity<>();
        JsonKeyReader reader = new JsonKeyReader(request);
        Integer id = reader.readObject("id", true, Integer.class);
        Integer status = reader.readObject("status", true, Integer.class);
        if (status != 1) {
            evaluateDoctorService.setIsTopStatusById(0, id);
        }
        evaluateDoctorRepository.setStatuById(status, id);
        response.setMsg("保存成功");
        return response;
    }

    /**
     * 置顶/取消置顶
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateIsTop", method = RequestMethod.POST)
    @Admin
    public JsonResponseEntity updateIsTop(@RequestBody String request) {
        JsonResponseEntity<String> response = new JsonResponseEntity<>();
        JsonKeyReader reader = new JsonKeyReader(request);
        Integer id = reader.readObject("id", true, Integer.class);
        Integer isTop = reader.readObject("isTop", true, Integer.class);

        if (isTop == 1) {
            EvaluateDoctor evaluateDoctor = evaluateDoctorRepository.getOne(id);
            if (evaluateDoctor.getStatus() != 1) {
                throw new ErrorEvaluateException("只有审核通过的可以置顶");
            }
        }

        evaluateDoctorService.setIsTopStatusById(isTop, id);
        response.setMsg("保存成功");
        return response;
    }


}
