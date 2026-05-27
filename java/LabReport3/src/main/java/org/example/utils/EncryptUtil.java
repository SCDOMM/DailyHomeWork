package org.example.utils;

public class EncryptUtil {
    public static String xorEncrypt(String content,String key){
        if (content == null || key == null || key.isEmpty()) {
            return null;
        }
        char[] contentChars = content.toCharArray();
        char[] keyChars = key.toCharArray();
        int keyLength = keyChars.length;
        for (int i = 0; i < contentChars.length; i++) {
            contentChars[i] = (char) (contentChars[i] ^ keyChars[i % keyLength]);
        }
        return new String(contentChars);
    }
}
