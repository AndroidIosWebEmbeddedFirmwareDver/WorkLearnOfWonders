package com.wondersgroup.healthcloud.services.account.impl;

import com.wondersgroup.common.http.utils.JsonConverter;
import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.jpa.entity.user.AccountThirdParty;
import com.wondersgroup.healthcloud.jpa.repository.user.AccountThirdPartyRepository;
import com.wondersgroup.healthcloud.services.account.ThirdPartyHttpUtil;
import com.wondersgroup.healthcloud.services.account.ThirdPartyService;
import com.wondersgroup.healthcloud.services.account.exception.ThirdPartyPlatformExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
 * Created by zhangzhixiu on 16/3/4.
 */
@Transactional(readOnly = true)
@Service("thirdPartyServiceImpl")
public class ThirdPartyServiceImpl implements ThirdPartyService {

    private AccountThirdPartyRepository accountThirdPartyRepository;
    private ThirdPartyHttpUtil thirdPartyHttpUtil;

    public AccountThirdParty wechat(String token, String openid) {
        ThirdPartyHttpUtil.User wechatUser = thirdPartyHttpUtil.wechatUser(token, openid);
        AccountThirdParty account = findByPlatformAndId("1", wechatUser.id);
        if (account == null) {
            account = new AccountThirdParty();
            account.setId(IdGen.uuid());
            account.setPlatform("1");
            account.setPlatformId(wechatUser.id);
            account.setInfo(JsonConverter.toJson(wechatUser));
            return accountThirdPartyRepository.save(account);
        } else {
            return account;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public AccountThirdParty weibo(String token) {
        ThirdPartyHttpUtil.User weiboUser = thirdPartyHttpUtil.weiboUser(token);
        AccountThirdParty account = findByPlatformAndId("3", weiboUser.id);
        if (account == null) {
            account = new AccountThirdParty();
            account.setId(IdGen.uuid());
            account.setPlatform("3");
            account.setPlatformId(weiboUser.id);
            account.setInfo(JsonConverter.toJson(weiboUser));
            return accountThirdPartyRepository.save(account);
        } else {
            return account;
        }
    }

    @Transactional
    @Override
    public AccountThirdParty qq(String token) {
        ThirdPartyHttpUtil.User qqUser = thirdPartyHttpUtil.qqUser(token);
        AccountThirdParty account = findByPlatformAndId("2", qqUser.id);
        if (account == null) {
            account = new AccountThirdParty();
            account.setId(IdGen.uuid());
            account.setPlatform("2");
            account.setPlatformId(qqUser.id);
            account.setInfo(JsonConverter.toJson(qqUser));
            return accountThirdPartyRepository.save(account);
        } else {
            return account;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void bind(String thirdPartyId, String userId) {
        AccountThirdParty account = findOne(thirdPartyId);
        if (account.getUid() != null) {
            throw new RuntimeException();
        } else {
            AccountThirdParty exist = accountThirdPartyRepository.findByUserIdAndPlatform(userId, account.getPlatform());
            if (exist == null) {
                account.setUid(userId);
                accountThirdPartyRepository.save(account);
            } else {
                throw new ThirdPartyPlatformExistException();
            }
        }
    }

    private AccountThirdParty findOne(String id) {
        AccountThirdParty account = accountThirdPartyRepository.findOne(id);
        if (account == null) {
            throw new RuntimeException();//todo
        }
        return account;
    }

    private AccountThirdParty findByPlatformAndId(String platform, String platformId) {
        return accountThirdPartyRepository.findByPlatformAndId(platform, platformId);
    }

    @Override
    public List<AccountThirdParty> findByUserId(String userId) {
        return accountThirdPartyRepository.findByUserId(userId);
    }

    @Autowired
    public void setAccountThirdPartyRepository(AccountThirdPartyRepository accountThirdPartyRepository) {
        this.accountThirdPartyRepository = accountThirdPartyRepository;
    }

    @Autowired
    public void setThirdPartyHttpUtil(ThirdPartyHttpUtil thirdPartyHttpUtil) {
        this.thirdPartyHttpUtil = thirdPartyHttpUtil;
    }
}
