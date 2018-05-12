package com.wondersgroup.healthcloud.jpa.entity.user;

import com.wondersgroup.healthcloud.jpa.utils.PatternUtils;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Description a
 * @Author
 * @Create 2018-04-13 下午2:56
 **/
@Data
@Entity
@Table(name = "tb_user_online_medical_cards")
public class UserOnlineMedicalCards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;//在线挂号卡类型唯一ID

    @Column(name = "uid")
    private String uid;//用户唯一编码ID

    @Column(name = "hospital_id")
    private long hospital_id;//医院ID；如果卡类型是就诊卡则不为空

    @Column(name = "hospital_code")
    private String hospital_code;//医院代码；如果卡类型是就诊卡则不为空

    @Column(name = "hospital_name")
    private String hospital_name;//医院名称；如果卡类型是就诊卡则不为空

    @Column(name = "mediacl_card_no")
    private String mediacl_card_no;//在线挂号卡卡号

    @Column(name = "card_type_code")
    private String card_type_code;//在线挂号卡类型编码描述

    @Column(name = "update_time")
    private Timestamp updateTime;//在线挂号卡类型更新时间

    @Column(name = "create_time")
    private Timestamp createTime;//在线挂号卡类型创建时间

    @Column(name = "is_deleted")
    private int is_deleted;//是否删除，0-未删除，1-已删除


    public void checkCreateData() throws Exception {
        if (this.card_type_code == null) {
            throw new Exception("卡类型不能为空");
        }
        if (this.card_type_code.equals(OnlineMedicalCardTypes.HOSPITALMEDICALCARD) && (this.hospital_id <= 0)) {
            throw new Exception("卡类型为院内就诊卡时，医院ID不能为空！");
        }
        if (this.card_type_code.equals(OnlineMedicalCardTypes.HOSPITALMEDICALCARD) && (this.hospital_code == null || this.hospital_code.length() <= 0)) {
            throw new Exception("卡类型为院内就诊卡时，医院编码不能为空！");
        }
        if (this.card_type_code.equals(OnlineMedicalCardTypes.HOSPITALMEDICALCARD) && (this.hospital_name == null || this.hospital_name.length() <= 0)) {
            throw new Exception("卡类型为院内就诊卡时，医院编码不能为空！");
        }
        if (this.uid == null || this.uid.length() <= 0) {
            throw new Exception("用户不能为空");
        }
        if (this.mediacl_card_no == null || this.mediacl_card_no.length() <= 0) {
            throw new Exception("卡号不能为空");
        }
        if (PatternUtils.isSpecialChar(this.mediacl_card_no)) {
            throw new Exception("卡号不能包含特殊字符");
        }
        this.is_deleted = 0;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.updateTime = new Timestamp(System.currentTimeMillis());
    }

    public void checkupdateData() throws Exception {
        if (this.id <= 0) {
            throw new Exception("卡唯一识别不能为空");
        }
        if (this.card_type_code == null) {
            throw new Exception("卡类型不能为空");
        }

        if (this.card_type_code.equals(OnlineMedicalCardTypes.HOSPITALMEDICALCARD) && (this.hospital_id <= 0)) {
            throw new Exception("卡类型为院内就诊卡时，医院ID不能为空！");
        }
        if (this.card_type_code.equals(OnlineMedicalCardTypes.HOSPITALMEDICALCARD) && (this.hospital_code == null || this.hospital_code.length() <= 0)) {
            throw new Exception("卡类型为院内就诊卡时，医院编码不能为空！");
        }
        if (this.card_type_code.equals(OnlineMedicalCardTypes.HOSPITALMEDICALCARD) && (this.hospital_name == null || this.hospital_name.length() <= 0)) {
            throw new Exception("卡类型为院内就诊卡时，医院编码不能为空！");
        }
        if (this.uid == null || this.uid.length() <= 0) {
            throw new Exception("用户不能为空");
        }
        if (this.mediacl_card_no == null || this.mediacl_card_no.length() <= 0) {
            throw new Exception("卡号不能为空");
        }
        if (PatternUtils.isSpecialChar(this.mediacl_card_no)) {
            throw new Exception("卡号不能包含特殊字符");
        }
        this.is_deleted = 0;
        this.updateTime = new Timestamp(System.currentTimeMillis());
    }

    public void checkDeleteData() throws Exception {
        if (this.id <= 0) {
            throw new Exception("卡唯一识别不能为空");
        }
        if (this.uid == null || this.uid.length() <= 0) {
            throw new Exception("用户不能为空");
        }
        this.is_deleted = 0;
        this.updateTime = new Timestamp(System.currentTimeMillis());
    }


    public void checkFindAllByUid() throws Exception {
        if (this.uid == null || this.uid.length() <= 0) {
            throw new Exception("参数错误：用户ID不能为空");
        }
    }


    public void checkFindAllByUidAndHospitalCode() throws Exception {
        if (this.uid == null || this.uid.length() <= 0) {
            throw new Exception("参数错误：用户ID不能为空");
        }
        if (this.hospital_code == null || this.hospital_code.length() <= 0) {
            throw new Exception("参数错误：医院编码不能为空");
        }
    }


    public void checkFindAllByUidAndCardTypeCode() throws Exception {
        if (this.uid == null || this.uid.length() <= 0) {
            throw new Exception("参数错误：用户ID不能为空");
        }
        if (this.card_type_code == null || this.card_type_code.length() <= 0) {
            throw new Exception("参数错误：类型编码不能为空");
        }
    }


    public void checkFindAllByUidAndHospitalCodeAndCardTypeCode() throws Exception {
        if (this.uid == null || this.uid.length() <= 0) {
            throw new Exception("参数错误：用户ID不能为空");
        }
        if (this.hospital_code == null || this.hospital_code.length() <= 0) {
            throw new Exception("参数错误：医院编码不能为空");
        }
        if (this.card_type_code == null || this.card_type_code.length() <= 0 || (!this.card_type_code.equals(OnlineMedicalCardTypes.HOSPITALMEDICALCARD) && !this.card_type_code.equals
                (OnlineMedicalCardTypes.RESIDENTHEALTHCARD))) {
            throw new Exception("参数错误：类型编码不能为空");
        }
    }


}
