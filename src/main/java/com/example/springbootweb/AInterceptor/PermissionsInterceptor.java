package com.example.springbootweb.AInterceptor;


import com.example.springbootweb.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * 访问地址权限判断优先判断
 */
@Component
public class PermissionsInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map<String, Object> map = new HashMap<>();

        /*
          获取请求头中的token
         */
        String headToken = request.getHeader("Authorization");
        System.out.println("首次拦截：token：" + headToken);
        if (headToken!=null) {
            boolean tokenCanUser = true;
            try {
                tokenCanUser = TokenUtil.typeCanUse(headToken, TokenUtil.getRequestAddressOrUrl(request), handler);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", 20004);
                map.put("message", "Invalid Authorization header——凭证无效");
                map.put("rest", new HashMap<>() {
                });
                JwtInterceptor.ErrorResponse(response, map);
            }
            if (!tokenCanUser) {
                System.out.println("首次Token不可使用");
                //头部token不为空
                map.put("code", 20003);
                map.put("message", "Invalid Authorization header——无权访问");
                map.put("rest", new HashMap<>());
                JwtInterceptor.ErrorResponse(response, map);
                return false;
            }
            System.out.println("首次token验证不为空 ");
        }
        return true;
    }
}
