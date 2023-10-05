package com.example.jwt;

import io.jsonwebtoken.*;

import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    public static String encodeJwt(String key) {
        // 1.定义header部分的内容
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("type", "jwt");
        headerMap.put("alg", SignatureAlgorithm.HS256.getValue());
        // 2.定义payload部分的内容
        Map<String, Object> payload = new HashMap<>();
        payload.put("sub", "jwt token generate");//标识jwt主题
        payload.put("iat", String.valueOf(System.currentTimeMillis()));//标识jwt发出的时间
        payload.put("exp", String.valueOf(System.currentTimeMillis() + 24 * 60 * 60 * 1000));//到期时间
        payload.put("name", "xg");
        payload.put("role", "admin");
        // 3.生成token
        String jwtToken = Jwts.builder()
                .setHeaderParams(headerMap)
                .setClaims(payload)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();// 拼接header和payload
        return jwtToken;
    }

    public static void decodeJwt(String jwtToken, String key) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtToken)
                .getBody();
        Object sub = claims.get("sub");
        Object name = claims.get("name");
        Object role = claims.get("role");
        Object exp = claims.get("exp");


        System.out.println(sub);
        System.out.println(name);
        System.out.println(role);
        System.out.println(exp);
    }

}
