package com.wondersgroup.healthcloud.jpa.entity.permission;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by zhuchunliu on 2015/11/11.
 */
@Data
@Entity
@Table(name = "tb_permission_role")
public class Role {
    @Id
    @Column(name = "role_id")
    private String roleId;
    private String name;
    private String enname;
    private String description;
    private String useable;//是否可用 1-可用 0-禁用
    @Column(name = "del_flag")
    private String delFlag;
    @Column(name = "create_by")
    private String createBy;
    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @Column(name = "update_by")
    private String updateBy;
    @Column(name = "update_date")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date updateDate;
    @Transient
    private List<Menu> menuList;
    @Transient
    private String menuTree;

    @Data
    public class Menu {
        String id;
        String name;
        Boolean checked;
        String pId;
        Boolean open;
    }
}
