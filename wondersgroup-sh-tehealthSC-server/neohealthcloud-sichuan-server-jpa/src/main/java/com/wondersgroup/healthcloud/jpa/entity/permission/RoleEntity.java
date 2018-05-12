package com.wondersgroup.healthcloud.jpa.entity.permission;

import lombok.Data;

/**
 * Created by zhaozhenxing on 2016/9/19.
 */
@Data
public class RoleEntity {
    String roleId;
    String name;
    Boolean checked;
}
