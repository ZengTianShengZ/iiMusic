package com.example.zts.appbase.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ZTS on 2016/1/30.
 */
public class MD5Code {

    public static String hashKeyFromUrl(String url){
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        }catch (NoSuchAlgorithmException e){
            cacheKey = String.valueOf(url.hashCode());
        }

        return cacheKey;
    }

    private static String bytesToHexString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<digest.length;i++){
            String hex = Integer.toHexString(0xFF & digest[i]);
            if(hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
