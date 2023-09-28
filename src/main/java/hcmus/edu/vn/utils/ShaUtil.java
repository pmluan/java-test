package hcmus.edu.vn.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ShaUtil {
	
	public static String computeSignature(String str){
        try {
            MessageDigest md=MessageDigest.getInstance("SHA1");
            md.update(str.getBytes());
            byte[] digest=md.digest();
            return Base64.getEncoder().encodeToString(digest);
        }
        catch (  NoSuchAlgorithmException ex) {
            throw new RuntimeException("It should not happen, " + ex.getMessage(),ex);
        }
    }

}
