package com.wondersgroup.healthcloud.services.permission.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.permission.Menu;
import lombok.Data;

import java.util.List;

/**
 * Created by zhaozhenxing on 2016/9/9.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDTO {
    private String menuId;
    private String menuName;
    private String parentId;
    private String parentName;
    private String href;
    private String type;
    private List<MenuDTO> children;
    private Boolean checked;
    private String permission;
    private String sort;

    public MenuDTO() {
    }

    public MenuDTO(Menu menu) {
        this.menuId = menu.getMenuId();
        this.menuName = menu.getName();
        this.parentId = menu.getParentId();
        this.parentName = menu.getParentName();
        this.href = menu.getHref();
        this.type = menu.getType();
        this.permission = menu.getPermission();
        this.sort = menu.getSort();
    }
}
