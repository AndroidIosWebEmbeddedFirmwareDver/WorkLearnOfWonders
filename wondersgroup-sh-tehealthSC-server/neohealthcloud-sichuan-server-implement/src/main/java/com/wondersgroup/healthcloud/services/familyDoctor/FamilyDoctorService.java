package com.wondersgroup.healthcloud.services.familyDoctor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.wondersgroup.healthcloud.common.utils.JsonMapper;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.familydoctor.response.Response;
import com.wondersgroup.healthcloud.familydoctor.response.sign.SignTeamDoctorResponse;
import com.wondersgroup.healthcloud.familydoctor.response.sign.SignTeamInfoResponse;
import com.wondersgroup.healthcloud.familydoctor.response.sign.SignTeamListResponse;
import com.wondersgroup.healthcloud.familydoctor.services.sign.QianyueInterface;
import com.wondersgroup.healthcloud.jpa.entity.doctor.DoctorAccount;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.jpa.repository.doctor.DoctorAccountRepository;
import com.wondersgroup.healthcloud.jpa.repository.user.AccountRepository;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.familyDoctor.dto.FamilyDoctorInfoDTO;
import com.wondersgroup.healthcloud.services.familyDoctor.dto.FamilyTeamInfoDTO;
import com.wondersgroup.healthcloud.services.familyDoctor.dto.FamilyTeamListDTO;
import com.wondersgroup.healthcloud.services.familyDoctor.utils.FamilyDoctorUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by ys on 17-6-6.
 */
@Service("familyDoctorService")
public class FamilyDoctorService {

    @Autowired
    private QianyueInterface qianyueInterface;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DoctorAccountRepository doctorAccountRepository;

    /**
     * 获取家庭医生团队列表
     * 需要注意 如果还有下一页 返回的数据为page+1条，最后一条为null，需要判断去掉。
     */
    public List<FamilyTeamListDTO> getFamilyDoctorList(String lat, String lng, double distance, int page, int pageSize) {
        List<FamilyTeamListDTO> listDTOs = new ArrayList<>();

        FamilyTeamListDTO listDTO = new FamilyTeamListDTO();
        listDTO.setIsOnline(1);
        listDTO.setLeader("王大状");
        listDTO.setOrgCode("001");
        listDTO.setOrgName("高鹏大道社区服务中心");
        listDTO.setSignedCount("1000+");
        listDTO.setTeamId("1000000001");
        listDTO.setTeamName("高鹏大道家庭医生团队");
        listDTOs.add(listDTO);

        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        Map<String, Object> reqParams = Maps.newHashMap();
        reqParams.put("longitude", lat);
        reqParams.put("latitude", lng);
        reqParams.put("pageNo", page);
        reqParams.put("pageSize", pageSize);
        String response = qianyueInterface.getMyTeamList(FamilyDoctorUtil.getRequestHeaderForJson(), jsonMapper.toJson(reqParams));
        if (StringUtils.isEmpty(response)) {
            throw new CommonException(1020, "暂无数据");
        }
        Response<SignTeamListResponse> resInfo = jsonMapper.fromJson(response, new TypeReference<Response<SignTeamListResponse>>() {
        });
        if (null == resInfo || !resInfo.getResultCode().equals("0") || CollectionUtils.isEmpty(resInfo.getData().getList())) {
            throw new CommonException(1021, "暂无数据");
        }
        //去检查这个团队是否在我们医生段app注册并登录过
        Set<String> leaderIds = new HashSet<>();
        for (SignTeamListResponse.TeamListEntity teamListEntity : resInfo.getData().getList()) {
            leaderIds.add(teamListEntity.getLeader());
        }
        for (SignTeamListResponse.TeamListEntity teamListEntity : resInfo.getData().getList()) {
            FamilyTeamListDTO familyTeamListDTO = new FamilyTeamListDTO(teamListEntity);
            listDTOs.add(familyTeamListDTO);
        }
        //如果还有下一页
        if (resInfo.getData().getTotalCount() > (page * pageSize)) {
            listDTOs.add(null);
        }
        return listDTOs;
    }

    /**
     * 获取家庭团队详细信息
     */
    public FamilyTeamInfoDTO getFamilyTeamInfo(String uid, String teamId) {

        Account userInfo = accountService.info(uid);
        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        Map<String, Object> reqParams = Maps.newHashMap();
        reqParams.put("teamId", teamId);
        reqParams.put("user_orgCode", "877ff477h441");
        reqParams.put("dlyhsfzh", "512501197203035172");

        String response = qianyueInterface.getTeamDetail(FamilyDoctorUtil.getRequestHeaderForJson(), jsonMapper.toJson(reqParams));
        if (StringUtils.isEmpty(response)) {
            throw new CommonException(1020, "查询失败");
        }
        Response<SignTeamInfoResponse> resInfo = jsonMapper.fromJson(response, new TypeReference<Response<SignTeamInfoResponse>>() {
        });
        if (null == resInfo || !resInfo.getResultCode().equals("0")) {
            throw new CommonException(1021, "查询失败");
        }
        if (resInfo.getData() == null) {
            throw new CommonException(1022, "暂无信息");
        }
        FamilyTeamInfoDTO familyTeamInfoDTO = new FamilyTeamInfoDTO(resInfo.getData());

        //测试数据
        familyTeamInfoDTO.setTeamName("高鹏大道家庭医生团队");
        familyTeamInfoDTO.setAddress("高鹏大道社区服务中心地址张江路2009号78弄平安大厦哈哈");
        familyTeamInfoDTO.setSignedCount("1000+");
        familyTeamInfoDTO.setTeamId("1000001");
        familyTeamInfoDTO.setIsOnline(1);
        familyTeamInfoDTO.setSkilled("本人擅长敲代码，改bug。哈哈，本人擅长敲代码，改bug。哈哈，本人擅长敲代码，改bug。哈哈");

        List<FamilyDoctorInfoDTO> familyMemberInfoDTOs = new ArrayList<>();
        FamilyDoctorInfoDTO memberInfoDTO = new FamilyDoctorInfoDTO();
        memberInfoDTO.setTeamId("100001");
        memberInfoDTO.setDept("产科");
        memberInfoDTO.setOrgName("高鹏大道家庭医生团队");
        memberInfoDTO.setIsLeader(1);
        memberInfoDTO.setDoctorName("夏雨");
        memberInfoDTO.setTitle("主任医生");
        familyMemberInfoDTOs.add(memberInfoDTO);

        FamilyDoctorInfoDTO memberInfoDTO1 = new FamilyDoctorInfoDTO();
        memberInfoDTO1.setTeamId("100001");
        memberInfoDTO1.setDept("产科2");
        memberInfoDTO1.setOrgName("高鹏大道家庭医生团队");
        memberInfoDTO1.setIsLeader(0);
        memberInfoDTO1.setDoctorName("夏雪");
        memberInfoDTO1.setTitle("副主任医生");
        familyMemberInfoDTOs.add(memberInfoDTO);
        familyTeamInfoDTO.setMemberList(familyMemberInfoDTOs);
        return familyTeamInfoDTO;
    }

    /**
     * 获取家庭团队详细信息
     */
    public FamilyDoctorInfoDTO getFamilyDoctorInfo(String teamId, String doctorId, String loginDoctorId) {
        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        Map<String, Object> reqParams = Maps.newHashMap();
        reqParams.put("memberId", doctorId);
        if (StringUtils.isNotEmpty(loginDoctorId)) {
            DoctorAccount doctorAccount = doctorAccountRepository.findOne(loginDoctorId);
            if (null == doctorAccount) {
                throw new CommonException(1021, "医生信息无效");
            }
            reqParams.put("user_orgCode", "");
            reqParams.put("dlyhsfzh", "");
        } else {
            reqParams.put("user_orgCode", "");
            reqParams.put("dlyhsfzh", "");
        }

        String response = qianyueInterface.getMemberDetail(FamilyDoctorUtil.getRequestHeaderForJson(), jsonMapper.toJson(reqParams));
        if (StringUtils.isEmpty(response)) {
            throw new CommonException(1020, "查询失败");
        }
        Response<SignTeamDoctorResponse> resInfo = jsonMapper.fromJson(response, new TypeReference<Response<SignTeamDoctorResponse>>() {
        });
        if (null == resInfo || !resInfo.getResultCode().equals("0") || resInfo.getData() == null) {
            throw new CommonException(1021, "查询失败");
        }
        FamilyDoctorInfoDTO doctorInfoDTO = new FamilyDoctorInfoDTO(resInfo.getData());
        doctorInfoDTO.setTeamId(teamId);
        return doctorInfoDTO;
    }

    /**
     * 用户签约团队
     *
     * @param uid
     * @param teamId
     */
    @Transactional
    public Boolean signFamilyTeam(String uid, String teamId) {
        Account userInfo = accountService.info(uid);
        if (StringUtils.isEmpty(userInfo.getIdcard())) {
            throw new CommonException(1031, "非实名认证用户，不能签约家庭医生");
        }
        if (StringUtils.isNotEmpty(userInfo.getSignFamilyTeamId())) {
            throw new CommonException(1032, "您已签约，不能重复签约!");
        }
        userInfo.setSignFamilyTeamId(teamId);
        userInfo.setSignTime(new Date());
        accountRepository.saveAndFlush(userInfo);//保存签约信息

        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        Map<String, Object> reqParams = Maps.newHashMap();
        reqParams.put("hzxxSfzh", userInfo.getIdcard());
        reqParams.put("hzxxHzxm", userInfo.getName());//姓名
        reqParams.put("user_orgCode", "");
        reqParams.put("dlyhsfzh", "");
        reqParams.put("hzxxLxdh", userInfo.getMobile());//手机号
        reqParams.put("teamId", teamId);

        String response = qianyueInterface.makeContract(FamilyDoctorUtil.getRequestHeaderForJson(), jsonMapper.toJson(reqParams));
        if (StringUtils.isEmpty(response)) {
            throw new CommonException(1020, "签约失败");
        }
        Response<SignTeamDoctorResponse> resInfo = jsonMapper.fromJson(response, new TypeReference<Response<SignTeamDoctorResponse>>() {
        });
        if (null == resInfo || !resInfo.getResultCode().equals("0")) {
            throw new CommonException(1021, "签约失败");
        }
        return true;
    }
}
