package com.wondersgroup.healthcloud.jpa.repository.user;

import com.wondersgroup.healthcloud.jpa.entity.user.AccountThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
public interface AccountThirdPartyRepository extends JpaRepository<AccountThirdParty, String> {

    @Query("select atp from AccountThirdParty atp where atp.platform=?1 and atp.platformId=?2")
    AccountThirdParty findByPlatformAndId(String platform, String platformId);

    @Query("select atp from AccountThirdParty atp where atp.uid=?1")
    List<AccountThirdParty> findByUserId(String userId);

    @Query("select atp from AccountThirdParty atp where atp.uid=?1 and atp.platform=?2")
    AccountThirdParty findByUserIdAndPlatform(String userId, String platform);
}