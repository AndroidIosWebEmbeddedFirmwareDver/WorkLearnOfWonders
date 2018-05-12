package com.wondersgroup.healthcloud.api.http.controllers.admin.user;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.services.account.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dukuanxin on 2017/9/22.
 */
@RestController
@RequestMapping("/api/platform")
@Admin
public class OtherPlatformUser {
    @Autowired
    private AccountService accountService;

    @GetMapping("/user/add")
    public JsonResponseEntity<String> add(@RequestParam String mobile) {
        JsonResponseEntity<String> response = new JsonResponseEntity<String>();
        Account accountByMobile = accountService.getAccountByMobile(mobile);
        if (mobile.length() != 11 || !mobile.startsWith("1") || !StringUtils.isNumeric(mobile)) {
            throw new CommonException(1000, "手机号码格式不正确, 请重新输入");
        }
        if (null == accountByMobile) {
            accountService.addUser(mobile);
            response.setMsg("添加成功");
        } else {
            response.setCode(-1);
            response.setMsg("账号已存在");
        }
        return response;
    }
}
