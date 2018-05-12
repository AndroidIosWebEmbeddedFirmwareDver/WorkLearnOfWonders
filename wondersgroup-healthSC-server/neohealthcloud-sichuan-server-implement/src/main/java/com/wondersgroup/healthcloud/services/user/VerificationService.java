package com.wondersgroup.healthcloud.services.user;

import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.jpa.entity.user.Verification;
import com.wondersgroup.healthcloud.services.user.dto.VerificationForm;

import java.util.List;
import java.util.Map;

/**
 * Created by longshasha on 16/11/4.
 */
public interface VerificationService {

    List<VerificationForm> findVerificationListByPager(int pageNum, int size, Map parameter);

    int countVerificationListByPager(Map parameter);

    Account verifiedUserById(Integer id);

    Boolean verificationSubmit(String id, String name, String idCard, String photo);

    int getVerificationLevelByAccount(Account account);

    Verification verficationSubmitInfo(String id);

    Account getAccountById(String userId);
}
