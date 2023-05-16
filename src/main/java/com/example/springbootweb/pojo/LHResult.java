package com.example.springbootweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LHResult implements Serializable {
    private int code;
    private String message;
    private Map<String, Object> rest;
    private Date time;
    private StringBuilder result;
    public static LHResult succ(Map<String, Object> rest) {
        return succ(0, "操作成功", rest);
    }

    public static LHResult fail(String msg) {
        return fail(-1, msg, new HashMap<>());
    }

    public static LHResult fail(String msg, Map<String, Object> rest) {
        return fail(-1, msg, rest);
    }

    public static LHResult others(String msg) {
        return others(1, msg, new HashMap<>());
    }

    public static LHResult others(String msg, Map<String, Object> rest) {
        return others(1, msg, rest);
    }

    public static LHResult succ(String msg) {
        return succ(0, msg, new HashMap<>());
    }

    public static LHResult succ(String msg, Map<String, Object> rest) {
        return succ(0, msg, rest);
    }

    public static LHResult succ(int code, String msg, Map<String, Object> rest) {
        LHResult lhResult = new LHResult();
        lhResult.setCode(code);
        lhResult.setMessage(msg);
        lhResult.setRest(rest);
//        System.out.println("请求成功返回数据\n————————————\n"+lhResult+"\n——————————————");
        return lhResult;
    }

    public static LHResult fail(int code, String msg, Map<String, Object> rest) {
        LHResult LHResult = new LHResult();
        LHResult.setCode(code);
        LHResult.setMessage(msg);
        LHResult.setRest(rest);
        return LHResult;
    }

    public static LHResult others(int code, String msg, Map<String, Object> rest) {
        LHResult LHResult = new LHResult();
        LHResult.setCode(code);
        LHResult.setMessage(msg);
        LHResult.setRest(rest);
        return LHResult;
    }

}
