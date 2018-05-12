package com.wondersgroup.healthcloud.api.http.controllers.article;

import com.wondersgroup.healthcloud.api.utils.Pager;
import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.jpa.entity.article.ForwardArticle;
import com.wondersgroup.healthcloud.services.article.ForwardArticleService;
import com.wondersgroup.healthcloud.services.article.dto.ForwardArticleAPIEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dukuanxin on 2016/8/25.
 */
@RestController
@RequestMapping("/back/home/article")
public class ForwardArticleController {

    @Autowired
    private ForwardArticleService forwardArticleService;

    @PostMapping("/list")
    public Pager findHomeArticleList(@RequestBody Pager pager) {
        Map param = new HashMap();
        param.putAll(pager.getParameter());

        if (StringUtils.isEmpty(param.get("mainArea"))) {
            param.put("mainArea", "5101");//默认5101(四川区域代码)
        }

        String areaCode = param.get("mainArea").toString();
        if (param.containsKey("articleId") && !StringUtils.isEmpty(param.get("articleId"))) {
            String id = (String) param.get("articleId");
            List<ForwardArticleAPIEntity> forwardArticle = forwardArticleService.queryById(id, areaCode);
            if (!forwardArticle.isEmpty()) {
                pager.setData(forwardArticle);
                pager.setTotalElements(forwardArticle.size());
            }
            return pager;
        }
        int pageNo = pager.getNumber();
        int pageSize = pager.getSize();
        String status = "0";//默认查全部
        if (null != param.get("status")) {
            status = (String) param.get("status");
        }
        pager.setData(forwardArticleService.queryPageForWardArticle(status, pageNo, pageSize, areaCode));
        int total = forwardArticleService.getCount(status, areaCode);
        int totalPage = total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1;
        pager.setTotalElements(total);
        pager.setTotalPages(totalPage);
        return pager;
    }

    @Admin
    @PostMapping("/save")
    public JsonResponseEntity save(@RequestBody ForwardArticle forwardArticle) {
        JsonResponseEntity response = new JsonResponseEntity();

        if (StringUtils.isEmpty(forwardArticle.getMain_area())) {
            forwardArticle.setMain_area("5101");//默认5101(四川区域代码)
        }
        int existCount = forwardArticleService.getExistCount(forwardArticle.getArticle_id(), forwardArticle.getMain_area());
        if (existCount >= 1) {
            response.setCode(1000);
            response.setMsg("该资讯已经存在");
            return response;
        }
        forwardArticleService.updateForwardArticle(forwardArticle);
        response.setMsg("成功");
        return response;
    }

    @Admin
    @PostMapping("/update")
    public JsonResponseEntity update(@RequestBody ForwardArticle forwardArticle) {
        JsonResponseEntity response = new JsonResponseEntity();

        if (StringUtils.isEmpty(forwardArticle.getMain_area())) {
            forwardArticle.setMain_area("5101");//默认5101(四川区域代码)
        }

        if (!StringUtils.isEmpty(forwardArticle.getId())) {
            long time = forwardArticle.getEnd_time().getTime();
            long nowTime = new Date().getTime();
            if ((nowTime + 24 * 3600 * 1000) > time) {
                String endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(time + 48 * 3600 * 1000));
                forwardArticle.setEnd_time(endTime);
            }
        }
        forwardArticleService.updateForwardArticle(forwardArticle);
        response.setMsg("成功");
        return response;
    }

    @GetMapping("/detail")
    public JsonResponseEntity getHomePageArticle(int id) {
        JsonResponseEntity response = new JsonResponseEntity();
        Object homePageArticle = forwardArticleService.getHomePageArticle(id);
        response.setData(homePageArticle);
        return response;
    }

}
