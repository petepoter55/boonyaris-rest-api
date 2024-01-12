package com.rest.api.boonyarisRestApi.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class UtilityTools {
    private static final Logger logger = LogManager.getLogger(UtilityTools.class);

    public String hashSha256(String msg) {
        String result = "";
        try {
            // สร้าง MessageDigest ด้วยอัลกอริทึมที่ต้องการ (MD5, SHA-1, SHA-256, เป็นต้น)
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // แปลงข้อมูลเป็น byte array
            byte[] inputBytes = msg.getBytes();
            // นำ byte array มาทำ hash
            byte[] hashBytes = md.digest(inputBytes);
            // แปลง byte array ที่ได้เป็นรหัสฐาน 16
            StringBuilder hashStringBuilder = new StringBuilder();
            for (byte hashByte : hashBytes) {
                // แปลงให้เป็นรหัสฐาน 16 และเพิ่มลงใน StringBuilder
                hashStringBuilder.append(String.format("%02x", hashByte));
            }
            result = hashStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    public boolean checkPassword(String hashNewPass, String hashOldPass) {
        return hashSha256(hashNewPass).equals(hashOldPass);
    }
}
