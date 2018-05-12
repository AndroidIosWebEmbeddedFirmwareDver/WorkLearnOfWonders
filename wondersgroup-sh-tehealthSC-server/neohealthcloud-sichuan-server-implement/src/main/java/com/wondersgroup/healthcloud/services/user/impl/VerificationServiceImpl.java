package com.wondersgroup.healthcloud.services.user.impl;

import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.config.client.OrderClient;
import com.wondersgroup.healthcloud.config.client.PlatformRegisterClient;
import com.wondersgroup.healthcloud.entity.RequestMessageHeaderUtil;
import com.wondersgroup.healthcloud.entity.request.RegisterOrUpdateUserInfoRequest;
import com.wondersgroup.healthcloud.entity.response.RegisterOrUpdateUserInfoResponse;
import com.wondersgroup.healthcloud.entity.response.SubmitOrderByUserInfoResponse;
import com.wondersgroup.healthcloud.jpa.entity.config.AppConfig;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.jpa.entity.user.Verification;
import com.wondersgroup.healthcloud.jpa.repository.user.AccountRepository;
import com.wondersgroup.healthcloud.jpa.repository.user.VerificationRepository;
import com.wondersgroup.healthcloud.services.config.AppConfigService;
import com.wondersgroup.healthcloud.services.user.VerificationService;
import com.wondersgroup.healthcloud.services.user.dto.VerificationForm;
import com.wondersgroup.healthcloud.services.user.exception.*;
import com.wondersgroup.healthcloud.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.BusFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by longshasha on 16/11/4.
 */
@Service("verificationServiceImpl")
@Transactional(readOnly = true)
public class VerificationServiceImpl implements VerificationService {

    private static final Logger log = Logger.getLogger(VerificationServiceImpl.class);

    private static final String add_paltform_user = "http://218.89.178.119:8089/webServiceOrderRegCDS/services/BookingWebServiceImpl?wsdl";

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SignatureGenerator signatureGenerator;

    @Autowired
    private RequestMessageHeaderUtil reqMesHeaderUtil;

    @Autowired
    private PlatformRegisterClient platformRegisterClient;

    @Autowired
    private AreaResourceUrl areaResourceUrl;

    @Autowired
    private OrderClient orderClient;

    private String verificationSql = " select v.id,v.uid,v.`name`,v.idcard,v.photo," +
            " v.verification_level,v.refusal_reason,a.mobile , date_format(v.create_time,'%Y-%m-%d %H:%i:%s') as create_time  " +
            " from tb_user_verification v " +
            " left join tb_user_account a on v.uid = a.id ";

    @Override
    public List<VerificationForm> findVerificationListByPager(int pageNum, int size, Map parameter) {

        String sql = verificationSql + " where 1=1  " +
                getWhereSqlByParameter(parameter)
                + " order by v.verification_level asc, v.create_time desc "
                + " LIMIT " + (pageNum - 1) * size + "," + size;
        RowMapper<VerificationForm> rowMapper = new VerificationFormRowMapper();
        List<VerificationForm> verificationFormList = jt.query(sql, rowMapper);
        return verificationFormList;
    }

    @Override
    public int countVerificationListByPager(Map parameter) {
        String sql = " select count(v.id) " +
                " from tb_user_verification v " +
                " left join tb_user_account a on v.uid = a.id " +
                " where 1=1 " + getWhereSqlByParameter(parameter);
        Integer count = jt.queryForObject(sql, Integer.class);
        return count == null ? 0 : count;
    }

    /**
     * 后台管理 审核通过实名认证信息
     *
     * @param id 实名认证的信息Id
     * @return
     */
    @Override
    @Transactional
    public Account verifiedUserById(Integer id) {
        Verification verification = verificationRepository.getOne(id);
        if (verification == null || verification.getVerificationLevel() != 0) {
            throw new VerificationManageException();
        }
        verification.setId(id);
        verification.setVerificationLevel(1);
        verificationRepository.save(verification);

        Account account = accountRepository.getOne(verification.getUid());

        account.setName(verification.getName());
        account.setIdcard(verification.getIdcard());
        account.setGender(IdcardUtils.getGenderByIdCard(verification.getIdcard()));
        account.setBirthday(DateFormatter.parseIdCardDate(IdcardUtils.getBirthByIdCard(verification.getIdcard())));
        account.setVerificationLevel(verification.getVerificationLevel());
        accountRepository.save(account);

        //调绵阳平台注册接口，同步用户信息
        register2PlatformMY(account);

        return account;
    }


    /**
     * 实名认证信息提交
     *
     * @param id
     * @param name
     * @param idCard
     * @param photo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public Boolean verificationSubmit(String id, String name, String idCard, String photo) {

        Account account = accountRepository.findOne(id);

        if (account == null) {
            throw new ErrorAccountNotExitException();
        }

        String birth = IdcardUtils.getBirthByIdCard(idCard);
        Date birDate = DateFormatter.parseIdCardDate(birth);
        Date now = new Date();
        if (DateUtils.compareDate(birDate, now) > 0) {
            throw new ErrorIdcardException("身份证的出生日期不能大于当前时间");
        }

        if (name.length() < 2 || name.length() > 20) {
            throw new ErrorNameVerificationException("姓名的长度范围为2到20个字");
        }

        String cleanName = EmojiUtils.cleanEmoji(name);
        if (name.length() > cleanName.length()) {
            throw new ErrorNameVerificationException("姓名是不能包含表情的噢");
        }

        //获取当前用户的实名认证状态
        int verificationLevel = getVerificationLevelByAccount(account);
        if (verificationLevel >= 0) {
            throw new ErrorResubmitVerificationException();
        }

        //一个身份证号只能对应一个账户 有审核中或者审核成功的身份证号不能再次提交
        List<Verification> verifications = verificationRepository.findVerificationsByVerificationLevel(idCard);
        if (verifications.size() > 0) {
            throw new ErrorResubmitVerificationException("身份证号已被使用");
        }

        Boolean automaticVerification = false;

        AppConfig appConfig = appConfigService.findSingleAppConfigByKeyWord("automaticVerification");
        if (appConfig != null) {
            automaticVerification = "1".equals(appConfig.getData()) ? true : false;
        }
        Verification verification = new Verification();
        verification.setUid(id);
        verification.setName(name);
        verification.setIdcard(idCard);
        verification.setPhoto(photo);
        Date date = new Date();
        verification.setCreateTime(date);
        verification.setUpdateTime(date);
        //如果是自动实名认证开关打开
        if (automaticVerification) {
            account.setVerificationLevel(1);
            account.setName(name);
            account.setIdcard(idCard);
            account.setGender(IdcardUtils.getGenderByIdCard(idCard));
            account.setBirthday(DateFormatter.parseIdCardDate(IdcardUtils.getBirthByIdCard(idCard)));
            accountRepository.save(account);
            //addPlatformUser(account);//往平台同步用户信息
            //调绵阳平台注册接口，同步用户信息
            register2PlatformMY(account);
            verification.setVerificationLevel(1);
        }
        verificationRepository.save(verification);

        return true;
    }

    /**
     * 获取实名认证状态
     *
     * @param account
     * @return -2:未提交过,-1审核失败,0:审核中,1:认证成功
     */
    @Override
    public int getVerificationLevelByAccount(Account account) {
        int verificationLevel = account.getVerificationLevel();
        if (!verified(verificationLevel)) {
            Verification verification = verificationRepository.findLatestVerificationByUid(account.getId());
            if (verification == null) {
                verificationLevel = -2;
            } else {
                verificationLevel = verification.getVerificationLevel();
            }
        }
        return verificationLevel;
    }

    /**
     * 查询实名认证信息
     *
     * @param id
     * @return
     */
    @Override
    public Verification verficationSubmitInfo(String id) {
        Verification verification = verificationRepository.findLatestVerificationByUid(id);
        return verification;
    }

    @Override
    public Account getAccountById(String userId) {
        Account account = accountRepository.findOne(userId);
        if (account == null) {
            throw new RuntimeException();
        }
        return account;
    }

    private boolean verified(int verificationLevel) {

        return verificationLevel == 1;
    }

    private String getWhereSqlByParameter(Map parameter) {
        StringBuffer bf = new StringBuffer();
        if (parameter.size() > 0) {
            if (parameter.containsKey("mobile") && StringUtils.isNotBlank(parameter.get("mobile").toString())) {
                bf.append(" and a.mobile like '%" + parameter.get("mobile").toString() + "%' ");
            }
            if (parameter.containsKey("name") && StringUtils.isNotBlank(parameter.get("name").toString())) {
                bf.append(" and v.name like '%" + parameter.get("name").toString() + "%' ");
            }
            if (parameter.containsKey("idcard") && StringUtils.isNotBlank(parameter.get("idcard").toString())) {
                bf.append(" and v.idcard like '%" + parameter.get("idcard").toString() + "%' ");
            }
            if (parameter.containsKey("verificationLevel") && StringUtils.isNotBlank(parameter.get("verificationLevel").toString())) {
                bf.append(" and v.verification_level = " + parameter.get("verificationLevel").toString());
            }
        }
        return bf.toString();
    }

    private class VerificationFormRowMapper implements RowMapper<VerificationForm> {

        @Override
        public VerificationForm mapRow(ResultSet rs, int i) throws SQLException {
            VerificationForm verificationForm = new VerificationForm();
            verificationForm.setId(Integer.valueOf(rs.getString("id")));
            verificationForm.setUid(rs.getString("uid"));
            verificationForm.setMobile(rs.getString("mobile"));
            verificationForm.setName(rs.getString("name"));
            verificationForm.setIdcard(rs.getString("idcard"));
            if (IdcardUtils.validateCard(rs.getString("idcard"))) {
                verificationForm.setGender(IdcardUtils.getGenderByIdCard(rs.getString("idcard")));
            }
            verificationForm.setPhoto(rs.getString("photo"));
            verificationForm.setVerificationLevel(Integer.valueOf(rs.getString("verification_level")));
            verificationForm.setRefusalReason(rs.getString("refusal_reason"));
            verificationForm.setCreateTime(rs.getString("create_time"));
            return verificationForm;
        }
    }

    private void addPlatformUser(Account account) {

        Object[] wsresult = null;
        JaxWsDynamicClientFactory factory = null;
        factory = JaxWsDynamicClientFactory.newInstance(BusFactory.newInstance().createBus());
        Client client = factory.createClient(add_paltform_user);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(3000);
        httpClientPolicy.setAllowChunking(false);
        httpClientPolicy.setReceiveTimeout(3000);
        http.getTarget().getAddress().setValue(add_paltform_user);
        http.setClient(httpClientPolicy);
        try {
            String param = "<Request>\n" +
                    "  <MessageHeader>\n" +
                    "<frontproviderId>wdyy</frontproviderId>\n" +
                    "<inputCharset>gbk</inputCharset>\n" +
                    "<signType>MD5</signType>\n" +
                    "<sign>wker4d22efba4kfjg5b18769779mesk5</sign>\n" +
                    "</MessageHeader>\n" +
                    "<UserInfo>\n" +
                    "    <userCardId>" + account.getIdcard() + "</userCardId>\n" +
                    "    <userCardType>01</userCardType>\n" +
                    "    <userPhone>" + account.getMobile() + "</userPhone>\n" +
                    "    <userName>" + account.getName() + "</userName>\n" +
                    "    <identifyCode>0</identifyCode>\n" +
                    "    <userBD>" + DateUtils.getDate(account.getBirthday()) + "</userBD>\n" +
                    "    <userSex>" + account.getGender() + "</userSex>\n" +
                    "</UserInfo></Request>";

            wsresult = client.invoke("RegisterOrUpdateUserInfoService", param);
            log.info("addPlatformUser  param-->" + param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String reString = String.valueOf(wsresult[0]);
        log.info("addPlatformUser -->" + reString);
    }

    /**
     * 绵阳平台用户同步
     * @param account
     * @return
     */
    private RegisterOrUpdateUserInfoResponse.Result register2PlatformMY(Account account){
        if(!register2PlatformMYCheck(account))//接口参数必填检查
            return null;

        RegisterOrUpdateUserInfoRequest registerOrUpdateUserInfoReq = new RegisterOrUpdateUserInfoRequest();
        registerOrUpdateUserInfoReq.requestMessageHeader = reqMesHeaderUtil.generator();
        registerOrUpdateUserInfoReq.userInfo = accountToWSUserInfo(account);
        registerOrUpdateUserInfoReq.requestMessageHeader.setSign(signatureGenerator.generateSignature(registerOrUpdateUserInfoReq));// 设置签名
        platformRegisterClient.setDefaultUri(areaResourceUrl.getUrl("1", "510701000000"));//绵阳平台接口地址
        RegisterOrUpdateUserInfoResponse wsResp = platformRegisterClient.registerOrUpdateUserInfo(JaxbUtil.convertToXml(registerOrUpdateUserInfoReq, "GBK"));
        if (wsResp != null && wsResp.getResult() != null) {
            account.setPlatformUserId(wsResp.getResult().getPlatformUserId());
            accountRepository.save(account);
        }

        return wsResp.getResult();
    }

    private boolean register2PlatformMYCheck(Account account){
        return account != null && StringUtils.isNotEmpty(account.getIdcard())
                    && StringUtils.isNotEmpty(account.getMobile()) && StringUtils.isNotEmpty(account.getName());
    }

    private RegisterOrUpdateUserInfoRequest.UserInfo accountToWSUserInfo(Account account){
        RegisterOrUpdateUserInfoRequest.UserInfo userInfo = new RegisterOrUpdateUserInfoRequest.UserInfo();
        userInfo.setUserCardId(account.getIdcard());
        userInfo.setUserCardType("01");
        userInfo.setUserPhone(account.getMobile());
        userInfo.setUserName(account.getName());
        userInfo.setPassWord(account.getPassword());
        userInfo.setIdentifyCode("0");
        userInfo.setUserSex(account.getGender());
        userInfo.setUserBD("");
        userInfo.setUserContAdd("");
        userInfo.setUserEmail("");
        return userInfo;
    }

}
