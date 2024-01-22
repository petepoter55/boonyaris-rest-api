package com.rest.api.boonyarisRestApi.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

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

    public String generateDateTimeToThai(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        return day + " " + convert2FullMonth(String.valueOf(month)) + " " + (year + 543);
    }

    public static String convert2FullMonth(String month) {
        String monthName = "";
        switch (month) {
            case "1":
                monthName = "มกราคม";
                break;
            case "2":
                monthName = "กุมภาพันธ์";
                break;
            case "3":
                monthName = "มีนาคม";
                break;
            case "4":
                monthName = "เมษายน";
                break;
            case "5":
                monthName = "พฤษภาคม";
                break;
            case "6":
                monthName = "มิถุนายน";
                break;
            case "7":
                monthName = "กรกฎาคม";
                break;
            case "8":
                monthName = "สิงหาคม";
                break;
            case "9":
                monthName = "กันยายน";
                break;
            case "10":
                monthName = "ตุลาคม";
                break;
            case "11":
                monthName = "พฤศจิกายน";
                break;
            case "12":
                monthName = "ธันวาคม";
                break;
            default:
                monthName = "";
                break;
        }
        return monthName;
    }
}
