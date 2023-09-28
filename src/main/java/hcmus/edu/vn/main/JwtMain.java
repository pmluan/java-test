package hcmus.edu.vn.main;

import com.auth0.jwt.exceptions.JWTDecodeException;
import hcmus.edu.vn.utils.JWTUtil;
import hcmus.edu.vn.utils.Jackson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class JwtMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtMain.class);

    public static void main(String[] args) {

        String secretKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuQLmQySVMfHUDpHZB6diQnkyb+QiRldWmFw0nY+wV2vUY715Kftl/0joDIHZvPy9mZn+0DEs04B+JoD0o5KK/duQFeEZfKxe71bTiLZti0ecvuW9qWI7MDtZM6QpLTE3hTOu8E2BUyFd3IdiwmmtUoZFbpIgRhea0K67yqCU8AFXVVLDD108Zbbo40UWOnVyGw7stbKEp1ppEH3jFt/MQsoQy2TgNSJaB1rshQuDg1y9veDpJTVg6U/H0LzQw4ASus8U4gLpzRjjBRaEaLi0Pc+mUJV62MMfjHO9qMRZezkJoLyFAVF1ePjrVNARVdwPRFPRcaoLtQfqA9Hi3wygWwIDAQAB";
        secretKey = TextCodec.BASE64.encode(secretKey);

        String key = "jwt_authorization_key";
        String json = "{\"username\":\"timon\",\"role\":\"root\"}";
        String token = JWTUtil.createJWT(json, 3600, key);
        System.out.println(token);

    }


    private static Map<String, Object> decodeJwtClaims(String secretKey, String token) {
        try {
            Jws<Claims> jwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            String jwtLog = Jackson.toJsonString(jwt);
            LOGGER.info("decodeJwt - jwt: {}", jwtLog);

            Map<String, Object> claims = jwt.getBody();
            if (MapUtils.isEmpty(claims)) {
                LOGGER.info("decodeJwt - Invalid jwt");
                return Collections.emptyMap();
            }

            Object exp = claims.get("exp");
            if (ObjectUtils.isEmpty(exp)) {
                LOGGER.info("decodeJwt - Jwt not set life time");
                return Collections.emptyMap();
            }

            return claims;

        } catch (Exception exception) {
            LOGGER.error("decodeJWT Exception: ", exception);
            return Collections.emptyMap();
        }

    }

    private static void decodeJwt(String publicKey, String token) {
        try {
            Jws<Claims> jwt = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
            System.out.println(jwt.getBody().toString());
        } catch (JWTDecodeException exception) {
            // Invalid token
        }
    }

}
