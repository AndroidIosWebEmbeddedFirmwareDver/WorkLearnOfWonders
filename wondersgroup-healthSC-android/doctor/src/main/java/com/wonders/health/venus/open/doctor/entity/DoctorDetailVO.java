package com.wonders.health.venus.open.doctor.entity;

import java.util.List;

/*
 * Created by sunning on 16/1/5.
 */
public class DoctorDetailVO {

    /**
     * id : 16
     * hosOrgCode : 450753341
     * hosDeptCode : 1007
     * hosDoctCode : 266
     * headphoto :
     * doctorName : 王小剑
     * doctorTitle :
     * expertin :
     * orderCount : 0
     * isFull : 0
     * evaluateCount : 1
     * evaluList : [{"id":21,"uid":"132dwf4fgrf4gt5","nickName":"健康用户1101","content":"里约奥运会4X100米混合泳接力比赛，是菲尔普斯在奥运舞台的最后一战。此前他已亲口宣布自己熬不到下一","createTime":"2016-11-02 15:11"}]
     */

    public int id;
    public String hosOrgCode;
    public String hosDeptCode;
    public String hosDoctCode;
    public String headphoto;
    public String doctorName;
    public String doctorTitle;
    public String expertin;
    public int orderCount;
    public int isFull;
    public int evaluateCount;
    public String hosName;
    public String deptName;
    public String gender;
    public String doctorDesc;
    public int concern;//0-未关注，1-已关注

    /**
     * id : 21
     * uid : 132dwf4fgrf4gt5
     * nickName : 健康用户1101
     * content : 里约奥运会4X100米混合泳接力比赛，是菲尔普斯在奥运舞台的最后一战。此前他已亲口宣布自己熬不到下一
     * createTime : 2016-11-02 15:11
     */
    public List<EvaluateEntity.Evaluate> evaluList;

    /**
     * concern : 0
     */
}
