package com.example.springbootweb.util;

import com.auth0.jwt.interfaces.Claim;
import com.example.springbootweb.pojo.Tokens;
import com.example.springbootweb.service.TokensService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public class TokenUtil {
    /**
     * 对前端请求的地址进行权限判断
     */
    private static Tokens tokens;

    @SneakyThrows
    public static boolean typeCanUse(String token, String url, Object handler) {
//        获取token对象
        Map<String, Claim> getToken = JwtUtil.parseToken(token);
        String uType = getToken.get("type").asString();
//        合法token必然会解析出type字段
        if (uType.isEmpty()) {
            return false;
        }
//        获取类
        Class classzz = ((HandlerMethod) handler).getBeanType();
//        获取类上的指定注解
        LHAuth classzzAnnotation = (LHAuth) classzz.getAnnotation(LHAuth.class);
        LHAuth method = ((HandlerMethod) handler).getMethod().getAnnotation(LHAuth.class);

//        如果类和方法皆无注解则为完全开放
        if (classzzAnnotation == null && method == null) {
            return true;
        } else if (classzzAnnotation != null && method != null) {//类和方法皆有注解
            int classzzAnnotationLength = classzzAnnotation.value().length;
            int methodLength = method.value().length;
            if (classzzAnnotationLength == 0 && methodLength == 0) {//类和方法有注解没有值
                return true;
            }

//            偏重于方法的取值——只要方法有单个空值或者没有值都表示任何人都可访问
            if ((methodLength == 1 && method.value()[0].isBlank()) || methodLength == 0) {//
                return true;
            }
            if (methodLength > 1) {//方法不只有一个非空值
//                取决于方法上的值
                for (String adm : method.value()) {//遍历方法上的值
                    if (adm.equals(uType)) {//只有相等的被放行
                        return true;
                    }
                }
                return false;
            }
            return false;
        } else {//类或者方法有注解
            LHAuth lhAuth = null;
            if (classzzAnnotation == null) {//类上无注解
                // 获取方法注解
                lhAuth = method;
            } else{//方法无注解
                //获取类上的注解
                lhAuth = classzzAnnotation;
            }
//
            if (lhAuth == null) {
                System.out.println("cant find @LHAuth in this uri(在请求的方法上找不到注解包括其类上也没有):" + url);
                return true;
            }
            // 从参数中取出用户身份并验证
            String[] admins = lhAuth.value();
            if (admins.length == 0) {
                return true;
            }
            if (admins.length==1&&admins[0].isBlank()){
                return true;
            }
            for (String adm : admins) {
                if (adm.equals(uType)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 对请求网络地址 进行加工
     */
    public static String getRequestAddressOrUrl(HttpServletRequest request) {
        String api = request.getRequestURI().replaceAll(request.getContextPath(), "");
        while (api.contains("//")) {
            api = api.replace("//", "/");
        }
        String methodName = request.getMethod();
        String url = methodName + ":" + api;
        System.out.println("截取的url：\t" + url);
        return url;
    }

    /**
     * 判断token是否被加入黑名单
     */
    public static boolean tokenActive(String token, TokensService tokensService) {
        Tokens setToken = new Tokens();
        setToken.setToken(token);
        tokens = tokensService.getTokensByToken(setToken);
        if (tokens == null) {
            return false;
        }
        return tokens.getActive().equals("true");
    }

    public static void killTokens(String token, TokensService tokensService) {
        Tokens setToken = new Tokens();
        setToken.setToken(token);
        tokens = tokensService.getTokensByToken(setToken);
        if (tokens == null) {
            return;
        }
//        将这个token加入黑名单
        tokens.setActive("false");
//        将数据库中的token的活跃值改为false 并更新
        Integer rows = tokensService.updataTokensById(tokens);
    }

    public static boolean writeTokenToSQL(String token, Integer uId, TokensService tokensService) {
        Tokens setTokens = new Tokens();
        setTokens.setToken(token);
        setTokens.setActive("true");
        setTokens.setUId(uId);
//        库已存在
        if (tokensService.getTokensByToken(setTokens) != null) {
            return true;
        }
        return tokensService.addTokens(setTokens) > 0;
    }
}
