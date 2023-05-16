package com.example.springbootweb.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    //密钥
    public static final String SECRET = "sgfdsopljkhsl;o437632";
    //过期时间:秒
    public static final int EXPIRE = 60*60*60;//60秒
    public static Date expireDate = null;
//

    /**
     * 生成Token
     */
    public static String createToken(String username, String type, Integer uId) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, EXPIRE);
         expireDate = nowTime.getTime();
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        /*JWTCreator.Builder 类
          withIssuer（） – 标识创建令牌并对其进行签名的一方
          withSubject（） – 标识 JWT 的主题
          withIssuedAt（） – 标识创建 JWT 的时间;我们可以用它来确定 JWT 的年龄
          withExpiresAt（） – 标识 JWT 的到期时间
          withJWTId（） – JWT 的唯一标识符
          withNotBefore（） – 标识不应接受 JWT 进行处理的时间
          withClaim（） – 用于设置任何自定义声明
         */
        return JWT.create()
                .withHeader(map)//头
                .withClaim("uId", uId)
                .withClaim("username", username)
                .withClaim("type", type)
                .withSubject("lh.com-JWT-验证")//
                .withIssuedAt(new Date())//签名时间
                .withExpiresAt(expireDate)//过期时间
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证Token
     *
     */
    public static Map<String, Claim> verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
        } catch (Exception e) {
            throw new RuntimeException("凭证已过期，请重新登录");
        }
        return jwt.getClaims();
    }

    /**
     * 解析Token
     *
     */
    public static Map<String, Claim> parseToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaims();
    }


}
