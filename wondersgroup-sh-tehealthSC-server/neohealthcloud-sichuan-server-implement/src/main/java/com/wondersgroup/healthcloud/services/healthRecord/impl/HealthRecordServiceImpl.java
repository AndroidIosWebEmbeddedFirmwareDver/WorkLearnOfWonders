package com.wondersgroup.healthcloud.services.healthRecord.impl;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.StringResponseWrapper;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.healthRecord.HealthRecordService;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.*;
import com.wondersgroup.healthcloud.utils.AreaResourceUrl;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;


/**
 * Created by ys on 2016/11/01.
 */
@Service("healthRecordService")
public class HealthRecordServiceImpl implements HealthRecordService {

    @Autowired
    private JedisPool jedisPool;

    /*@Value("${healthRecord.service.url}")
    private String serviceHost;*/

    @Autowired
    private AccountService accountService;

    @Value("${healthRecord.cache.enable}")
    private String enableCacheHealthRecord = "1";

    @Autowired
    private AreaResourceUrl areaResourceUrl;

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

    /**
     * 门诊就诊记录
     */
    @Override
    public HealthJiuZhenListResponse getJiuZhenList(String uid, int page, int pageSize, String cityCode) {
        String idc = this.getIdCardAndLogin(uid, false, cityCode);
        String cache_key = String.format("v1_HealthRecordServiceImpl_getJiuZhenList_idc_%s_%s_%s", idc, page, pageSize);
        String responseBody;
        HealthJiuZhenListResponse response;
        try (Jedis jedis = jedisPool.getResource()) {
            if (!jedis.exists(cache_key)) {
                responseBody = this.curlPost("?st=outpatient.getMedicalRecord",
                        new String[]{"cardNum", idc, "cardType", "01", "pageSize", String.valueOf(pageSize), "currentPage", String.valueOf(page)}, cityCode);
            } else {
                responseBody = jedis.get(cache_key);
            }
            response = JaxbUtil.convertToJavaBean(responseBody, HealthJiuZhenListResponse.class);
            if (!response.getResultCode().equals("0") || response.getTotalPage() == 0) {
                throw new CommonException(2021, response.getResultDesc());
            }
            jedis.set(cache_key, responseBody);
            jedis.expire(cache_key, 86400);
        }
        return response;
    }

    @Override
    public HealthJianYanListResponse getJianYanList(String uid, int page, int pageSize, String cityCode) {
        String idc = this.getIdCardAndLogin(uid, false, cityCode);
        String cache_key = String.format("v1_HealthRecordServiceImpl_getJianYanList_idc_%s_%s_%s", idc, page, pageSize);
        String responseBody;
        HealthJianYanListResponse response;
        try (Jedis jedis = jedisPool.getResource()) {
            if (!jedis.exists(cache_key)) {
                responseBody = this.curlPost("?st=outpatient.getLisRecord",
                        new String[]{"cardNum", idc, "cardType", "01", "pageSize", String.valueOf(pageSize), "currentPage", String.valueOf(page)}, cityCode);
            } else {
                responseBody = jedis.get(cache_key);
            }
            response = JaxbUtil.convertToJavaBean(responseBody, HealthJianYanListResponse.class);
            if (!response.getResultCode().equals("0") || response.getTotalPage() == 0) {
                throw new CommonException(2021, response.getResultDesc());
            }
            jedis.set(cache_key, responseBody);
            jedis.expire(cache_key, 86400);
        }
        return response;
    }

    @Override
    public HealthJianYanDetailResponse getJianYanDetail(String rid, String rNo, String date, String uid, String cityCode) {
        String idc = this.getIdCardAndLogin(uid, false, cityCode);
        String cache_key = String.format("v1_HealthRecordServiceImpl_getJianYanDetail_idc_%s_%s_%s_%s", idc, rid, rNo, date);
        String responseBody;
        HealthJianYanDetailResponse response;
        try (Jedis jedis = jedisPool.getResource()) {
            if ("0".equals(enableCacheHealthRecord) || !jedis.exists(cache_key)) {
                responseBody = this.curlPost("?st=outpatient.getLisDetail",
                        new String[]{"rid", rid, "cardId", rNo, "cardDate", date, "cardNum", idc, "cardType", "01"}, cityCode);
            } else {
                responseBody = jedis.get(cache_key);
            }
            response = JaxbUtil.convertToJavaBean(responseBody, HealthJianYanDetailResponse.class);
            if (!response.getResultCode().equals("0")) {
                throw new CommonException(2021, response.getResultDesc());
            }
            jedis.set(cache_key, responseBody);
            jedis.expire(cache_key, 86400);
        }
        return response;
    }

    @Override
    public HealthZhuYuanListResponse getZhuYuanList(String uid, int page, int pageSize, String cityCode) {
        String idc = this.getIdCardAndLogin(uid, false, cityCode);
        String cache_key = String.format("v1_HealthRecordServiceImpl_getZhuYuanList_idc_%s_%s_%s", idc, page, pageSize);
        String responseBody;
        HealthZhuYuanListResponse response;
        try (Jedis jedis = jedisPool.getResource()) {
            if ("0".equals(enableCacheHealthRecord) || !jedis.exists(cache_key)) {
                responseBody = this.curlPost("?st=inpatient.getMedicalRecord",
                        new String[]{"cardNum", idc, "cardType", "01", "pageSize", String.valueOf(pageSize), "currentPage", String.valueOf(page)}, cityCode);
            } else {
                responseBody = jedis.get(cache_key);
            }
            response = JaxbUtil.convertToJavaBean(responseBody, HealthZhuYuanListResponse.class);
            if (!response.getResultCode().equals("0") || response.getTotalPage() == 0) {
                throw new CommonException(2021, response.getResultDesc());
            }
            jedis.set(cache_key, responseBody);
            jedis.expire(cache_key, 86400);
        }
        return response;
    }

    @Override
    public HealthZhuYuanDetailResponse getZhuYuanDetail(String medicalOrgId, String serialNum, String uid, String cityCode) {
        String idc = this.getIdCardAndLogin(uid, false, cityCode);
        String cache_key = String.format("v1_HealthRecordServiceImpl_getZhuYuanDetail_idc_%s_%s_%s", idc, medicalOrgId, serialNum);
        String responseBody;
        HealthZhuYuanDetailResponse response;
        try (Jedis jedis = jedisPool.getResource()) {
            if ("0".equals(enableCacheHealthRecord) || !jedis.exists(cache_key)) {
                responseBody = this.curlPost("?st=inpatient.getDischargeAbstract",
                        new String[]{"medicalOrgId", medicalOrgId, "serialNum", serialNum, "cardNum", idc, "cardType", "01"}, cityCode);
            } else {
                responseBody = jedis.get(cache_key);
            }
            response = JaxbUtil.convertToJavaBean(responseBody, HealthZhuYuanDetailResponse.class);
            if (!response.getResultCode().equals("0") || null == response.getHosDischargeAbstractVo().getId()) {
                throw new CommonException(2021, response.getResultDesc());
            }
            jedis.set(cache_key, responseBody);
            jedis.expire(cache_key, 86400);
        }
        return response;
    }

    @Override
    public HealthYongYaoListResponse getYongYaoList(String uid, int page, int pageSize, String cityCode) {
        String idc = this.getIdCardAndLogin(uid, false, cityCode);
        String cache_key = String.format("v1_HealthRecordServiceImpl_getYongYaoList_idc_%s_%s_%s", idc, page, pageSize);
        String responseBody;
        HealthYongYaoListResponse response;
        try (Jedis jedis = jedisPool.getResource()) {
            if ("0".equals(enableCacheHealthRecord) || !jedis.exists(cache_key)) {
                responseBody = this.curlPost("?st=outpatient.getPrescriptionRecord",
                        new String[]{"cardNum", idc, "cardType", "01", "pageSize", String.valueOf(pageSize), "currentPage", String.valueOf(page)}, cityCode);
            } else {
                responseBody = jedis.get(cache_key);
            }
            response = JaxbUtil.convertToJavaBean(responseBody, HealthYongYaoListResponse.class);
            if (!response.getResultCode().equals("0") || response.getTotalPage() == 0) {
                throw new CommonException(2021, response.getResultDesc());
            }
            jedis.set(cache_key, responseBody);
            jedis.expire(cache_key, 86400);
        }
        return response;
    }

    @Override
    public HealthYongYaoDetailResponse getYongYaoDetail(String prescriptionId, String uid, String cityCode) {
        String idc = this.getIdCardAndLogin(uid, false, cityCode);
        String cache_key = String.format("v1_HealthRecordServiceImpl_getYongYaoDetail_idc_%s_%s", idc, prescriptionId);
        String responseBody;
        HealthYongYaoDetailResponse response;
        try (Jedis jedis = jedisPool.getResource()) {
            if ("0".equals(enableCacheHealthRecord) || !jedis.exists(cache_key)) {
                responseBody = this.curlPost("?st=outpatient.getPrescriptionDetail",
                        new String[]{"prescriptionId", prescriptionId, "cardNum", idc, "cardType", "01"}, cityCode);
            } else {
                responseBody = jedis.get(cache_key);
            }
            response = JaxbUtil.convertToJavaBean(responseBody, HealthYongYaoDetailResponse.class);
            if (!response.getResultCode().equals("0") || response.getTotalPage() == 0) {
                throw new CommonException(2021, response.getResultDesc());
            }
            jedis.set(cache_key, responseBody);
            jedis.expire(cache_key, 86400);
        }
        return response;
    }

    private String curlPost(String url, String[] parms, String cityCode) {
        String str = "";
        StringBuilder sb = new StringBuilder(areaResourceUrl.getUrl("4", cityCode) + url);
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
                    .params(parms)
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
    private String getIdCardAndLogin(String uid, Boolean getRealData, String cityCode) {
        String idc = "";
        try (Jedis jedis = jedisPool.getResource()) {
            String cache_key = "healthRecord-cd-login-pid-uid-" + uid;
            if (!jedis.exists(cache_key) || getRealData) {
                Account account = accountService.info(uid);
                if (StringUtils.isEmpty(account.getIdcard())) {
                    throw new CommonException(2021, "无效用户");
                }
                idc = account.getIdcard();
                String loginUrl = areaResourceUrl.getUrl("4", cityCode) + "?st=login&accessType=2&cardNum=" + idc + "&cardType=01";
                Request request = new RequestBuilder().post().url(loginUrl).build();
                StringResponseWrapper response = (StringResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(StringResponseWrapper.class);
                String body = response.body();
                HealthLoginResponse info = JaxbUtil.convertToJavaBean(body, HealthLoginResponse.class);
                if (!info.getResultCode().equals("0")) {
                    throw new CommonException(2021, "暂无数据");
                }
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
