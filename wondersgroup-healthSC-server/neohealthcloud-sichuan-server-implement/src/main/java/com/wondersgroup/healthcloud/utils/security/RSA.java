package com.wondersgroup.healthcloud.utils.security;

import okio.ByteString;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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
 * <p>
 * Created by zhangzhixiu on 6/1/16.
 */
public class RSA {

    private static PublicKey generatePublicKeyFrom(String publicKey) {
        byte[] keyBytes = ByteString.decodeBase64(publicKey).toByteArray();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }

    private static PrivateKey generatePrivateKeyFrom(String privateKey) {
        byte[] keyBytes = ByteString.decodeBase64(privateKey).toByteArray();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }

    public static String encryptByPublicKey(String data, String publicKey) {
        try {
            PublicKey pk = generatePublicKeyFrom(publicKey);
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, pk);
            byte[] bytes = c.doFinal(data.getBytes());
            return ByteString.of(bytes).base64();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptByPrivateKey(String data, String privateKey) {
        try {
            PrivateKey pk = generatePrivateKeyFrom(privateKey);
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, pk);
            byte[] bytes = c.doFinal(ByteString.decodeBase64(data).toByteArray());
            return new String(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encryptByPrivateKey(String data, String privateKey) {
        try {
            PrivateKey pk = generatePrivateKeyFrom(privateKey);
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, pk);
            byte[] bytes = c.doFinal(data.getBytes());
            return ByteString.of(bytes).base64();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptByPublicKey(String data, String publicKey) {
        try {
            PublicKey pk = generatePublicKeyFrom(publicKey);
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, pk);
            byte[] bytes = c.doFinal(ByteString.decodeBase64(data).toByteArray());
            return new String(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
