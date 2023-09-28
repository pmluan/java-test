package hcmus.edu.vn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.util.Base64;

public class KeyPairFactory {

	private static final String ALGORITHM_KEY_FACTORY = "RSA";
	private static final Logger LOGGER = LoggerFactory.getLogger(KeyPairFactory.class);

	private static KeyPairFactory instance;

	private KeyPair keyPair;

	private KeyPairFactory() {
		KeyPairGenerator generator;
		try {
			generator = KeyPairGenerator.getInstance(ALGORITHM_KEY_FACTORY);
			generator.initialize(2048);
			this.keyPair = generator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("KeyPairFactory - Error: ", e);
		}

	}

	public static synchronized KeyPairFactory getInstance() {
		if (instance == null) {
			instance = new KeyPairFactory();
		}
		return instance;
	}

	public String getPublicKey() {
		PublicKey publicKey = getPublicKeyObj();
		if (publicKey != null) {
			return Base64.getEncoder().encodeToString(publicKey.getEncoded());
		}
		return null;
	}

	public PublicKey getPublicKeyObj() {
		if (keyPair != null && keyPair.getPublic() != null) {
			return keyPair.getPublic();
		}
		return null;
	}

	public String getPrivateKey() {
		PrivateKey privateKey = getPrivateKeyObj();
		if (privateKey != null) {
			return Base64.getEncoder().encodeToString(privateKey.getEncoded());
		}
		return null;
	}

	public PrivateKey getPrivateKeyObj() {
		if (keyPair != null && keyPair.getPrivate() != null) {
			return keyPair.getPrivate();
		}
		return null;
	}

}
