package com.wondersgroup.healthcloud.api.helper;


import com.wondersgroup.healthcloud.utils.security.RSA;
import com.wondersgroup.healthcloud.utils.security.RSAKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * Created by Administrator on 2015/11/23.
 */
public class PasswordHelper {


    public static String encryptPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public static void main(String[] args) {
//        System.out.println(PasswordHelper.encryptPassword("123456"));

        //获取可预约资源
//        String str = StringUtils.lowerCase("endtime=2016-11-11&frontproviderid=wdjky&hosdeptcode=13782" +
//                "&hosdoctcode=7941&hosorgcode=42500377601&inputcharset=utf-8" +
//                "&starttime=2016-11-08&tophosdeptcode=03wdjky");


        //获取医院信息
//        String str = StringUtils.lowerCase("frontproviderId=wdjky&inputCharset=utf-8wdjky");

        //获取一级科室
//        String str = StringUtils.lowerCase("frontproviderId=wdjky&hosOrgCode=42500377601&inputCharset=utf-8wdjky");

        //获取二级科室
//        String str = StringUtils.lowerCase("frontproviderId=wdjky&&hosOrgCode=42500377601&inputCharset=utf-8&topHosDeptCode=03wdjky");

        //获取医生列表信息
//        String str = StringUtils.lowerCase("frontproviderId=wdjky&hosDeptCode=13782&hosOrgCode=42500377601&inputCharset=utf-8wdjky");


//        String res = PasswordHelper.encryptPassword(str);
//        System.out.println(res);


//        String string = "kYUKdoVhnWQLS3vyZmnyudpKUVT5mt9UaI3k8ObDV010kiXL83o7DV8+yCDCVgLnCXtt/xtQJYbAO6wqB7TftE5sR7EtB9Q3DOcpfCjej4Y4fYs9jN67ZfyOG7Isg8fG6
// +CpWAyIupz6oFpGg06Decw3Zeg33ZwGsQEO5Xw3FveaF9KYflbPqzha4R7Xx5Sh2avVuofgfXEtC67YxXTu1BnwxezctfZQuk3wR6P0BFleRaRWU+YLz8U2K40abYNTV4h+qlrkj44xQHHH8A9TEH+wBkAVAziYIT/gJSWL/mnYjBbk/9zNaJ6bL8/pdy
// +LFG5DcVCr5qlVDaBq5g3TXw==";

        String psw = RSA.encryptByPublicKey("123456", RSAKey.adminPublicKey);

//        string = "ZhKbutmWrFl60pO4jxn9zp8XoOUKaet07nvbjAE5X7QGgXO/gkGt8/BXo3XZJqC+BB37ZLcWnsovaDLHd+ing/J5cWGloh4OrkhPBZouQHSNvtqH1gi8uD+EiMb9O9bnqk62XfEvquuf9WdS3mLADywU1OdJx6tt
// +T9cPf81wAls6LKB0unK80T4wY+SEycB3XKjWkFBVwAnehiejrxgXhug/1sQTwSZffBF/4P8cxZfUgfKux80acq0d5HcA/oLK/K7BaVBlkipiRiURPM1BxCqZzjEblL03D1QO5cO7h4KwvX8XEF/fp4xPax+OgoUBWUzAheOpuO2yfhQ2o5HhQ==";

//        System.out.println(RSA.decryptByPrivateKey(string, RSAKey.adminPrivateKey));

        System.out.println(psw);


    }


}
