package com.example.springbootweb.exception;

import com.example.springbootweb.pojo.LHResult;
import org.springframework.util.StringUtils;

public class CustomerException extends RuntimeException {
    public CustomerException(String msg) {
        super(msg);
    }

    public CustomerException(LHResult result) {
        super(result.getMessage());
    }

    /**
     * 功能描述 异常枚举类抛出
     *
     * @return com.funtl.itoken.commons.exception.ItokenException
     * @author qyh
     * @date 2020/3/11
     */
    public static CustomerException buildException(LHResult result) {
        LHResult result1 = new LHResult(result.getCode(), result.getMessage(), result.getRest(),result.getTime(),result.getResult());
        return new CustomerException(result1);
    }

    /**
     * 功能描述 异常枚举类加自定义信息
     *
     * @return com.funtl.itoken.commons.exception.ItokenException
     * @author qyh
     * @date 2020/3/11
     */
    public static CustomerException buildException(LHResult result, String msg) {

        LHResult result1 = null;
        if (StringUtils.isEmpty(msg)) {
            result1 = new LHResult(result.getCode(), result.getMessage(), result.getRest(),result.getTime(),result.getResult());
        } else {
            result1 = new LHResult(result.getCode(), result.getMessage() + msg, result.getRest(),result.getTime(),result.getResult());
        }

        return new CustomerException(result1);
    }
}

