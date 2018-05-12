package com.wondersgroup.healthcloud.services.account.impl;

import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.im.easemob.users.EasemobAccount;
import com.wondersgroup.healthcloud.im.easemob.users.EasemobUserService;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.jpa.entity.user.AccountThirdParty;
import com.wondersgroup.healthcloud.jpa.repository.user.AccountRepository;
import com.wondersgroup.healthcloud.services.account.AccountPasswordDigestUtil;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.account.SessionUtil;
import com.wondersgroup.healthcloud.services.account.ThirdPartyService;
import com.wondersgroup.healthcloud.services.account.dto.AccountInfoAndSession;
import com.wondersgroup.healthcloud.services.account.dto.AccountInfoForm;
import com.wondersgroup.healthcloud.services.account.dto.AccountSignupForm;
import com.wondersgroup.healthcloud.services.account.dto.Session;
import com.wondersgroup.healthcloud.services.account.exception.*;
import com.wondersgroup.healthcloud.utils.CityCodeNameConverter;
import com.wondersgroup.healthcloud.utils.sms.VerificationFrequencyChecker;
import com.wondersgroup.healthcloud.utils.sms.VerifyCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p/>
 * Created by zhangzhixiu on 15/12/17.
 */
@Transactional(readOnly = true)
@Service("accountServiceImpl")
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private ThirdPartyService thirdPartyService;
    private VerifyCodeService verifyCodeService;
    private VerificationFrequencyChecker frequencyChecker;
    private SessionUtil sessionUtil;
    @Autowired
    private EasemobUserService easemobUserService;

    //注册环信
    private void registerEasemob(Account account) {
        if (StringUtils.isBlank(account.getTalkid())) {
            EasemobAccount easemobAccount = easemobUserService.createUserAccount();
            if (easemobAccount != null) {
                account.setTalkid(easemobAccount.id);
                account.setTalkpwd(easemobAccount.pwd);
            }
        }
    }

    @Transactional
    @Override
    public AccountInfoAndSession signin(String mobile, String password) {
        frequencyChecker.passwordCheckBeforeVerification(mobile);
        Account account = accountRepository.signin(mobile, AccountPasswordDigestUtil.digest(password));
        if (account != null) {
            return createSessionByAccount(account, false);
        } else {
            frequencyChecker.passwordMarkAfterFailure(mobile);
            throw new ErrorUsernameOrPasswordException();
        }
    }

    @Transactional
    @Override
    public AccountInfoAndSession signup(AccountSignupForm form) {
        if (!verifyCodeService.tempCheck(form.mobile, form.code)) {//注册后验证码能再次用于设置密码
            throw new ErrorVerifyCodeException();
        }
        Account account = getAccountByMobile(form.mobile);
        if (form.bindId == null) {//非三方登录, 直接注册
            if (account != null) {
                throw new SignupMobileExistException();
            }
            account = saveNewAccount(form);
        } else {//三方登录绑定
            if (account == null) {
                account = saveNewAccount(form);
            }
            thirdPartyService.bind(form.bindId, account.getId());
        }
        return createSessionByAccount(account, true);
    }

    private Account saveNewAccount(AccountSignupForm form) {
        Account account = form.newAccount();
        account.setNickname(CityCodeNameConverter.getName(form.cityCode) + "用户" + StringUtils.substring(form.mobile, 7));
        return accountRepository.save(account);
    }

    private AccountInfoAndSession createSessionByAccount(Account account, Boolean firstLogin) {
        //this.registerEasemob(account);
        Session session = sessionUtil.createUser(account.getId());
        return new AccountInfoAndSession(account, session, firstLogin);
    }

    private AccountInfoAndSession createSessionByThirdPartyAccount(Account account, AccountThirdParty accountThirdParty, Boolean firstLogin) {
        //this.registerEasemob(account);
        Session session = sessionUtil.createUser(account.getId());
        return new AccountInfoAndSession(account, session, accountThirdParty.getId(), firstLogin);
    }

    @Transactional
    @Override
    public AccountInfoAndSession codeSignin(String mobile, String code, String cityCode) {
        Account account = getAccountByMobile(mobile);
        if (account == null) {
            if (!verifyCodeService.tempCheck(mobile, code)) {
                throw new ErrorVerifyCodeException();
            }
        } else {
            if (!verifyCodeService.check(mobile, code)) {
                throw new ErrorVerifyCodeException();
            }
        }
        Boolean firstLogin = false;
        if (account == null) {
            firstLogin = true;
            account = new Account();
            account.setId(IdGen.uuid());
            account.setJoinTime(new Date());
            account.setMobile(mobile);
            account.setVerificationLevel(0);
            account.setNickname(CityCodeNameConverter.getName(cityCode) + "用户" + StringUtils.substring(mobile, 7));
            account = accountRepository.save(account);
        }
        return createSessionByAccount(account, firstLogin);
    }

    @Transactional
    @Override
    public AccountInfoAndSession wechatSignin(String token, String openid) {
        AccountThirdParty accountThirdParty = thirdPartyService.wechat(token, openid);
        String uid = accountThirdParty.getUid();
        if (uid == null) {
            return new AccountInfoAndSession(null, null, accountThirdParty.getId(), true);
        } else {
            Account account = accountRepository.findOne(uid);
            return createSessionByThirdPartyAccount(account, accountThirdParty, false);
        }
    }

    @Transactional
    @Override
    public AccountInfoAndSession weiboSignin(String token) {
        AccountThirdParty accountThirdParty = thirdPartyService.weibo(token);
        String uid = accountThirdParty.getUid();
        if (uid == null) {
            return new AccountInfoAndSession(null, null, accountThirdParty.getId(), true);
        } else {
            Account account = accountRepository.findOne(uid);
            return createSessionByThirdPartyAccount(account, accountThirdParty, false);
        }
    }

    @Transactional
    @Override
    public AccountInfoAndSession qqSignin(String token) {
        AccountThirdParty accountThirdParty = thirdPartyService.qq(token);
        String uid = accountThirdParty.getUid();
        if (uid == null) {
            return new AccountInfoAndSession(null, null, accountThirdParty.getId(), true);
        } else {
            Account account = accountRepository.findOne(uid);
            return createSessionByThirdPartyAccount(account, accountThirdParty, false);
        }
    }

    @Transactional
    @Override
    public Account updateInfo(AccountInfoForm form) {
        Account account = accountRepository.findOne(form.id);
        Account merged = form.merge(account);
        return accountRepository.save(merged);
    }

    @Override
    public Account info(String userId) {
        Account account = accountRepository.findOne(userId);
        if (account == null) {
            throw new CommonException(1011, "内部错误");
        }
        return account;
    }

    @Transactional
    @Override
    public void resetPassword(String mobile, String password, String code) {
        Account account = accountRepository.getAccountByMobile(mobile);
        if (account == null) {
            throw new MobileNotExistException();
        }
        if (!verifyCodeService.check(mobile, code)) {
            throw new ErrorVerifyCodeException();
        }
        account.setPassword(AccountPasswordDigestUtil.digest(password));
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void updatePassword(String userId, String previousPassword, String newPassword) {
        Account account = info(userId);
        if (account.getPassword() != null && account.getPassword().equals(AccountPasswordDigestUtil.digest(previousPassword))) {
            frequencyChecker.passwordCheckBeforeVerification(account.getId());
            account.setPassword(AccountPasswordDigestUtil.digest(newPassword));
            accountRepository.save(account);
        } else {
            frequencyChecker.passwordMarkAfterFailure(account.getId());
            throw new CommonException(1000, "原密码输入错误");
        }
    }

    @Transactional
    @Override
    public void addUser(String mobile) {
        Account account = new Account();
        account.setId(IdGen.uuid());
        account.setJoinTime(new Date());
        account.setMobile(mobile);
        account.setVerificationLevel(0);
        account.setNickname("微健康用户" + StringUtils.substring(mobile, 7));
        accountRepository.saveAndFlush(account);
    }

    @Override
    public Map<String, Account> infos(Iterable<String> userIds) {
        List<Account> accounts = accountRepository.findAll(userIds);
        Map<String, Account> rt = new HashMap<>();
        if (accounts != null) {
            for (Account account : accounts) {
                rt.put(account.getId(), account);
            }
        }
        return rt;
    }

    @Override
    public Account getAccountByMobile(String mobile) {
        return accountRepository.getAccountByMobile(mobile);
    }

    @Transactional
    @Override
    public void updateMobile(String userId, String newMobile, String code) {
        Account accountToUpdate = info(userId);
        Account checkMobileExist = getAccountByMobile(newMobile);
        if (checkMobileExist != null) {
            throw new UpdateMobileExistException();
        } else {
            if (!verifyCodeService.check(newMobile, code)) {
                throw new ErrorVerifyCodeException();
            }
            accountToUpdate.setMobile(newMobile);
            accountRepository.save(accountToUpdate);
        }
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setThirdPartyService(ThirdPartyService thirdPartyService) {
        this.thirdPartyService = thirdPartyService;
    }

    @Autowired
    public void setFrequencyChecker(VerificationFrequencyChecker frequencyChecker) {
        this.frequencyChecker = frequencyChecker;
    }

    @Autowired
    public void setVerifyCodeService(VerifyCodeService verifyCodeService) {
        this.verifyCodeService = verifyCodeService;
    }

    @Autowired
    public void setSessionUtil(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Transactional
    @Override
    public void platFormRegister(Account account){
        account.setId(IdGen.uuid());
        account.setJoinTime(new Date());
        account.setVerificationLevel(0);
        account.setNickname("微健康用户" + StringUtils.substring(account.getMobile(), 7));
        accountRepository.saveAndFlush(account);
    }

}
