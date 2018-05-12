package com.wondersgroup.healthcloud.jpa.entity.permission;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tb_permission_user")
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;
    private String loginname;
    private String username;
    private String password;
    private String locked;
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
    @Column(name = "main_area")
    private String mainArea = "51";
    @Transient
    private String mainAreaName;
    @Transient
    private String specAreaName;
    @Column(name = "spec_area")
    private String specArea;
    @Transient
    private List<RoleEntity> roleList;
}