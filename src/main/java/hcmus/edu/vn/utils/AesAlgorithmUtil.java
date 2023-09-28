package hcmus.edu.vn.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AesAlgorithmUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(AesAlgorithmUtil.class);

	public static String encryptMethod(String data, String key) {
		try {
			Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKey secretKey = convertStringToSecretKeyto(key);
			aesCipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
			return Base64.getEncoder().encodeToString(aesCipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			LOGGER.error("encryptMethod - Error: ", e);
			return null;
		}
	}

	public static String decryptMethod(String encryptedData, String symetricKey) {
		try {
			SecretKey secretKey = convertStringToSecretKeyto(symetricKey);
			Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
			byte[] bytePlainText = aesCipher.doFinal(Base64.getDecoder().decode(encryptedData.getBytes()));
			return new String(bytePlainText);
		} catch (Exception e) {
			LOGGER.error("decryptMethod - Error: ", e);
			return null;
		}
	}

	public static String createAesKey() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(256); // for example
			SecretKey secretKey = keyGen.generateKey();
			return convertSecretKeyToString(secretKey);
		} catch (Exception e) {
			LOGGER.error("createAesKey - Error: ", e);
			return StringUtils.EMPTY;
		}
	}

	private static SecretKey convertStringToSecretKeyto(String encodedKey) {
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	}

	private static String convertSecretKeyToString(SecretKey secretKey) {
		byte[] decodedKey = Base64.getEncoder().encode(secretKey.getEncoded());
		return new String(decodedKey);
	}

	private AesAlgorithmUtil() {

	}

}
