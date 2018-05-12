package com.wondersgroup.healthcloud.utils;

import com.wondersgroup.healthcloud.services.permission.dto.MenuDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by zhaozhenxing on 2016/9/21.
 */
public class MenuUtils {
    public static void addMenuChildrenToParent(List<MenuDTO> all) {
        LinkedHashMap<String, MenuDTO> map = new LinkedHashMap<>();
        for (MenuDTO menuDTO : all) {
            map.put(menuDTO.getMenuId(), menuDTO);
        }
        for (MenuDTO menuDTO : map.values()) {
            String parentId = menuDTO.getParentId();
            if (!StringUtils.isEmpty(parentId + "") && map.get(parentId) != null) {
                if (map.get(parentId).getChildren() == null) {
                    map.get(parentId).setChildren(new ArrayList<MenuDTO>());
                }
                map.get(parentId).getChildren().add(menuDTO);
            }
        }
    }
}
