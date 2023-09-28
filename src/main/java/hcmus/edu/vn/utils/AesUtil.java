package hcmus.edu.vn.utils;

import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AesUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(AesUtil.class);
	
	public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

	public static String encryptMethod(String data, String key) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec( key.getBytes(StandardCharsets.UTF_8), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
			return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			LOGGER.error("encryptMethod - Error: ", e);
			return null;
		}
	}

	public static String decryptMethod(String encryptedData, String key) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec( key.getBytes(StandardCharsets.UTF_8), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
			byte[] bytePlainText = cipher.doFinal(Base64.getDecoder().decode(encryptedData.getBytes()));
			return new String(bytePlainText);
		} catch (Exception e) {
			LOGGER.error("decryptMethod - Error: ", e);
			return null;
		}
	}
	
	
	public static String encryptMethod1(String data, String key) {
		try {
			
			SecretKeySpec skeySpec = new SecretKeySpec( key.getBytes(StandardCharsets.UTF_8), "AES");
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] ciphertext = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(ciphertext);
		} catch (Exception e) {
			LOGGER.error("encryptMethod - Error: ", e);
			return null;
		}
	}
	
	public static String decryptMethod1(String encryptedData, String key) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec( key.getBytes(StandardCharsets.UTF_8), "AES");
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			
			AlgorithmParameters params = cipher.getParameters();
			byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		    
		    cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));
			return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData.getBytes())), StandardCharsets.UTF_8);
		} catch (Exception e) {
			LOGGER.error("decryptMethod - Error: ", e);
			return null;
		}
	}

	private AesUtil() {
	}
}
