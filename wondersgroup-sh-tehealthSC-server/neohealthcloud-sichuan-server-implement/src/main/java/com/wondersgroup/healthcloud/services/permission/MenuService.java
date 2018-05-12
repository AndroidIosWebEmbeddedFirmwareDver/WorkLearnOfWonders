package com.wondersgroup.healthcloud.services.permission;

import com.wondersgroup.healthcloud.jpa.entity.permission.Menu;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/17.
 */
public interface MenuService {

    List<Map<String, Object>> getMenuByRole(String roleId);

    List<Map<String, Object>> getMenuByGroup(String groupId);

    List<Map<String, Object>> findAllMenu();


    Integer getNextSort(String menuId);

    List<Menu> getMenuExpectButtonByParentId(String menuId, String userId);
}
