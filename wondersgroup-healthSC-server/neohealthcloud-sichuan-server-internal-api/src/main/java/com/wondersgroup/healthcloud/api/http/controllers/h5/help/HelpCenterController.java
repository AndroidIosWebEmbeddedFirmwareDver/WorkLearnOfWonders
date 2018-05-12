package com.wondersgroup.healthcloud.api.http.controllers.h5.help;

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

@RestController("H5HelpCenterController")
@RequestMapping("/h5/api")
public class HelpCenterController {

    @Autowired
    private HelpCenterService helpCenterService;
    @Autowired
    private HelpCenterRepository helpCenterRepository;

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

}
