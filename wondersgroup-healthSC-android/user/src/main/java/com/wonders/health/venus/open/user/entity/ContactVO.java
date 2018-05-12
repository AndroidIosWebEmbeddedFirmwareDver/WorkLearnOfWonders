package com.wonders.health.venus.open.user.entity;

/**
 * Created by sunning on 16/1/6.
 */
public class ContactVO {

    private int isDefault;//0否1是
    /**
     * id :
     * name :
     * idcard :
     * gender :
     * age :
     * mobile :
     */

    private String id;
    private String name;
    private String idcard;
    private String gender;
    private int age;
    private String mobile;


    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int aDefault) {
        isDefault = aDefault;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdcard() {
        return idcard;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getMobile() {
        return mobile;
    }



    public ContactVO() {
    }

    public ContactVO(String name, String idcard, String mobile,int isDefault) {
        this.name = name;
        this.idcard = idcard;
        this.mobile = mobile;
        this.isDefault = isDefault;
    }
}
