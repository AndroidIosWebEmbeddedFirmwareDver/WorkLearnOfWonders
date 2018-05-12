package com.wondersgroup.healthcloud.jpa.entity.doctor;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by longshasha on 16/8/1.
 */
@Data
@Entity
@Table(name = "tb_doctor_account")
public class DoctorAccount {
    @Id
    private String id;
    private String mobile;
    private String name;
    private String nickname;
    private String avatar;
    private String talkid;
    private String talkpwd;
    @Column(name = "del_flag")
    private String delFlag;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;


}
