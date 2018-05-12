package com.wondersgroup.hs.healthcloud.common.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * 类描述：健康云的https签名认证
 * 创建人：Bob
 * 创建时间：2016/7/20 11:13
 */
public class JkySSLSocketFactory {
    private final static String PWD = "wonders";
    private SSLContext sslContext = SSLContext.getInstance("TLS");

    private static JkySSLSocketFactory instance;


    public static SSLSocketFactory getSocketFactory(Context context, int rawId) {
        if (instance == null) {
            InputStream ins = context.getResources().openRawResource(rawId);
            try {
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(ins, PWD.toCharArray());
                instance = new JkySSLSocketFactory(keyStore);
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
            } finally {
                try {
                    if (ins != null) {
                        ins.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return instance.sslContext.getSocketFactory();
    }

    private JkySSLSocketFactory(KeyStore keystore) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.
                getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keystore);


        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keystore, PWD.toCharArray());

        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
    }

}
