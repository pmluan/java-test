package hcmus.edu.vn.main;

import hcmus.edu.vn.utils.JWTUtil;
import hcmus.edu.vn.utils.Jackson;
import hcmus.edu.vn.utils.RSAUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main2.class);

	public static void main(String[] args) throws Exception {

		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkUQ2U3x8sPuwJ+qzhL59uqccL8+li2XACipcXz7UVpaone/E6iv+LgevL1U7gptWX4DkuQMn1S4M38SAqPEJLbETgp+bVuCPV+L1d3zJg/2Ob2NNUo4a3BO72y1l4CVTzJCssuupmDMeVpSLR559KsnxvLzbSXKEiRMxlLSJ6E2Vg/oiZnwDliZXhAvFxKqejNVS7Bent1ZE/DJV/OdTX/m4ebZlPrIMxuizW7/whpqty6sBWaPFI0v82RJE9cKHbTj0N5WAmW16CJg0uquaOnfyNJJfVkm/HrPiYcaxxaSjs0TOl2JSVwiZpBH1nc2SIVvDbxm6AhvFmyJ5bRZFQwIDAQAB";

		String privateKey= "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRRDZTfHyw+7An6rOEvn26pxwvz6WLZcAKKlxfPtRWlqid78TqK/4uB68vVTuCm1ZfgOS5AyfVLgzfxICo8QktsROCn5tW4I9X4vV3fMmD/Y5vY01SjhrcE7vbLWXgJVPMkKyy66mYMx5WlItHnn0qyfG8vNtJcoSJEzGUtInoTZWD+iJmfAOWJleEC8XEqp6M1VLsF6e3VkT8MlX851Nf+bh5tmU+sgzG6LNbv/CGmq3LqwFZo8UjS/zZEkT1wodtOPQ3lYCZbXoImDS6q5o6d/I0kl9WSb8es+JhxrHFpKOzRM6XYlJXCJmkEfWdzZIhW8NvGboCG8WbInltFkVDAgMBAAECggEAQJ+q11sjEYz2YducfypLPpUHChIDIE9krmEH9Os7hUXCv3giDtcBXbLXcWQmdETsmNH7bn8D4md5HLcLUZzhK22CM3MTpQODYWK7NqvR7iEHk3AGvvou4sb4pFm+34dbjz6xRWtnaCkdjwDIMVVFc5qhKwiNOVBzu5NJVFE4pJnrhdVl29KdzDxzqM827S1noDMB9RhI8V49KhfBaPm7vtbFivT2+PpwAS5Rt2gKFbbALMOxH9pUhFYSYcQlBbw8eS2k9hCrhBArRXOVe/6XIy7c+75OKqHBVLr2izWDkGmKtRXgvphAr296L9Ks/JbeJgBhB3laDpVwsI6Lt+il4QKBgQDHJDmzBIy0y1YAdgpM7XlF/qUgcz3WHSjS+4vmffYOklvV/4XlZp4dDUkdwBXFnhAhDhTWv8AQ9fyzgH/NQbRzNi0vqL1yu9eeA36PhifoFubR9HXCymLfH9YY2FKd5ieJSZCL/rIvfLTqkpPcY+MI5sCG/v3QK2j/11rBZHVH0QKBgQC6vh47lWKgoRYBtpYu+6opInPuBGZRVCZzhMg6VNREyhUWZlK6HOxjdR0mD5/LGJTk9I3+GtIT6lfgX+q7izJDIF1QAZEDeLvJ+GDW5ghD2NxvqofgdOWwnqL2lsXu0by8S9zCDWIJ3adArx/FBrVqVvjYrGTHiICSNGzT8YPU0wKBgQDBY7CU5Oov+Pc/42IFaujGzJBXfxKiCBdJLPejkteNNxzwKQFC0fDijbBfr9Z03xcSwrYCBpzi27/YIgX6ssAm0on3KoswDLNLxGD3yHSgsvzdD+X8kMaLoEMuRYWVyZjDfildiq5H53daBZaKBIbKciyt7JtXuZByRBTikkhCwQKBgEK7C1ypXiweRBnqoDI71iuK1zh3tARd+LuQ+Gf3xj6qo+x0Nh2xljtYL0lZjfVqXFKQnWjvNqDNPge83smTzSs1loKvfJ1r+FcM/bXtT4jHJsZ0fseTnjyGA5/wctyLcFEtHr7tP1pdLZYsvkKOyics2xG6UO0Id0FHoJgAFubnAoGBAMOj4kt8k91xxb1GYEemFDEsKCA+wpzEpvmK2S4P5Cbeb/bd4HvHw0MMpEdlJuxYg2fwMVo+/XgVg/xpsINbtx4VYM+YoLxFsGt4zJDvjcvOjGS8yR7QEPejAgrfpE48lrgKoU10b8m1fQ8+oRgcHaAVGsPjSEacu7m/nemoH14c";
		String data = "{\"username\":\"luanpm\",\"password\":\"P@ssw0rd\",\"passwordConfirm\":\"P@ssw0rd\",\"email\":\"\",\"phoneNumber\":\"0352673283\",\"name\":\"Pham Minh Luan\",\"address\":\"\",\"birthday\":\"\",\"gender\":\"Male\"}";
		
		
		String pass = "luanpm";
		String encrypt = RSAUtil.encryptRsa256(pass, publicKey);
		System.out.println(encrypt);
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("userName", pass);
		
		System.out.println(JWTUtil.generateJWT(claims, 300, "294de3557d9d00b3d2d8a1e6aab028cf"));
		

	}

	protected static String decryptMethod(String data, String internalPrivateKey) throws Exception {
		// Create private key
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(internalPrivateKey));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey priKey = factory.generatePrivate(spec);

		// Decrypt data using private key
		Cipher c = Cipher.getInstance("RSA");
		c.init(Cipher.DECRYPT_MODE, priKey);
		byte decryptOut[] = c.doFinal(Base64.getDecoder().decode(data));
		return new String(decryptOut);
	}
	
	
	protected static String encryptMethod(String data, String internalPublicKey) throws Exception {
		// Create public key
		X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(internalPublicKey));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = factory.generatePublic(spec);

		// Encrypt data using public key
		Cipher c = Cipher.getInstance("RSA");
		c.init(Cipher.ENCRYPT_MODE, pubKey);
		byte encryptOut[] = c.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(encryptOut);
	}
	
	public static String getCode(String partner, String type, String resultCode) {
		String json = "{\"error\":[{\"partner\":\"vnpay\",\"code\":[{\"inquiry\":[{\"00\":\"Success\",\"01\":\"Error\"}]},{\"payment\":[{\"00\":\"Success\"}]}]}]}";

		try {
			ErrorModel model = Jackson.fromJsonString(json, ErrorModel.class);
			if (model == null) {
				LOGGER.info("Can not parse json data");
				return StringUtils.EMPTY;
			}

			PartnerModel partnerModel = model.getError().stream().filter(i -> i.getPartner().equals(partner)).findAny().orElse(null);
			if (partnerModel == null) {
				LOGGER.info("Partner not found");
				return StringUtils.EMPTY;
			}

			List<Map<String, List<Map<String, String>>>> code = partnerModel.getCode();
			if (CollectionUtils.isEmpty(code)) {
				LOGGER.info("List data not found");
				return StringUtils.EMPTY;
			}

			Map<String, List<Map<String, String>>> typeMap = code.stream().filter(i -> i.containsKey(type)).findAny().orElse(null);
			if (MapUtils.isEmpty(typeMap)) {
				LOGGER.info("Type not found");
				return StringUtils.EMPTY;
			}

			List<Map<String, String>> list = typeMap.get(type);
			if (CollectionUtils.isEmpty(list)) {
				LOGGER.info("List code not found");
				return StringUtils.EMPTY;
			}

			Map<String, String> codeMap = list.stream().filter(i -> i.containsKey(resultCode)).findAny().orElse(null);
			if (MapUtils.isEmpty(codeMap)) {
				LOGGER.info("Code not found");
				return StringUtils.EMPTY;
			}

			return codeMap.get(resultCode);
		} catch (Exception e) {
			LOGGER.error("Error: ", e);
			return StringUtils.EMPTY;
		}
	}

	public static class ErrorModel {
		private List<PartnerModel> error;

		public List<PartnerModel> getError() {
			return error;
		}

		public void setError(List<PartnerModel> error) {
			this.error = error;
		}

	}

	public static class PartnerModel {
		private String partner;
		private List<Map<String, List<Map<String, String>>>> code;

		public String getPartner() {
			return partner;
		}

		public void setPartner(String partner) {
			this.partner = partner;
		}

		public List<Map<String, List<Map<String, String>>>> getCode() {
			return code;
		}

		public void setCode(List<Map<String, List<Map<String, String>>>> code) {
			this.code = code;
		}

	}

}
