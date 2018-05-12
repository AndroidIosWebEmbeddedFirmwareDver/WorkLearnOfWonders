package com.wondersgroup.healthcloud.api.http.controllers.admin.verification;

import com.wondersgroup.healthcloud.api.utils.DateUtils;
import com.wondersgroup.healthcloud.api.utils.Pager;
import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonKeyReader;
import com.wondersgroup.healthcloud.entity.request.RegisterOrUpdateUserInfoRequest;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.jpa.entity.user.Verification;
import com.wondersgroup.healthcloud.jpa.repository.user.VerificationRepository;
import com.wondersgroup.healthcloud.services.user.VerificationService;
import com.wondersgroup.healthcloud.services.user.dto.VerificationForm;
import com.wondersgroup.healthcloud.services.user.exception.VerificationManageException;
import com.wondersgroup.healthcloud.utils.sms.SMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by longshasha on 16/11/4.
 */

@RestController
@RequestMapping("/admin/verification/manage")
@Admin
public class VerificationManageController {

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private SMS sms;


    private static final String[] smsContent = {
            "您的提交的实名认证已通过,可登录app查看",
            "您的提交的实名认证被驳回,可登录app查看"
    };

    /**
     * 实名认证信息列表
     *
     * @param pager
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Admin
    public Pager findVerificationList(@RequestBody Pager pager) {
        int pageNum = 1;
        if (pager.getNumber() != 0)
            pageNum = pager.getNumber();

        List<VerificationForm> verificationFormList =
                verificationService.findVerificationListByPager(pageNum, pager.getSize(), pager.getParameter());

        int totalSize = verificationService.countVerificationListByPager(pager.getParameter());
        pager.setTotalElements(totalSize);
        pager.setData(verificationFormList);
        return pager;
    }

    /**
     * 审核通过
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/verified", method = RequestMethod.POST)
    @Admin
    public JsonResponseEntity verified(@RequestBody String request) {
        JsonResponseEntity<String> response = new JsonResponseEntity<>();
        JsonKeyReader reader = new JsonKeyReader(request);
        Integer id = reader.readObject("id", false, Integer.class);

        Account account = verificationService.verifiedUserById(id);

        //产品说不要实名认证发送短信,以防他们再变暂时注释
        /*if(StringUtils.isNotBlank(account.getMobile())){
            sms.send(account.getMobile(),smsContent[0]);
        }*/

        response.setMsg("保存成功");
        return response;
    }

    public RegisterOrUpdateUserInfoRequest.UserInfo accountToWSUserInfo(Account account){
        RegisterOrUpdateUserInfoRequest.UserInfo userInfo = new RegisterOrUpdateUserInfoRequest.UserInfo();
        userInfo.setUserCardId(account.getIdcard());
        userInfo.setUserCardType("01");
        userInfo.setUserPhone(account.getMobile());
        userInfo.setUserName(account.getName());
        userInfo.setPassWord(account.getPassword());
        userInfo.setIdentifyCode("0");
        userInfo.setUserSex(account.getGender());
        userInfo.setUserBD(DateUtils.formatDate(account.getBirthday(),"yyyy-MM-dd"));
        userInfo.setUserContAdd("");
        userInfo.setUserEmail("");
        return userInfo;
    }

    /**
     * 审核驳回
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
    @Admin
    public JsonResponseEntity refuse(@RequestBody String request) {
        JsonResponseEntity<String> response = new JsonResponseEntity<>();
        JsonKeyReader reader = new JsonKeyReader(request);
        Integer id = reader.readObject("id", false, Integer.class);
        String reason = reader.readString("reason", false);
        reason = reason.trim();
        if (reason.length() > 30) {
            throw new VerificationManageException("驳回原因不能超过30个字");
        }
        Verification verification = verificationRepository.getOne(id);
        if (verification == null || verification.getVerificationLevel() != 0) {
            throw new VerificationManageException();
        }
        verification.setId(id);
        verification.setVerificationLevel(-1);
        verification.setRefusal_reason(reason);
        verificationRepository.save(verification);

        //产品说不要实名认证发送短信,以防他们再变暂时注释
        /*Account account = verificationService.getAccountById(verification.getUid());
        if(StringUtils.isNotBlank(account.getMobile())){
            sms.send(account.getMobile(),smsContent[1]);
        }*/
        response.setMsg("保存成功");
        return response;
    }


}
