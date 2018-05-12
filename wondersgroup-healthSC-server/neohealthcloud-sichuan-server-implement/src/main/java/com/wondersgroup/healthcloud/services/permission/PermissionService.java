package com.wondersgroup.healthcloud.services.permission;


import com.wondersgroup.healthcloud.jpa.entity.permission.Menu;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/26.
 */
public interface PermissionService {
    List<Map<String, Object>> getRoleEnnameByUser(String userId, Boolean admin);

    List<Map<String, Object>> getMenuPermissionByUser(String userId, Boolean admin);

    List<Menu> getMenuByParentId(String userId, String parentId, Boolean admin);

    Boolean hasPermission(String userId, String permission);
}
