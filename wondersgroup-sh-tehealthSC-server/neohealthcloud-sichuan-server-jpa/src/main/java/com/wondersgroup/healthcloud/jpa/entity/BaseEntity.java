/**
 *
 */
package com.wondersgroup.healthcloud.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 2568737017856098619L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @Column(name = "del_flag")
    private String delFlag = "0";
    @Column(name = "source_id")
    private String sourceId;
    @Column(name = "create_by")
    private String createBy;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_by")
    private String updateBy;
    @Column(name = "update_date")
    private Date updateDate;
}
