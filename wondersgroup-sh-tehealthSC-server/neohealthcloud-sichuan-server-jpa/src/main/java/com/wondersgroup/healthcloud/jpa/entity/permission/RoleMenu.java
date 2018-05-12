package com.wondersgroup.healthcloud.jpa.entity.permission;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zhuchunliu on 2015/11/12.
 */
@Data
@Entity
@Table(name = "tb_permission_role_menu")
public class RoleMenu {

    @Id
    private String id;
    @Column(name = "role_id")
    private String roleId;
    @Column(name = "menu_id")
    private String menuId;
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
}
