package com.mysite.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoderUtil {
    public static String encoder(String password, Integer clientId){
        try{
            String passToEncode= password + clientId;
            MessageDigest digest= MessageDigest.getInstance("SHA-256");
            byte[] hash= digest
                    .digest(passToEncode.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);

        }catch(NoSuchAlgorithmException exception){
            return null;
        }
    }

    private static String bytesToHex(byte[] hash){
        StringBuilder hexString= new StringBuilder(2 * hash.length);
        for(byte b: hash){
            hexString.append(String.format("%02x" , b ^ 0xFF));
        }
        return hexString.toString();
    }
}
