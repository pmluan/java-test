package hcmus.edu.vn.utils;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {
    private JWTUtil() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtil.class);
    
    private static final String AUTH_0 = "auth0";
    
    private static final String SECRET_KEY_HS = "7eFcXBLR9y+$!Y?F";

    public static String createJWT(Map<String, String> data, int lifeTime, PrivateKey privKey) {
        String token;
        try {
            Algorithm algorithm = Algorithm.RSA256(null, (RSAPrivateKey) privKey);

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);

            //Builds the JWT and serializes it to a compact, URL-safe string
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(AUTH_0)
                    .withIssuedAt(now);

            if (lifeTime > 0) {
            	calendar.setTimeInMillis(calendar.getTimeInMillis() + TimeUnit.SECONDS.toMillis(lifeTime));
            	builder.withExpiresAt(calendar.getTime());
            }
            
            if (data != null) {
            	data.forEach(builder::withClaim);
            }
            token = builder.sign(algorithm);
        } catch (Exception e) {
            LOGGER.error("GetJwtRsa256 Exception: ", e);
            return StringUtils.EMPTY;
        }
        return token;
    }
    
	public static String generateJWT(Map<String, Object> claims, int lifeTime, PrivateKey privKey) {
		String token;
		try {
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.SECOND, lifeTime);
			
			if (claims == null) {
				claims = new HashMap<>();
			}
			claims.put(Claims.ISSUER, AUTH_0);
			
			token = Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(lifeTime > 0 ? calendar.getTime() : null)
					.signWith(SignatureAlgorithm.RS256, privKey).compact();
		} catch (Exception e) {
			LOGGER.error("Failed While Generating JWT", e);
			return StringUtils.EMPTY;
		}
		return token;
	}
	
    public static Map<String, Claim> decodeJWT(String jwtToken, PublicKey publicKey) {
        Map<String, Claim> claims;

        try {
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(AUTH_0)
                    .build();

            DecodedJWT jwt = verifier.verify(jwtToken);
            claims = jwt.getClaims();
        } catch (Exception e) {
            LOGGER.error("decodeJWT Exception: ", e);
            return Collections.emptyMap();
        }

        return claims;
    }
    
    public static String createJWT(String data, int lifeTime, String secretKey) {
    	try {
    		
    		long time = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(lifeTime);
            Date expiryDate = new Date(time);
            
    		return Jwts.builder()
	                    .setSubject(data)
	                    .setIssuedAt(new Date())
	                    .setExpiration(expiryDate)
	                    .signWith(SignatureAlgorithm.HS256, secretKey)
	                    .compact();
    		
    	} catch (Exception e) {
			LOGGER.error("createJWT - error: ", e);
			return StringUtils.EMPTY;
		}
    }
    
    public static String generateJWT(Map<String, Object> claims, int lifeTime, String secretKey) {
		String token;
		try {
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.SECOND, lifeTime);
			
			if (claims == null) {
				claims = new HashMap<>();
			}
			claims.put(Claims.ISSUER, AUTH_0);
			
			token = Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(lifeTime > 0 ? calendar.getTime() : null)
					.signWith(SignatureAlgorithm.HS256, secretKey).compact();
		} catch (Exception e) {
			LOGGER.error("Failed While Generating JWT", e);
			return StringUtils.EMPTY;
		}
		return token;
	}

    
    public static Map<String, Object> decodeJwtCliams(String secretKey, String token) {
		try {
			Jws<Claims> jwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			String jwtLog = Jackson.toJsonString(jwt);
			LOGGER.info("decodeJwt - jwt: {}", jwtLog);
			
			Map<String, Object> claims = jwt.getBody();
			if(MapUtils.isEmpty(claims)) {
				LOGGER.info("decodeJwt - Invalid jwt");
				return Collections.emptyMap();
			}
			
			Object exp = claims.get("exp");
			if(ObjectUtils.isEmpty(exp)) {
				LOGGER.info("decodeJwt - Jwt not set life time");
				return Collections.emptyMap();
			}
			
			return claims;
			
		} catch (Exception exception) {
			LOGGER.error("decodeJWT Exception: ", exception);
			return Collections.emptyMap();
		}
		
	}
    
    public static String generateJwtHS(Map<String, Object> claims, int timeExpirationSecond) {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		byte[] apiKeySecretBytes = Base64.encodeBase64(SECRET_KEY_HS.getBytes(StandardCharsets.UTF_8));
		SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, timeExpirationSecond);

		if (claims == null) {
			claims = new HashMap<>();
		}
		
		JwtBuilder builder = Jwts.builder()
				.setIssuedAt(now)
				.signWith(signatureAlgorithm, signingKey)
				.setExpiration(calendar.getTime())
				.setIssuer(AUTH_0)
				.addClaims(claims);

		return builder.compact();
	}
    
    public static Claims decodeJwtHS(String jwt) {
        return Jwts.parser()
                .setSigningKey( Base64.encodeBase64(SECRET_KEY_HS.getBytes(StandardCharsets.UTF_8)))
                .parseClaimsJws(jwt).getBody();
    }
    
}
