package hcmus.edu.vn.main;

import hcmus.edu.vn.utils.AesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class AesMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(AesMain.class);

    private static final String CIPHER = "AES";

    public static void main(String[] args) {
        try {

//			String key = AesAlgorithmUtil.encrypt(UUID.randomUUID().toString(),AesAlgorithmUtil.getRandomKey(CIPHER, 256));
//			System.out.println(key);

            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // for example
            SecretKey secretKey = keyGen.generateKey();
            String keya = convertSecretKeyToString(secretKey);
            System.out.println(keya);

            String key = "A/0+ThjcxUszhwfVUrdNEg==";
//			

//			String encrypt = AesAlgorithmUtil.encryptMethod(text, key);
//			System.out.println(encrypt);
////
//			String data = "WgEPB3Vj1lSr+lbqFJFC3g==";
//			String decrypt = AesAlgorithmUtil.decryptMethod(data, key);
//			System.out.println(decrypt);


//			String encrypt = AesUtil.encryptMethod1(text, key);
//			System.out.println(encrypt);

            String data = "AvnzZayJ7BnndfB69p7qB0m5Gib9IA5vR24/sFbfBnysQmQ2Dpq5juAuHCNLusPG5dVAwLFypi7D7BOW+nBVpexgnmJeLTiDsEJfp+LEI0VEGD0b7krnjNjItDvzLOuBfl/Qjqnt9afKa1IeJ+QDJnR4wmRrSt04V4RCr2So2mi3KYGSKVdHlCjC//qLWeXjjN/IH5SABhlCpvbVENfsS5O59KWoznIBbCVIHJfqT8IW2OwGod/8DnhSqjvuWXzdXWG4f6apeIa191FN4dhbfDabjhp17zWBeXfCbgzQLQMcBEXz1E78ixDk+HvgTJnfXanrQSakXH7c2sH6iwNDHu2sDZhFp8zh9MeXDjtXLm1FfYmdJkZpTtiXr4tqjBQ/AZy5+W5vQwWd9MEk8uK7uQCgAcA5GpMlvp5+jX2Fc7gWv0GNwiHpRsdfqRE+0udUCtIJG4muXuf6hdq3Xt9E3VvL4+CeLzQBT5qyEfwCXKlAPUktEwWK+WvzPUS/GuEhhoieWKEQzNWFGjc68O1mt3zvqi7QRkoP+nvE9DA+yiP6nRQX+Sxf05kg/4jueLw/67T638S4LYmIOjpeW+TbMsrdPh12Zl+NtMbg+zVGp3GOpwUVmLAQxy4tmg1icG9yMRedibjLddYuck+bhmn2uZwq97FyarBxCZb7RVwaD6hOH/1Bz1RlguUkLIfo95ZKOjNVYKRescqPVjoQHhO+iutnApY9X6sL6gZb/J/b5J/315HtJwmzji2IcfqpeDd2cHCc26T6xaFm7MVAiRYAlv4MVODRc/vFG6nuf/fZ7Rk1ZDXIoSOp/zIsLVgHLpC+KsKFGSjhCv126/oAcIn02t2w3BeghNayUh59wUPXQIqBvVRQjOnu0eb7J3TnW+V6Qk0jBy+3pErrN3PolT/FD0+ps3DfY/B1QidG7sYTrvupul8DC7iC1ZzvpVu5ydbomvf+Ryy2HDdVc06npAWdka4i9un3ZCCEZs6abcGKBk4=";
            String decrypt = AesUtil.decryptMethod(data, key);
            System.out.println(decrypt);


        } catch (Exception e) {
            LOGGER.error("Error: ", e);
        }
    }

    private static String convertSecretKeyToString(SecretKey secretKey) {
        byte[] decodedKey = Base64.getEncoder().encode(secretKey.getEncoded());
        return new String(decodedKey);
    }

}
