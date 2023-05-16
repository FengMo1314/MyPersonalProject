package com.example.springbootweb.exception;

import com.alibaba.fastjson2.JSON;
import com.example.springbootweb.pojo.LHResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@ResponseBody
@Order(-1000)
public class CustomerExceptionResolver implements HandlerExceptionResolver {
    private Map<String,Object> rh;
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        LHResult result = new LHResult();
        StringBuilder sb = new StringBuilder();

        //处理异常
        if(ex instanceof CustomerException ) {
            resolverBussinessException(ex, sb, result);
        } else if (ex instanceof BindException) {
            resolverBindException(ex, sb, result);
        } else {
            resolverOtherException(ex, sb, result);
        }

        result.setCode(0);
        result.setResult(sb);
        result.setTime(new Date());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            log.error("与客户端通讯异常：" + e.getMessage(), e);
            e.printStackTrace();
        }

        log.debug("异常：" + ex.getMessage(), ex);
        ex.printStackTrace();

        return new ModelAndView();
    }
    /*
     * 处理业务层异常
     */
    private void resolverBussinessException(Exception ex, StringBuilder sb, LHResult result) {
        CustomerException businessException = (CustomerException) ex;
        sb.append(businessException.getMessage());
        addResult(result, "业务异常");
    }

    /*
     * 处理参数绑定异常
     */
    private void resolverBindException(Exception ex, StringBuilder sb, LHResult result) {
        BindException be = (BindException) ex;
        List<FieldError> errorList = be.getBindingResult().getFieldErrors();
        for (FieldError error : errorList) {
            sb.append(error.getObjectName());
            sb.append("对象的");
            sb.append(error.getField());
            sb.append("字段");
            sb.append(error.getDefaultMessage());
        }
        addResult(result, "参数传递异常");
    }

    /*
     * 处理其他异常
     */
    private void resolverOtherException(Exception ex, StringBuilder sb, LHResult result) {
        sb.append(ex.getMessage());
        addResult(result, "其他异常");
    }

    /*
     * 封装code和msg
     */
    private void addResult(LHResult result, String msg) {
        result.setMessage(msg);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)//处理异常信息——
    public LHResult handler(RuntimeException e) {
        log.error("运行时异常：----------------{}", e.getMessage());
        return LHResult.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public LHResult handler(AccessDeniedException e) {
        log.info("security权限不足：----------------{}", e.getMessage());
        return LHResult.fail("权限不足");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public LHResult handler(MethodArgumentNotValidException e) {
        log.info("实体校验异常：----------------{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return LHResult.fail(objectError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public LHResult handler(IllegalArgumentException e) {
        log.error("Assert异常：----------------{}", e.getMessage());
        return LHResult.fail(e.getMessage());
    }
}

