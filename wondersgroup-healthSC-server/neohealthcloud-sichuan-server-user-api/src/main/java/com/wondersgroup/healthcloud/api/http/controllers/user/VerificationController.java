package com.wondersgroup.healthcloud.api.http.controllers.user;

import com.wondersgroup.healthcloud.api.http.dto.user.VerificationInfoDTO;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonKeyReader;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.user.VerificationService;
import com.wondersgroup.healthcloud.services.user.exception.ErrorAccountNotExitException;
import com.wondersgroup.healthcloud.services.user.exception.ErrorIdcardException;
import com.wondersgroup.healthcloud.utils.IdcardUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by longshasha on 16/11/9.
 */

@RestController
@RequestMapping("/api")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private AccountService accountService;


    /**
     * 提交实名认证信息
     *
     * @return
     */
    @VersionRange
    @PostMapping(path = "/verification/submit")
    public JsonResponseEntity<String> verificationSubmit(@RequestBody String request) {
        JsonKeyReader reader = new JsonKeyReader(request);
        String id = reader.readString("uid", false);
        String name = reader.readString("name", false);
        String idCard = reader.readString("idcard", false);
        String photo = reader.readString("photo", false);
        JsonResponseEntity<String> body = new JsonResponseEntity<>();
        name = name.trim();//去除空字符串
        idCard = idCard.trim();
        idCard = StringUtils.upperCase(idCard);
        Boolean isIdCard = IdcardUtils.validateCard(idCard);
        if (!isIdCard) {
            throw new ErrorIdcardException();
        }
        verificationService.verificationSubmit(id, name, idCard, photo);
        body.setMsg("提交成功");
        return body;
    }


    /**
     * 根据用户Id获取实名认证信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/verification/submit/info", method = RequestMethod.GET)
    @VersionRange
    public JsonResponseEntity<VerificationInfoDTO> verificationSubmitInfo(@RequestParam("uid") String id) {
        JsonResponseEntity<VerificationInfoDTO> body = new JsonResponseEntity<>();
        Account account = accountService.info(id);
        if (account != null && account.verified()) {
            VerificationInfoDTO data = new VerificationInfoDTO();
            data.setUid(id);
            data.setName(IdcardUtils.maskName(account.getName()));
            data.setIdcard(IdcardUtils.maskIdcard(account.getIdcard()));
            data.setSuccess(true);
            data.setStatus(3);
            data.setStatusSpec(VerificationInfoDTO.statusArray[3]);
            data.setCanSubmit(false);
            body.setData(data);
        } else if (account != null) {
            body.setData(new VerificationInfoDTO(id, verificationService.verficationSubmitInfo(id)));
        } else if (account == null) {
            throw new ErrorAccountNotExitException();
        }
        return body;
    }
}
