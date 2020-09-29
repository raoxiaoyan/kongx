package com.kongx.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.kongx.common.core.entity.UserInfo;

public abstract class JWTTokenUtils {
    private final static String SECRET = "kongx";

    public static String getToken(UserInfo user) {
        String token = "";
        token = JWT.create().withAudience(Jackson2Helper.toJsonString(user))
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    public static UserInfo decode(String token) {
        String user = JWT.decode(token).getAudience().get(0);
        return Jackson2Helper.parsonObject(user, new TypeReference<UserInfo>() {
        });
    }

    public static boolean verify(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }
}
