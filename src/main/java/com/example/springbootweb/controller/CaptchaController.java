package com.example.springbootweb.controller;

import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.util.LHAuth;
import com.google.code.kaptcha.Producer;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码获取与验证
 */
@Controller
@RequestMapping("/kaptcha")
@RestController//返回json字符串
@Slf4j//日志打印
public class CaptchaController {
    /**
     * Local Cache  1分钟过期
     */
    Cache<String,String> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(1, TimeUnit.MINUTES).build();
    private Date date;
    private Map<String, Object> rm;
    //    @GetMapping(produces = "image/ipeg")
    @GetMapping("/{uuId}")
    public LHResult getKaptchaImage(@PathVariable("uuId") String uuId) {
        System.out.println("------------------CaptchaController——@GetMapping(\"/{uuId}\")-----------------------------");
        rm = new HashMap<>();
        System.out.println("缓存uuId:"+uuId+"\t上次求取的验证码为"+localCache.getIfPresent(uuId));
        if (localCache.getIfPresent(uuId)== null) {
//            生成uuId
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//注意月和小时的格式为两个大写字母
            date = new Date();//获得当前时间
            System.out.println("日期：\t" + date);
            uuId = "LH" + df.format(date);
            localCache.put(uuId,uuId);
        }

        //生成验证码
        SpecCaptcha captcha = new SpecCaptcha(150, 40);
        captcha.setLen(5);
        captcha.setCharType(Captcha.TYPE_DEFAULT);
        System.out.println("用户uuId:\t" + uuId + "生成验证码\t" + captcha.text());
        rm.put("uuId", uuId);
//        图片用Base64明文传输
        rm.put("KaptchaImage", captcha.toBase64());
        //保存到缓存
        localCache.put(uuId, captcha.text());
        return LHResult.succ("生成验证码成功", rm);
    }

    @GetMapping("/{uuId}/{userCode}")
    public LHResult chickCODE(@PathVariable("userCode") String userCode, @PathVariable("uuId") String uuId) {
//获取缓存中的验证码
        String captcha = localCache.getIfPresent(uuId);
        //删除验证码
//        if(captcha != null){
//            localCache.invalidate("catch");
//        }
        if (userCode==null){
            return LHResult.others("空验证码");
        }
        if (captcha==null) {
            return LHResult.others("验证码失效");
        }
        if (!userCode.equalsIgnoreCase(captcha)) {
            System.out.println("库存验证码:\t"+captcha+"传入验证码:\t"+userCode);
            System.out.println("验证码错误");
            return LHResult.others("验证码错误");
        }
        System.out.println("库存验证码:\t"+captcha+"传入验证码:\t"+userCode);
//        //删除验证码
//        localCache.invalidate(uuId);
        return LHResult.succ("验证码正确");
    }

}
