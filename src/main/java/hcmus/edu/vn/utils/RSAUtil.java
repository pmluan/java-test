package hcmus.edu.vn.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    private RSAUtil() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtil.class);

    private static final String ALGORITHM_CIPHER = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    private static final String ALGORITHM_KEY_FACTORY = "RSA";

    public static String encryptRsa256(String plainText, String publicKey) {
        String result = StringUtils.EMPTY;
        if (StringUtils.isBlank(plainText)) {
            return result;
        }
        try {
            PublicKey publKey = getPublicKey(publicKey);

            Cipher cipher = Cipher.getInstance(ALGORITHM_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, publKey);

            byte[] bytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            result = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            LOGGER.error("RSA Encrypt data failed: ", e);
            return StringUtils.EMPTY;
        }
        return result;
    }

    public static String decryptRsa256(String encryptedText, String privateKey) {
        String result = StringUtils.EMPTY;
        if (StringUtils.isBlank(encryptedText)) {
            return result;
        }
        try {
            PrivateKey privKey = getPrivateKey(privateKey);

            Cipher cipher = Cipher.getInstance(ALGORITHM_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            result = new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("RSA Decrypt data failed: ", e);
            return StringUtils.EMPTY;
        }
        return result;
    }
    
    public static PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
    	String priv = privateKey .replace("-----BEGIN PRIVATE KEY-----", "").replaceAll(System.lineSeparator(), "").replace("-----END PRIVATE KEY-----", "");
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM_KEY_FACTORY);
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(priv));
        return kf.generatePrivate(keySpecPKCS8);
    }

    public static PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
    	String pub = publicKey.replace("-----BEGIN PUBLIC KEY-----", "").replaceAll(System.lineSeparator(), "").replace("-----END PUBLIC KEY-----", "");
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM_KEY_FACTORY);
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(pub));
        return kf.generatePublic(keySpecX509);
    }

}
