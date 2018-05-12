package com.wondersgroup.healthcloud.jpa.entity.identify;


import com.wondersgroup.healthcloud.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wang
 */

/**
 * 检验表
 * <table border="1" >
 * <tr><th>设计名</th> <th>中文名</th> <th>说明</th></tr>
 * <tr><td>id</td><td>测试id</td><td>无</td></tr>
 * <tr><td>registerid</td><td>注册id</td><td>无</td></tr>
 * <tr><td>testtime</td><td>测试时间</td><td>无</td></tr>
 * <tr><td>type</td><td>问卷类型 1：中医体质辨识、2：糖尿病风险评估、3：高血压风险评估、4脑卒中风险评估</td><td>无</td></tr>
 * <tr><td>content</td><td>回答内容</td><td>无</td></tr>
 * <tr><td>result</td><td>测试结果</td><td>无</td></tr>
 * </table>
 * */
@Data
@Entity
@Table(name = "app_tb_healthquestion")
public class HealthQuestion extends BaseEntity {

    @Id
    private String id;
    private String registerid;
    private Date testtime;
    private String type;
    private String content;
    private String result;

}
