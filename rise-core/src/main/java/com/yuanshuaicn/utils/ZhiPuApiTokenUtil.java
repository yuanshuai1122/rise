package com.yuanshuaicn.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class ZhiPuApiTokenUtil {

    public static String generateToken(String apiKey, int expSeconds) {
        try {
            String[] parts = apiKey.split("\\.");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid apiKey format.");
            }
            String id = parts[0];
            String secret = parts[1];

            long currentTimeMillis = System.currentTimeMillis();
            Date issuedAt = new Date(currentTimeMillis);
            Date expiresAt = new Date(currentTimeMillis + expSeconds * 1000L);

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("api_key", id)
                    .withClaim("exp", expiresAt.getTime())
                    .withClaim("timestamp", issuedAt.getTime())
                    .withHeader(java.util.Map.of("alg", "HS256", "sign_type", "SIGN"))
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Error generating token: ", e);
        }
    }


}
