package com.nooheat.secure;

/**
 * Created by NooHeat on 18/06/2017.
 */

import java.io.UnsupportedEncodingException;
import java.security.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES256 {

    private static Key keySpec = null;
    private static String key = null;
    private static String iv = null;


    static {
        // 파일시스템으로 불러들일 예정
        key = "1231kjandajkdjjdjj";
        iv = key.substring(0, 16);
        System.out.println(iv);
        byte[] keyBytes = new byte[16];
        byte[] b = null;
        try {
            b = key.getBytes("UTF-8");
            int len = b.length;
            if (len > keyBytes.length) {
                len = keyBytes.length;
            }
            System.arraycopy(b, 0, keyBytes, 0, len);
            keySpec = new SecretKeySpec(keyBytes, "AES");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /*
    encrypt : 암호화,
    암호화된 문자열을 반환
     */
    public static String encrypt(String str) {
        String enStr = null;

        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
            byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
            enStr = new String(Base64.encodeBase64(encrypted));
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException
                | NoSuchPaddingException | BadPaddingException | UnsupportedEncodingException |
                IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return enStr;
    }

    public static String decrypt(String str) {
        Cipher c = null;
        byte[] byteStr = null;
        try {
            c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
            byteStr = Base64.decodeBase64(str.getBytes());
            if (byteStr == null) return null;
            return new String(c.doFinal(byteStr), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
