package com.ygy.util;


import com.ygy.service.CategorySevice;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.crypto.Cipher;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

public class RsaUtil {
    private static final String CHARSET_NAME = "utf-8";
    private static final String TYPE = "RSA";
    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA/JiVZORasJaKDbfXjTl6WU4Xe2rZgiUvq8IBxmIdTXHOnb5TScdVGKkPPM7S125fWGRggArOMFbNGIdaYBRImTKO3KH/jwe8veQpXdKmNqwkJVU0ymTPz1lufL9eeNreFJHv2Y1wpWL3pc8l0sSzXwvRhGo5WKAGz4ho8uqcogtKeMGad8bZ6ZapRaoF2K7YXmIYqTBWc/FZY5d+ia029jnpnBOa0Y/NiPIPdYUbaO8grquYXQdSVlH3/lOwFj6jeKvEb9zrnKOKocTDf+9r/Z+GiNhiZo8Vof+nNFw5s6KS80iZJbb8JeMqOhrROYML53vWgfRTKJwzxxvVDmeR2QIDAQAB";
    public static final String PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQD8mJVk5FqwlooNt9eNOXpZThd7atmCJS+rwgHGYh1Ncc6dvlNJx1UYqQ88ztLXbl9YZGCACs4wVs0Yh1pgFEiZMo7cof+PB7y95Cld0qY2rCQlVTTKZM/PWW58v1542t4Uke/ZjXClYvelzyXSxLNfC9GEajlYoAbPiGjy6pyiC0p4wZp3xtnplqlFqgXYrtheYhipMFZz8Vljl36JrTb2OemcE5rRj82I8g91hRto7yCuq5hdB1JWUff+U7AWPqN4q8Rv3Ouco4qhxMN/72v9n4aI2GJmjxWh/6c0XDmzopLzSJkltvwl4yo6GtE5gwvne9aB9FMonDPHG9UOZ5HZAgMBAAECggEBAIeRxSy/OsVoJnQyZWgQn0/rD8NXl6G4H5vJpnY5Fo0INeKH0d/ZMhzoL5ttTc/+FwIz7W8LXZqoG6+8hY7nlAdVEUPtVmIHvw8Ts/f9JORXA5dd4dV2weoBj8E4XJX0shjMMrTKqti5Az1krmIu3C55A1xUPq8IRF+CUQF/Vc3JvJlWtkM7EDvVJM8NjlAkOfLflPw8UAdgtufr2LSNXzPVsAVh4tzPv64HnPIzCEeJzZ/t3hadqThkefT3ma5aJHqAKIin28ACQw5LEO6gNE0qsEXdcVSdCYxTEhH7w5vksArU5LE9AEQF9vi/05zPKZ0SrlR1SCbExMPs/pF6mNkCgYEA/z4CLhWTRYPPamfckhStFuS+uCWmfCdB8EmTs37AyfXxOe1YEMUUVkAaaIqdTFqsx7sq5z6BTaxhF9fmKZ8Eue2vgK4Mw4U0+JGODkUlSyhRZlvGpBXrNNxSZPOyuU+iW3JKP8QJ8ooVFSgdQtgNR7InAR4NeGpddG9sc6anFEMCgYEA/ViQWfwnH5tKnFSUGvmZUHeiAn8iEKeZDNb6XubWkGwQ7+HXEDGU5F7QFxqIl9q6xuzeZ5PoFaaJdzunbNUk5tDz1EAoexAYcsNxgY6u6nwkJlCS74O60UKJEbbq/ixdZ/cJnn5TJFr/xRubNjlDw0ZnVr4yXhzPYZwsfMvvDbMCgYBp5BrZbaAEpfOGDILIpAVPSyUIlq4kMdE8U/oP7JUk9IDYbGwtwOV4Bu4t0twBUlj+H9tjxl20kMBTOQHG4K8AR+iYtRycfQYF3bUWnH8lWBu1IHwflOxYqNMSCLxr/uq3j4nYPId39OtcNKE3GxUfWnyuDP6/pzkQVi2jKU+FqwKBgQDUe1Emh5twoNgk4bMOJMV3fb79hQ9XFJoC8NMwgT74Bgr2hl3QESWlm/e/SNvDMQpyCPxHtv9yGSk2PWwglWvLwvidzemlvUwkBNPQK5F8zP2svOtfwwYOuFUp7FaPMzpsFf+VVLH6ze+yq4K0Kbcxsny9fevUjylH3qpZSNmIjQKBgQDOG+ejh9PRKfgEeTGDF4VCj9nyTv2xUkamYay0tZSjFCh0oUay2j0RnEaetp/l4MkreDWNkZqZSx8cCPjpgpvNgl1hxTCMNibx5mEidI7cM5Y1Lblu08LNcJeAXOIVyIfdvc3MlAiq1HX081hAWmOyjylOMaWRLA0i0Obld6SSSA==";


    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(TYPE).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(CHARSET_NAME)));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes(CHARSET_NAME));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(TYPE).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance(TYPE);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    public static void main(String[] args) throws Exception {
//        long start = System.currentTimeMillis();
//        String text = "1";
//        String encodeText = encrypt(text, PUBLIC_KEY);
//        System.out.println("加密后：" + encodeText);
//        String code = "k7JcnILTLwdQXPPhxC6E/du2VswvElLIMQGgeNtgnDGLQdrCwnITriGlY5fXSLe2vuzSjldsIVN4uxng23zHAtAaIst/Xya8hj7vFblCyTiVS5NT91kyrbnuB6YVfS5mnwyHNtHPbllDj7u03yFqvencAkNog9P6FOJ5pxQ0Fus9c3A3UfeuCe+QNnyRFK9C2yOE6aOsZDz26un1V7g3vAl5WYypOw0GRd6TJLykxn7ooWortgPS2jCya+KMumWyF3aYUmI5yPKOpn4q4RX21ShQ5ab0XFPRyvxgSWkYHn1TrFHNgwOAe+NUPEa+bAAcMZugqyOJWuyvwg8kkujcYQ==";
//        System.out.println("解密后：" + decrypt(code, PRIVATE_KEY));
//
//
//        long end = System.currentTimeMillis();
//        System.out.println(end - start);
        String beginTime = "2022-05-01%2000%3A00%3A00";
        String beginTimeDecode = URLDecoder.decode(beginTime, StandardCharsets.UTF_8);
        System.out.println(beginTimeDecode);
    }
}
