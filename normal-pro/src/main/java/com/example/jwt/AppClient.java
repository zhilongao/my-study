package com.example.jwt;

public class AppClient {
    public static void main(String[] args) {
        String key = "marsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmarsmars";
        String jwtToken = JwtUtils.encodeJwt(key);
        System.out.println(jwtToken);

        JwtUtils.decodeJwt(jwtToken, key);

    }
}
