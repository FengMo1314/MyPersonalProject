package com.example.springbootweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Content-Type: text/html;charset:utf-8;
 * <p>
 * 常见的媒体格式类型如下：
 * <p>
 * text/html ： HTML格式
 * text/plain ：纯文本格式
 * text/xml ：  XML格式
 * image/gif ：gif图片格式
 * image/jpeg ：jpg图片格式
 * image/png：png图片格式
 * <p>
 * 以application开头的媒体格式类型：
 * <p>
 * application/xhtml+xml ：XHTML格式
 * application/xml     ： XML数据格式
 * application/atom+xml  ：Atom XML聚合格式
 * application/json    ： JSON数据格式
 * application/pdf       ：pdf格式
 * application/msword  ： Word文档格式
 * application/octet-stream ： 二进制流数据（如常见的文件下载）
 * application/x-www-form-urlencoded ： <form encType=””>中默认的encType，form表单数据被编码为key/value格式发送到服务器（表单默认的提交数据的格式）
 * <p>
 * 另外一种常见的媒体格式是上传文件之时使用的：
 * <p>
 * multipart/form-data ： 需要在表单中进行文件上传时，就需要使用该格式
 */

@RestController
@RequestMapping(value = "/", produces = "text/html;charset=utf-8")
@Slf4j//日志打印
public class HelloController {
    @GetMapping
    @ResponseBody
    public String hello() {
        return "<h1>" +
                "你好游客" +
                "</h1>" +
                "<a href=\"/lh.com/listings\">查看部分json房源数据</a>" +
                "<img src=\"/src/main/resources/static/微信图片_20221222090124.png\"";
    }

    @GetMapping("/{name}/")
    @ResponseBody
    public String hello(@PathVariable("name") String name) {
        log.info("\nhello");
        return "<h1>Hello" + name + "😊</h1>" +
                "<h1>" +
                "你好" +
                name +
                "</h1>" +
                "<a href=\"/lh.com/listings/\">查看部分(第一页,2条)json房源数据</a>";
    }
}