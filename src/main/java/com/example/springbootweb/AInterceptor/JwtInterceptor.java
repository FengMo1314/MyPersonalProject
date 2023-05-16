package com.example.springbootweb.AInterceptor;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.example.springbootweb.service.TokensService;
import com.example.springbootweb.util.JwtUtil;
import com.example.springbootweb.util.TokenUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 编写JWT拦截器实现类
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    public TokensService tokensService;

    /**
     * 统一处理不符合要求的信息
     *
     * @param response 响应——
     * @param result   给客户端的响应结果值
     */
    public static void ErrorResponse(HttpServletResponse response, Map<String, Object> result) {
        OutputStream out = null;
//将返回信息转换为JSON
        JSONObject object = new JSONObject(result);
//        object.toJSONBBytes();
//        object.put("result", result);
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
//            response.setContentType("html/json");
            out = response.getOutputStream();
            out.write(object.toString().getBytes());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理器执行之前
     * 在preHandle中，可以进行编码、安全控制等在操作之前处理；
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param o        chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    @ExceptionHandler()
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Map<String, Object> map = new HashMap<>();

        System.out.println("JWT-preHandle拦截");

        /*
          获取请求头中的token
         */
        String headToken = request.getHeader("Authorization");
        System.out.println("token设置：头部token:" + headToken);
        //StringUtils.isEmpty(headToken)——第三方作者的判断字符串值是否为空
        if (StringUtils.isEmpty(headToken)||headToken==null) {
            //头部token为空——给出拦截信息
            map.put("code", 20001);
            map.put("message", "Missing or invalid Authorization header\n请在登录之后或者携带有效密钥访问");
            map.put("rest", new HashMap<>() {
            });
            ErrorResponse(response, map);
            return false;
        }
        if (!TokenUtil.tokenActive(headToken, tokensService)) {
            map.put("code", 20004);
            map.put("message", "Invalid Authorization header——已经被加入黑名单或者无效的用户token");
            map.put("rest", new HashMap<>() {
            });
            ErrorResponse(response, map);
            return false;
        }
//
        if (TokenUtil.typeCanUse(headToken, TokenUtil.getRequestAddressOrUrl(request), o)) {
            try {
//             验证token
                Map<String, Claim> maps = JwtUtil.verifyToken(headToken);
//                tokensService.getTokensByToken(setToken);
            } catch (Exception e) {
//                登录过期
                e.printStackTrace();
                TokenUtil.killTokens(headToken, tokensService);
                map.put("code", 20002);
                map.put("message", "Invalid Authorization header " + e.getLocalizedMessage());
                map.put("rest", new HashMap<>() {
                });
                ErrorResponse(response, map);
                return false;//中断执行——
            }
        } else {
            map.put("code", 20003);
            map.put("message", "Invalid Authorization header——无权访问");
            map.put("rest", new HashMap<>() {
            });
            ErrorResponse(response, map);
            return false;//中断执行——
        }
        return true;
    }

    /**
     * 处理器执行之后——视图解析器之前
     * 在postHandle中，有机会修改ModelAndView在操作之后处理
     *
     * @param httpServletRequest  current HTTP request
     * @param httpServletResponse current HTTP response
     * @param o                   the handler (or {@link HandlerMethod}) that started asynchronous
     *                            execution, for type and/or instance examination
     * @param modelAndView        the {@code ModelAndView} that the handler returned
     *                            (can also be {@code null})
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 视图解析之后
     * 在afterCompletion中，可以根据ex是否为null判断是否发生了异常，进行日志记录，页面操作完之后。
     *
     * @param httpServletRequest  current HTTP request
     * @param httpServletResponse current HTTP response
     * @param o                   the handler (or {@link HandlerMethod}) that started asynchronous
     *                            execution, for type and/or instance examination
     * @param e                   any exception thrown on handler execution, if any; this does not
     *                            include exceptions that have been handled through an exception resolver
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
