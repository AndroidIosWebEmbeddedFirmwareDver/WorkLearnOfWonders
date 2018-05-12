package com.wondersgroup.healthcloud.api.http.controllers.help;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.wondersgroup.healthcloud.api.utils.MapToBeanUtil;
import com.wondersgroup.healthcloud.api.utils.PropertyFilterUtil;
import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.jpa.entity.help.HelpCenter;
import com.wondersgroup.healthcloud.jpa.repository.help.HelpCenterRepository;
import com.wondersgroup.healthcloud.services.help.HelpCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class HelpCenterController {

    @Autowired
    private HelpCenterService helpCenterService;
    @Autowired
    private HelpCenterRepository helpCenterRepository;

    /**
     * 查询帮助中心app
     *
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "helpCenter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findHelpCenter() throws JsonProcessingException {
        List<HelpCenter> helpCenterList = helpCenterService.findByIsVisable("0");

        Map<Class, Object> filterMap = new HashMap<>();
        filterMap.put(HelpCenter.class, new String[]{"title", "content"});
        SimpleFilterProvider filterProvider = PropertyFilterUtil.filterOutAllExceptFilter(filterMap);

        if (helpCenterList != null && !helpCenterList.isEmpty()) {
            return PropertyFilterUtil
                    .getObjectMapper()
                    .setFilterProvider(filterProvider)
                    .writeValueAsString(new JsonResponseEntity(0, "查询成功", helpCenterList));
        } else {
            ObjectNode objectNode = JsonNodeFactory.instance.objectNode().put("info", 0);
            return new JsonResponseEntity(-1, "无数据", objectNode).toString();
        }
    }

    /**
     * 查询问题列表
     *
     * @param pageable
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "helpCenter/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllHelpCenter(@PageableDefault(size = 20, sort = "sort") Pageable pageable, @RequestParam(defaultValue = "1") Integer platform) throws JsonProcessingException {
        Page<HelpCenter> helpCenterList = helpCenterRepository.findByPlatform(platform, pageable);

        Map<Class, Object> filterMap = new HashMap<>();
        filterMap.put(HelpCenter.class, new String[]{"id", "platform", "title", "content", "sort", "create_date", "is_visable"});
        filterMap.put(PageImpl.class, new String[]{"content", "total_pages", "total_elements", "size", "number", "last"});
        SimpleFilterProvider filterProvider = PropertyFilterUtil.filterOutAllExceptFilter(filterMap);

        JsonResponseEntity response;
        if (helpCenterList.getContent() != null && !helpCenterList.getContent().isEmpty()) {
            response = new JsonResponseEntity(0, "查询成功", helpCenterList);
        } else {
            response = new JsonResponseEntity(-1, "查询失败");
        }
        return PropertyFilterUtil.getObjectMapper().setFilterProvider(filterProvider).writeValueAsString(response);
    }

    /**
     * 查询问题详情
     *
     * @param id
     * @return
     * @throws JsonProcessingException app-H5和后台都在用
     */
    @RequestMapping(value = "helpCenter/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findHelpCenterDetail(@RequestParam String id) throws JsonProcessingException {
        HelpCenter helpCenter = helpCenterService.findById(id);

        Map<Class, Object> filterMap = new HashMap<>();
        filterMap.put(HelpCenter.class, new String[]{"id", "title", "platform", "sort", "content", "is_visable"});
        SimpleFilterProvider filterProvider = PropertyFilterUtil.filterOutAllExceptFilter(filterMap);

        JsonResponseEntity response;
        if (helpCenter != null) {
            response = new JsonResponseEntity(0, "查询成功", helpCenter);
        } else {
            response = new JsonResponseEntity(-1, "查询失败");
        }
        return PropertyFilterUtil.getObjectMapper().setFilterProvider(filterProvider).writeValueAsString(response);
    }

    /**
     * 新增问题
     *
     * @param para
     * @return
     */
    @RequestMapping(value = "saveHelpCenter", method = RequestMethod.POST)
    @Admin
    public JsonResponseEntity saveHelpCenter(@RequestBody Map para) {
        HelpCenter helpCenter = MapToBeanUtil.fromMapToBean(HelpCenter.class, para);
        helpCenter.setDelFlag("0");
        helpCenter.setUpdateDate(new Date());
        helpCenter.setCreateDate(new Date());
        helpCenterService.saveHelpCenter(helpCenter);

        return new JsonResponseEntity(0, "保存成功");
    }

    /**
     * 修改问题
     *
     * @param para
     * @return
     */
    @RequestMapping(value = "updateHelpCenter", method = RequestMethod.POST)
    @Admin
    public JsonResponseEntity updateHelpCenter(@RequestBody Map para) {
        HelpCenter helpCenter = MapToBeanUtil.fromMapToBean(HelpCenter.class, para);
        helpCenter.setDelFlag("0");
        helpCenter.setUpdateDate(new Date());
        helpCenter.setCreateDate(new Date());
        helpCenterService.saveHelpCenter(helpCenter);

        return new JsonResponseEntity(0, "修改成功");
    }

    /**
     * 批量删除问题
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "deleteHelpCenter", method = RequestMethod.DELETE)
    @Admin
    public JsonResponseEntity deleteHelpCenter(@RequestParam List<String> ids) {
        helpCenterService.batchRemoveHelpCenter(ids);

        return new JsonResponseEntity(0, "删除成功");
    }
}
