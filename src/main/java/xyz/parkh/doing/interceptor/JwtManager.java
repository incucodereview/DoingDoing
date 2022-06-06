package xyz.parkh.doing.interceptor;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Slf4j
public class JwtManager {
    public static String generateToken(String userId) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 21)); // 테스트용 21 일

        String tokenStr = Jwts.builder()
                .setHeaderParam("kid", "myKeyId")
                .setIssuer("PARKH").setId(id)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(exp)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, "parkh".getBytes())
                .compact();
        log.info(userId + " : " + tokenStr);
        return tokenStr;
    }

    public static HashMap<String, Object> verifyToken(String token) throws Exception {
        HashMap<String, Object> tokenMap = new HashMap<>();
        String tokenCode = "000";

        if (token.equals(null) || token.equals("")) {
            tokenCode = "999";
        } else {
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("parkh".getBytes())
                        .parseClaimsJws(token).getBody();
                tokenMap.put("userId", claims.get("userId").toString());
            } catch (ExpiredJwtException e) {
                tokenCode = "901";

            } catch (ClaimJwtException e) {
                tokenCode = "902";

            } catch (MalformedJwtException e) {
                tokenCode = "903";
            }
        }
        tokenMap.put("tokenCode", tokenCode);
        return tokenMap;
    }
}