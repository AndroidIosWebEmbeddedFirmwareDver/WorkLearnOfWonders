package com.wondersgroup.healthcloud.api.http.controllers.user;

import com.google.common.collect.ImmutableMap;
import com.wondersgroup.healthcloud.api.http.dto.user.AccountAndSessionDTO;
import com.wondersgroup.healthcloud.api.http.dto.user.AccountDTO;
import com.wondersgroup.healthcloud.common.http.annotations.IgnoreGateLog;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonKeyReader;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.jpa.entity.user.UserOnlineMedicalCards;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.account.dto.AccountInfoAndSession;
import com.wondersgroup.healthcloud.services.account.dto.AccountInfoForm;
import com.wondersgroup.healthcloud.services.account.dto.AccountSignupForm;
import com.wondersgroup.healthcloud.services.user.UserOnlineMedicalCardsService;
import com.wondersgroup.healthcloud.services.user.VerificationService;
import com.wondersgroup.healthcloud.utils.security.RSA;
import com.wondersgroup.healthcloud.utils.security.RSAKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(value = "/api/user/online/medcial/cards", tags = "1.卡管理相关接口", description = "卡管理相关接口")
@RestController
@RequestMapping("/api/user/online/medcial/cards")
public class UserOnlineMedicalCardsController {

    @Autowired
    private UserOnlineMedicalCardsService userOnlineMedicalCardsService;

    @ApiOperation(value = "新增", notes = "新增<T>")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<UserOnlineMedicalCards> create(@RequestBody UserOnlineMedicalCards input) throws Exception {
        JsonResponseEntity<UserOnlineMedicalCards> response = new JsonResponseEntity<UserOnlineMedicalCards>(0, "成功");
        try {
            response.setData(userOnlineMedicalCardsService.create(input));
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().length() > 0) {
                response.setMsg(e.getMessage());
            } else {
                response.setMsg("失败");
            }
            response.setCode(1002);
        }
        return response;
    }

    @ApiOperation(value = "更新", notes = "新增<T>")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<UserOnlineMedicalCards> update(@RequestBody UserOnlineMedicalCards input) throws Exception {
        JsonResponseEntity<UserOnlineMedicalCards> response = new JsonResponseEntity<UserOnlineMedicalCards>(0, "成功");
        try {
            response.setData(userOnlineMedicalCardsService.update(input));
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().length() > 0) {
                response.setMsg(e.getMessage());
            } else {
                response.setMsg("失败");
            }
            response.setCode(1002);
        }
        return response;
    }

    @ApiOperation(value = "解除绑定", notes = "解除绑定<T>")
    @RequestMapping(value = "/updateLogicDelete", method = RequestMethod.POST)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<UserOnlineMedicalCards> updateLogicDelete(@RequestBody UserOnlineMedicalCards input) throws Exception {
        JsonResponseEntity<UserOnlineMedicalCards> response = new JsonResponseEntity<UserOnlineMedicalCards>(0, "成功");
        try {
            response.setData(userOnlineMedicalCardsService.updateLogicDelete(input));
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().length() > 0) {
                response.setMsg(e.getMessage());
            } else {
                response.setMsg("失败");
            }
            response.setCode(1002);
        }
        return response;
    }

    @ApiOperation(value = "查询一条", notes = "查询<T>")
    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<UserOnlineMedicalCards> findOne(@RequestBody UserOnlineMedicalCards input) throws Exception {
        JsonResponseEntity<UserOnlineMedicalCards> response = new JsonResponseEntity<UserOnlineMedicalCards>(0, "成功");
        try {
            response.setData(userOnlineMedicalCardsService.findOne(input));
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().length() > 0) {
                response.setMsg(e.getMessage());
            } else {
                response.setMsg("失败");
            }
            response.setCode(1002);
        }
        return response;
    }

    @ApiOperation(value = "通过用户ID查询列表", notes = "查询<T>")
    @RequestMapping(value = "/findAllByUid", method = RequestMethod.POST)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<List<UserOnlineMedicalCards>> findAllByUid(@RequestBody UserOnlineMedicalCards input) throws Exception {
        JsonResponseEntity<List<UserOnlineMedicalCards>> response = new JsonResponseEntity<List<UserOnlineMedicalCards>>(0, "成功");
        try {
            response.setData(userOnlineMedicalCardsService.findAllByUid(input));
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().length() > 0) {
                response.setMsg(e.getMessage());
            } else {
                response.setMsg("失败");
            }
            response.setCode(1002);
        }
        return response;
    }

    @ApiOperation(value = "通过用户ID和医院ID查询列表", notes = "查询<T>")
    @RequestMapping(value = "/findAllByUidAndHospitalCode", method = RequestMethod.POST)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<List<UserOnlineMedicalCards>> findAllByUidAndHospitalCode(@RequestBody UserOnlineMedicalCards input) throws Exception {
        JsonResponseEntity<List<UserOnlineMedicalCards>> response = new JsonResponseEntity<List<UserOnlineMedicalCards>>(0, "成功");
        try {
            response.setData(userOnlineMedicalCardsService.findAllByUidAndHospitalCode(input));
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().length() > 0) {
                response.setMsg(e.getMessage());
            } else {
                response.setMsg("失败");
            }
            response.setCode(1002);
        }
        return response;
    }

    @ApiOperation(value = "通过用户ID和类型编码查询列表", notes = "查询<T>")
    @RequestMapping(value = "/findAllByUidAndCardTypeCode", method = RequestMethod.POST)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<List<UserOnlineMedicalCards>> findAllByUidAndCardTypeCode(@RequestBody UserOnlineMedicalCards input) throws Exception {
        JsonResponseEntity<List<UserOnlineMedicalCards>> response = new JsonResponseEntity<List<UserOnlineMedicalCards>>(0, "成功");
        try {
            response.setData(userOnlineMedicalCardsService.findAllByUidAndCardTypeCode(input));
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().length() > 0) {
                response.setMsg(e.getMessage());
            } else {
                response.setMsg("失败");
            }
            response.setCode(1002);
        }
        return response;
    }

    @ApiOperation(value = "通过用户ID、医院ID、类型编码查询列表", notes = "查询<T>")
    @RequestMapping(value = "/findAllByUidAndHospitalCodeAndCardTypeCode", method = RequestMethod.POST)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<List<UserOnlineMedicalCards>> findAllByUidAndHospitalCodeAndCardTypeCode(@RequestBody UserOnlineMedicalCards input) throws Exception {
        JsonResponseEntity<List<UserOnlineMedicalCards>> response = new JsonResponseEntity<List<UserOnlineMedicalCards>>(0, "成功");
        try {
            response.setData(userOnlineMedicalCardsService.findAllByUidAndHospitalCodeAndCardTypeCode(input));
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().length() > 0) {
                response.setMsg(e.getMessage());
            } else {
                response.setMsg("失败");
            }
            response.setCode(1002);
        }
        return response;
    }


}
