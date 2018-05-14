package com.cts.migration.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class MigrationUtil {
	private static final SecretKeySpec secKey = new SecretKeySpec(("yuybrtRhHoPN9xHz").getBytes(), "AES");

	public static Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		default:
			if(DateUtil.isCellDateFormatted(cell)){
				return cell.getDateCellValue();
			}
		}
		return null;
	}
	
	public static String getMD5Hash(String original) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(original.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}
	
	public static String encryptAES(String plainText) throws Exception{
  		// AES defaults to AES/ECB/PKCS5Padding in Java 7
         Cipher aesCipher = Cipher.getInstance("AES");
         aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
         byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
         return DatatypeConverter.printBase64Binary(byteCipherText);
    }

    public static String decryptAES(String cipherText) throws Exception {
  		// AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] byteCipherText = DatatypeConverter.parseBase64Binary(cipherText);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        return new String(bytePlainText);
    }
    
    public static boolean isEmpty(String inputStr) {
  		
    	if(null == inputStr || "".equalsIgnoreCase(inputStr.trim())){
    		return true;
    	}
    	
        return false;
    }
    
//    public static void main(String s[]) throws Exception{
//    	
//    	String rawPassword = "qwer@1234";
//    	String encPass	= getMD5Hash(rawPassword);
//    	System.out.println("RAW : "+rawPassword);
//    	System.out.println("ENC : "+encPass);
//    	
//    }
}
