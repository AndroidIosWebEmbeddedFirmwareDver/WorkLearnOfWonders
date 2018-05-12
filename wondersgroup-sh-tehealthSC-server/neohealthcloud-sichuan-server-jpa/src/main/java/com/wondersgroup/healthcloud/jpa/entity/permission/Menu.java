package com.wondersgroup.healthcloud.jpa.entity.permission;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhuchunliu on 2015/11/11.
 */
@Data
@Entity
@Table(name = "tb_permission_menu")
public class Menu {
    @Id
    @Column(name = "menu_id")
    private String menuId;
    @Column(name = "parent_id")
    private String parentId;
    private String name;
    private String icon;
    private String sort;
    private String href;
    private String permission;
    @Column(name = "is_show")
    private String isShow;//是否可用 1-可用 0-禁用
    private String type;//类型 1-菜单 2-按钮
    @Column(name = "del_flag")
    private String delFlag;
    @Column(name = "create_by")
    private String createBy;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_by")
    private String updateBy;
    @Column(name = "update_date")
    private Date updateDate;
    @Transient
    private String parentName;
}
