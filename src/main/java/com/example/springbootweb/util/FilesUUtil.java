package com.example.springbootweb.util;
import com.example.springbootweb.Constant.Constant;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.MutablePeriod;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

public class FilesUUtil {
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};
    // uploads文件夹位置
    private static final String rootPath = Constant.FILES_ROOT_PATH;
    private static Map<String,Object> rm;
    public static Map<String,Object> upload(MultipartFile uploadFile,HttpServletRequest request) throws RuntimeException {
      rm=   new HashMap<>();
        Map<String,String> r=new HashMap<>();
        String urlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/static/img/";
        // 校验图片格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
           rm.put("code",1);
           rm.put("message","文件类型不不符合");
           rm.put("rest",new HashMap<>());
            return rm;
        }
        //文件新路径
        String oldFileName = uploadFile.getOriginalFilename();//原始文件名称
        System.out.println("fName:\t" + oldFileName);
        String newFilePath = getFilePath(oldFileName);//加工后的
        System.out.println("fPath:\t" + newFilePath);
        // 上传
        File newFile = new File(rootPath, newFilePath);
        // 判断目标文件所在目录是否存在
        if (!newFile.exists() && !newFile.isDirectory()) {
//        if (!newFile.getParentFile().exists()) {
            // 如果目标文件所在的目录不存在，则创建父目录
            newFile.mkdirs();
        }
        System.out.println(newFile);
        // 将内存中的数据写入磁盘
        try {
            uploadFile.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            //上传失败
            rm.put("code",-1);
            rm.put("message","系统错误");
            rm.put("rest",new HashMap<>());
            return rm;
        }
        urlPath=urlPath+newFilePath;
        r.put("urlPath",urlPath);
        r.put("oldFileName",oldFileName);
        r.put("newFilePath",newFilePath);
        rm.put("code",0);
        rm.put("message","OK");
        rm.put("rest",r);
        return rm;
    }

    /**
     * @author lastwhisper
     * @desc 生成路径以及文件名 例如：//images/2019/04/28/15564277465972939.jpg
     * @email gaojun56@163.com
     */
    private static String getFilePath(String sourceFileName) {
        DateTime dateTime = new DateTime();
        return dateTime.toString("yyyy") + "/" + dateTime.toString("MM") + "/" + dateTime.toString("dd") + "/" + System.currentTimeMillis() + RandomUtils.nextInt(100, 9999) + "." + StringUtils.substringAfterLast(sourceFileName, ".");
    }

    /**
     * @author lastwhisper
     * @desc 查看文件列表
     * 文档链接 https://help.aliyun.com/document_detail/84841.html?spm=a2c4g.11186623.2.13.3ad5b5ddqxWWRu#concept-84841-zh
     * @email gaojun56@163.com
     */
    public Map<String,Object> list() {
        rm=new HashMap<>();
        // 设置最大个数。
        final int maxKeys = 200;
        // 列举文件。
        File allFiles = new File(rootPath);
        List<String> sums = Arrays.stream(Objects.requireNonNull(allFiles.list())).toList();
        rm.put("code",0);
        rm.put("mesg","OK");
        rm.put("rest",new HashMap<>().put("allFiles", sums));
        return rm;
    }

    /**
     * @author lastwhisper
     * @desc 删除文件
     * 文档链接 https://help.aliyun.com/document_detail/84842.html?spm=a2c4g.11186623.6.770.4f9474b4UYlCtr
     * @email gaojun56@163.com
     */
    public boolean  delete(String filesPath) {
        File delF = new File(rootPath + "/" + filesPath);
        return delF.delete();
    }

    /**
     * @author lastwhisper
     * @desc 下载文件
     * 文档链接 https://help.aliyun.com/document_detail/84823.html?spm=a2c4g.11186623.2.7.37836e84ZIuZaC#concept-84823-zh
     * @email gaojun56@163.com
     */
    public void exportOssFile(OutputStream os, String objectName) throws IOException {
        File dowFil = new File(rootPath + "/" + objectName);
        // 读取文件内容。
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(dowFil));
        BufferedOutputStream out = new BufferedOutputStream(os);
        byte[] buffer = new byte[1024];
        int lenght = 0;
        while ((lenght = in.read(buffer)) != -1) {
            out.write(buffer, 0, lenght);
        }
        if (out != null) {
            out.flush();
            out.close();
        }
        if (in != null) {
            in.close();
        }
    }
}
