package hcmus.edu.vn.main;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.apache.commons.lang3.StringUtils;

public class SimpleDecrypt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String pwdEncrypt = "";
        String pwdDecrypt = "";
        String user = "userTest";
        String pwdText = "password";
        String [] err = new String[1];
        pwdEncrypt = simpleEncrypt(StringUtils.join(user,"$",pwdText), "d793d747-638e-4387-be02-efb551e20956", err);
        pwdDecrypt = simpleDecrypt(pwdEncrypt, "d793d747-638e-4387-be02-efb551e20956", new String[256]);
        //System.out.println(pwdEncrypt);
        //System.out.println(pwdDecrypt);
        
        
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv10MwXSIvmLVNIiAMLov6nf4zQWBX6yOWKE4tmcENP7hanYrY0IyKrf9XIdFXdLxPB+Vbl36+5Fg0d6bBT1KHSOhktJgCRs0nk/ry2bntv9OruLM+vPFSwenMq/wLdHYQmj92KVVRachg7NELXIPJ+kybGpzN3Jt3MH5s8tvTVwQdnkHUFF+q64janHZ5k0/UBGjPh3BYjy0eKQwxn4AF6VpSEyUlRAI3m0QcTmVGijzZPQ7mORXGD3v4x+Sm4lJox9Xgtajw52bJESsk0kmV2jPwnQf0y6ZsUY3A/dRYN+skVTPJKjicDDeLZppWCyBEw3MdT5rhAmnoWxFaAzbPwIDAQAB";
        
        String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCw2xlQlWyfUe22TiRSd3LYYoFQNXbZBK6/6+9rMpTx+SJwCbk+aLmI1vozxXhCsyuymuOqJXHpJJAOH76JwPjxJ//PtplEBcH+4iwia0kCBisNQ47kikGQiCq/9q9wwg7VPqKgd1wmZlfy+haQXg/gyvk5voY9ySpR8bnWXDqeBNFwSNSd9arAzgyR6sxiiK5fLjFk1TbFQ0XWM1VLicOmKmlhEAdrZK8TbV3zZgcTIsHd38IzHCKtpswSx3Nz+9znV4xtGdk0axwkDp7EZYZwMsyTYuCRo1Es81Kfex60ESfISG3zA0bhLZw+BAyedVEhZFb7QGTByVGgLBWLTUX3AgMBAAECggEBAJe2nHTAejoB8kXdqtMDnQV7GytPcfChT7dcEjqHXbZl3eD2tm6PL/nvURtwXsX5JZdFv9+J17oEn38+RBmv7N4TTiNFpRzxQ4X/R5UPK8YMvInYVpA++heed+3NFJtKHadY3/F/8xEN4oyRdSHLTxurgzjB9C60jxVUo/L7k11RnOklYM3YweO0io3btwvPn77QgUK+l7xih9MhBC5RkMb/dPJ2tFsvEypi2be99kY8B9rRlsFvVkrIWxWqpoqGos88BiUT2pVLw4sQOUDEVznkJd8j96BxdzvA/LdWrDS81wZUJwOzCA4It7ltOBeP+xRP8xc7hjtsTFMVeWC4u8ECgYEA8N15TmES7mrw40q8Gr5kFDh2nGgIEVyFjz4i7dTtcd+XlP7TcCO66Uff+YlFdwaYwrvje0o9GIy8q20bakLjFLNaDu9MlK25nXRAHxc2ZB135A9mV1t3JCV1iBYu6auhKwaediWIUHhU7g8wJiDHF514ymIe9+JLVWs48kYAxnkCgYEAu/f62NMJPdqrpRZEXVkko/52cqOWzU/Zpx9kNeyAv1Ak3vnDCPbQKEpnnZhf2QOV3NvkC4SJwxoXJPkRFkz1iBSqyqjn1/uUxG8hFI31MeLX6AJBwWHYWxZaZh7EDvMolsLQr2bs4ABUfgPR3yWmtVoff6t6gQp3TT6TrN5hE+8CgYBU5Bnu7/O2J+bClQJ7AoNPRR84vMmrJMrF04aIADW3nrBUCQQIT+9+bKCZ2DGtY5vPdl0UhPPHhUKxwICbEPvrvdKN1PVOqXPz9Jbs3keDPYeVz3+oZko1aSZlUjwyixUAjxKmXVAP3HlCdgPoTEEfJ5b87VwwzYbzkhi9+sInMQKBgQC7fVyOzeGX+vfe0Vkbq4EquvS24InY/N7jHiK8zsyVB4Hfj02FMXrr1VuJDKX+Nt9Sz1qmSmuHWgzf6jerANSgCc1aHCQ1gVIgpaBfekGqotQtLaMdG1XNdXxyBBoXt18zEdtKOeXogSvGDlVWuZ2RovVsSUUGVcQuHk76zU4TrQKBgQDncbZMpfEhn/3eG/SBAG0t8sDstBOMctbAQ6JJgVD5o66q2fTf6Z8+yTl9PqezFa3fdujJZxeuwGX5P3VgaLwQXl7GSrnz6VdCGLNQDqrIz8EtgoCKAkPiiTDKzjwyzUp6VgyFCtuMgdPdQCatPZNDqiaXpHXEAzCHTlrgabHNkg==";
        String data = "DAtqQKQYKwoFo6XGdjFz+WvKOqJ/4rfXLf+B5hrhYA8wcbCeHfPt+Cyck4KF5rB4TToOCdBAV9q3xQSILhMyUt66JKDNC4KLHqRyWzAz0rqV7GQSF+wRRPO785hScBN9m7C8m9TL4h312eGmRJUUGKvGlvlTqIvvnPx3Bvy7hwrF9Obc5+l23WONlmKqMpruQC+YRBy1A6gDXTKQ14JwNZj0aeJnRpvMPvxX3fxc9ZXkCBAPLdtRcR77FNXR5NAwTRHIcJr04k/aCsbrCOeax//m/RFcDyLDtuO1Vlk1HAK4wCoE+caX8F2r476ItTRVYhFpoJA8lxNCGJafrCRMug==";
        
        String rsa = encryptMethod(pwdEncrypt, publicKey);
        
        System.out.println(rsa);
        

    }
    
    private static String encryptMethod(String data, String internalPublicKey) {
        // Create public key
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(internalPublicKey));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = factory.generatePublic(spec);

            // Encrypt data using public key
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] encryptOut = c.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptOut);
        }catch (Exception e){
           e.printStackTrace();
            return StringUtils.EMPTY;
        }
    }

    public static String simpleEncrypt(String plainText, String key, String[] err) {
        err[0] = "000000";
        String out = "";
        try {
            int i = (key.hashCode() < 0) ? key.hashCode() * -1 : key.hashCode();
            byte[] b = new byte[plainText.length()];

            for (int j = 0; j < b.length; ++j) {
                b[j] = (byte) plainText.charAt(j);
                b[j] = (byte) (b[j] ^ i);
            }
            out = data2hex(b);
        } catch (Exception ex) {
            err[0] = ex.getMessage();
        } 
            return out;
      
    }

    public static String simpleDecrypt(String cipherText, String key,String[] err) {
        err[0] = "000000";
        String out = "";
        try {
            int i = (key.hashCode() < 0) ? key.hashCode() * -1 : key.hashCode();
            byte[] b = hex2data(cipherText);

            for (int j = 0; j < b.length; ++j) {
                b[j] = (byte) (b[j] ^ i);
            }

            StringBuffer sb = new StringBuffer(b.length);
            for (int j = 0; j < b.length; ++j) {
                sb.append((char) b[j]);
            }
            out = sb.toString();
        } catch (Exception ex) {
            err[0] = ex.getMessage();
        } 
            return out;
       
    }

    public static byte[] hex2data(String str) {
        if (str == null) {
            return new byte[0];
        }
        int len = str.length();
        char[] hex = str.toCharArray();
        byte[] buf = new byte[len / 2];

        for (int pos = 0; pos < len / 2; ++pos) {
            buf[pos] = (byte) (toDataNibble(hex[(2 * pos)]) << 4 & 0xF0 | toDataNibble(hex[(2 * pos + 1)]) & 0xF);
        }
        return buf;
    }

    public static byte toDataNibble(char c) {
        if (('0' <= c) && (c <= '9')) {
            return (byte) ((byte) c - 48);
        }
        if (('a' <= c) && (c <= 'f')) {
            return (byte) ((byte) c - 97 + 10);
        }
        if (('A' <= c) && (c <= 'F')) {
            return (byte) ((byte) c - 65 + 10);
        }
        return -1;
    }

    public static String removeWhiteSpace(String str) {
        String temp = "";
        for (int i = 0; i < str.length(); ++i) {
            if ((str.charAt(i) != ' ') && (str.charAt(i) != '\n') && (str.charAt(i) != '\t')) {
                temp = temp + str.charAt(i);
            }
        }
        return temp;
    }

    public static String data2hex(byte[] data) {
        if (data == null) {
            return null;
        }
        int len = data.length;
        StringBuffer buf = new StringBuffer(len * 2);
        for (int pos = 0; pos < len; ++pos) {
            buf.append(toHexChar(data[pos] >>> 4 & 0xF)).append(toHexChar(data[pos] & 0xF));
        }
        return buf.toString();
    }

    private static char toHexChar(int i) {
        if ((0 <= i) && (i <= 9)) {
            return (char) (48 + i);
        }
        return (char) (97 + i - 10);
    }

    public static String encryptUTF8(String plainText, String key)
            throws Exception {
        int i = (key.hashCode() < 0) ? key.hashCode() * -1 : key.hashCode();
        byte[] b = plainText.getBytes("UTF8");

        for (int j = 0; j < b.length; ++j) {
            b[j] = (byte) (b[j] ^ i);
        }
        return data2hex(b);
    }

    public static String decryptUTF8(String cipherText, String key)
            throws Exception {
        int i = (key.hashCode() < 0) ? key.hashCode() * -1 : key.hashCode();
        byte[] b = hex2data(cipherText);

        for (int j = 0; j < b.length; ++j) {
            b[j] = (byte) (b[j] ^ i);
        }

        return new String(b, "UTF8");
    }
}
