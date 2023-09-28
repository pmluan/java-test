package hcmus.edu.vn.utils;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAUtils {

	
    private RSAUtils() {
        // Prevents developer from initializing object
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);
    private static final String ALGORITHM_CIPHER = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final String ALGORITHM_KEY_FACTORY = "RSA";

    /**
     * Encrypts a plain text using {@code publicKey}
     * 
     * @param plainText the {@code plainText} value to be encrypted
     * @param publicKey the {@code publicKey} value
     * @return an encrypted based64 value
     * @throws CustomiseException
     */
    public static String encryptRsa256(String plainText, String publicKey) {
        if (StringUtils.isBlank(publicKey)) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(plainText)) {
        	return StringUtils.EMPTY;
        }
        try {
            PublicKey publKey = getPublicKey(publicKey);
            Cipher cipher = Cipher.getInstance(ALGORITHM_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, publKey);
            byte[] bytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        }
        catch (Exception e) {
        	LOGGER.error("encryptRsa256 - error: ", e);
        	return StringUtils.EMPTY;
        }
    }

    /**
     * Decrypts the RSA encrypted text using {@code privateKey}
     * 
     * @param encryptedText the text to be decrypted
     * @param privateKey the {@code privateKey} value
     * @return the plain text value
     * @throws CustomiseException
     */
    public static String decryptRsa256(String encryptedText, String privateKey) {
        if (StringUtils.isBlank(privateKey)) {
        	return StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(encryptedText)) {
        	return StringUtils.EMPTY;
        }
        try {
            PrivateKey privKey = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance(ALGORITHM_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)), StandardCharsets.UTF_8);
        }
        catch (Exception e) {
        	LOGGER.error("decryptRsa256 - error: ", e);
        	return StringUtils.EMPTY;
        }
    }

    /**
     * Gets the {@link PrivateKey} based on the private key
     * 
     * @param privateKey the private key value
     * @return a {@link PrivateKey}
     * @throws CustomiseException
     */
    public static PrivateKey getPrivateKey(String privateKey) {
        if (StringUtils.isBlank(privateKey)) {
        	return null;
        }
        try {
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM_KEY_FACTORY);
            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            return kf.generatePrivate(keySpecPKCS8);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
        	LOGGER.error("getPrivateKey - error: ", ex);
        	return null;
        }
    }

    /**
     * Gets the {@link PublicKey} based on the private key
     * 
     * @param publicKey the public key value
     * @return a {@link PublicKey}
     * @throws CustomiseException
     */
    public static PublicKey getPublicKey(String publicKey) {
        if (StringUtils.isBlank(publicKey)) {
        	return null;
        }
        try {
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM_KEY_FACTORY);
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            return kf.generatePublic(keySpecX509);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
        	LOGGER.error("getPublicKey - error: ", ex);
        	return null;
        }
    }
    
    public String createPublicPrivateKey() throws Exception {
    	SecureRandom sr = new SecureRandom();
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048, sr);

		KeyPair kp = kpg.genKeyPair();
		PublicKey publicKey = kp.getPublic();
		PrivateKey privateKey = kp.getPrivate();

		return Base64.getEncoder().encodeToString(publicKey.getEncoded()) + "#"
				+ Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

}
