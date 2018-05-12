package com.wondersgroup.healthcloud.services.healthRecord.impl;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.StringResponseWrapper;
import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.config.ServerConfigService;
import com.wondersgroup.healthcloud.services.healthRecord.HealthOnlineService;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.*;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by ys on 2016/11/08
 */
@Service("healthOnlineService")
public class HealthOnlineServiceImpl implements HealthOnlineService {

    @Autowired
    private JedisPool jedisPool;

    @Value("${healthOnline.service.url}")
    private String serviceHost;

    @Autowired
    private ServerConfigService serverConfigService;

    @Autowired
    private AccountService accountService;
    @Autowired
    private HospitalService hospitalService;

    //    private static HttpRequestExecutorManager httpRequestExecutorManager=new HttpRequestExecutorManager(new OkHttpClient());
    private static HttpRequestExecutorManager httpRequestExecutorManager;


    static {
        /**
         * 在okhttp3之前的版本可以按如下方法设置超时时间：
         *
         * client = new OkHttpClient();
         *
         *   client.setConnectTimeout(10, TimeUnit.SECONDS);
         *
         *   client.setWriteTimeout(10, TimeUnit.SECONDS);
         *
         *   client.setReadTimeout(30, TimeUnit.SECONDS);
         *
         * 2
         * 升级到okhttp3的时候，原有的设置方法被移到了builder中：
         *
         * client = new OkHttpClient.Builder()
         *
         *             .connectTimeout(10, TimeUnit.SECONDS)
         *
         *             .readTimeout(20, TimeUnit.SECONDS)
         *
         *             .build();
         *
         * 3
         * 以上即为在okhttp中设置超时的办法，很简单吧。
         */
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        httpRequestExecutorManager = new HttpRequestExecutorManager(okHttpClient);
    }

    @Override
    public HealthOnlineJianYanListResponse getJianYanList(String uid, String medicalOrgId, Integer timeFlag) {
        String idc = getIdCard(uid);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        String nowDate = DateUtils.sdf_day.format(calendar.getTime());
        String startDate = getDateByTimeFlag(timeFlag);
        String responseBody = this.curlPost(medicalOrgId, "?st=examTest.getLisRecord",
                new String[]{"idCard", idc, "medicalOrgId", medicalOrgId, "endDate", nowDate, "startDate", startDate});
        HealthOnlineJianYanListResponse jianYanListResponse = JaxbUtil.convertToJavaBean(responseBody, HealthOnlineJianYanListResponse.class);
        if (null == jianYanListResponse) {
            throw new CommonException(2011, "暂无相关信息");
        }
        if (!jianYanListResponse.getResultCode().equals("0")) {
            throw new CommonException(2021, "暂无相关信息");
        }
        return jianYanListResponse;
    }

    /**
     * 根据医院 查询所属区域 然后获取该区域的配置地址
     */
    private String getRequestServiceHost(String medicalOrgId) {
        Hospital hospital = hospitalService.findByHospitalCode(medicalOrgId);
        if (null == hospital || StringUtils.isEmpty(hospital.getCityCode())) {
            throw new CommonException(1000, "暂无相关信息");
        }
        String host = serverConfigService.queryApiUrl(hospital.getCityCode(), "3");
        if (StringUtils.isEmpty(host)) {
            return serviceHost;
        }
        return host;
    }

    @Override
    public HealthOnlineJianYanDetailResponse getJianYanDetail(String id) {
        String[] parms = id.split("-\\|-");
        if (parms.length != 4) {
            throw new CommonException(2001, "id格式无效");
        }
        String responseBody = this.curlPost(parms[0], "?st=examTest.getLisDetail",
                new String[]{"medicalOrgId", parms[0], "rid", parms[1], "cardId", parms[2], "cardDate", parms[3]});
        HealthOnlineJianYanDetailResponse detailResponse = JaxbUtil.convertToJavaBean(responseBody, HealthOnlineJianYanDetailResponse.class);
        if (!detailResponse.getResultCode().equals("0")) {
            throw new CommonException(2021, "暂无相关信息");
        }
        return detailResponse;
    }

    @Override
    public HealthOnlineJianChaListResponse getJianChaList(String uid, String medicalOrgId, Integer timeFlag) {
        String idc = getIdCard(uid);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        String nowDate = DateUtils.sdf_day.format(calendar.getTime());
        String startDate = getDateByTimeFlag(timeFlag);
        String responseBody = this.curlPost(medicalOrgId, "?st=examTest.getRisRecord",
                new String[]{"idCard", idc, "medicalOrgId", medicalOrgId, "endDate", nowDate, "startDate", startDate});
        HealthOnlineJianChaListResponse listResponse = JaxbUtil.convertToJavaBean(responseBody, HealthOnlineJianChaListResponse.class);
        if (null == listResponse) {
            throw new CommonException(2011, "暂无相关信息");
        }
        if (!listResponse.getResultCode().equals("0")) {
            throw new CommonException(2021, "暂无相关信息");
        }
        return listResponse;
    }

    private String getDateByTimeFlag(Integer timeFlag) {
        Calendar calendar = Calendar.getInstance();
        switch (timeFlag) {
            case 1:
                calendar.add(Calendar.MONTH, -1); // 最近一个月
                break;
            case 2:
                calendar.add(Calendar.MONTH, -6); // 最近6个月
            default:
                break;
        }
        String startDate = DateUtils.sdf_day.format(calendar.getTime());
        return startDate;
    }

    @Override
    public HealthOnlineJianChaDetailResponse getJianChaDetail(String id) {
        String[] parms = id.split("-\\|-");
        if (parms.length != 4) {
            throw new CommonException(2001, "id格式无效");
        }
        String responseBody = this.curlPost(parms[0], "?st=examTest.getRisDetail",
                new String[]{"medicalOrgId", parms[0], "cardId", parms[1], "serialNum", parms[2], "applyTime", parms[3]});
        HealthOnlineJianChaDetailResponse detailResponse = JaxbUtil.convertToJavaBean(responseBody, HealthOnlineJianChaDetailResponse.class);
        if (null == detailResponse) {
            throw new CommonException(2011, "暂无相关信息");
        }
        if (!detailResponse.getResultCode().equals("0")) {
            throw new CommonException(2021, "暂无相关信息");
        }
        return detailResponse;
    }

    private String curlPost(String medicalOrgId, String url, String[] parms) {
        String str = "";
        StringBuilder sb = new StringBuilder(getRequestServiceHost(medicalOrgId) + url);
        if (null != parms && parms.length > 0) {
            if (!url.contains("?")) {
                sb.append("?");
            }
            for (int i = 0; i < parms.length; ) {
                sb.append("&" + parms[i] + "=" + parms[i + 1]);
                i = i + 2;
            }
        }
        try {
            Request request = new RequestBuilder().post()
                    .url(sb.toString()).build();
            StringResponseWrapper response = (StringResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(StringResponseWrapper.class);
            str = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取厂商需要的pid
     *
     * @param uid 身份证
     * @return
     */
    private String getIdCard(String uid) {
        //方便调试
        if (uid.startsWith("0-")) {
            return uid.substring(2);
        }
        String idc = "";
        try (Jedis jedis = jedisPool.getResource()) {
            String cache_key = "healthRecord-cd-getIdCard-pid-uid-" + uid;
            if (!jedis.exists(cache_key) || 1 == 1) {
                Account account = accountService.info(uid);
                if (StringUtils.isEmpty(account.getIdcard())) {
                    throw new CommonException(2021, "无效用户");
                }
                idc = account.getIdcard();
                jedis.set(cache_key, idc);
                jedis.expire(cache_key, 86400);
            } else {
                idc = jedis.get(cache_key);
            }
        }
        if (StringUtils.isEmpty(idc)) {
            throw new CommonException(2021, "无效用户");
        }
        return idc;
    }
}
