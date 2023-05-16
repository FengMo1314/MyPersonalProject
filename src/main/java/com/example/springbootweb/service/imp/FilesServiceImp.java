package com.example.springbootweb.service.imp;


import com.example.springbootweb.Constant.Constant;
import com.example.springbootweb.dao.FilesDao;
import com.example.springbootweb.pojo.LHFiles;
import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.service.FilesService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
@Transactional
public class FilesServiceImp implements FilesService {
    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};
    private final LHFiles LHFiles = new LHFiles();
    @Autowired
    private FilesDao filesDao;
    private Map<String, Object> rh;

    // uploads文件夹位置
    private final String rootPath = Constant.FILES_ROOT_PATH;
//    private final  String rootPath="D:/MyProgrammingLanguagesWorkspace/JAVAEE/SSM-WEB/target/SSM-WEB-1.0-SNAPSHOT/static/img/";
    /**
     * @author lastwhisper
     * @desc 文件上传
     * 文档链接 https://help.aliyun.com/document_detail/84781.html?spm=a2c4g.11186623.6.749.11987a7dRYVSzn
     * @email gaojun56@163.com
     */
    @Override
    public LHResult upload(MultipartFile uploadFile, Integer uId, HttpServletRequest request) throws RuntimeException {
        rh = new HashMap<>();
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
            return LHResult.fail("不支持的文件格式");
        }
        //文件新路径
        String fileName = uploadFile.getOriginalFilename();//原始文件名称
        System.out.println("fName:\t" + fileName);
        String filePath = getFilePath(fileName);//加工后的
        System.out.println("fPath:\t" + filePath);
        // 上传
        File newFile = new File(rootPath, filePath);
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
            return LHResult.fail("系统错误");
        }
        LHFiles.setUId(uId);
        LHFiles.setFile_name(fileName);
        LHFiles.setFile_path(filePath);
        filesDao.addFiles(LHFiles);
        rh.put("userImg", urlPath + filePath);
        return LHResult.succ(rh);
    }

    /**
     * @author lastwhisper
     * @desc 生成路径以及文件名 例如：//images/2019/04/28/15564277465972939.jpg
     * @email gaojun56@163.com
     */
    private String getFilePath(String sourceFileName) {
        DateTime dateTime = new DateTime();
        return dateTime.toString("yyyy") + "/" + dateTime.toString("MM") + "/" + dateTime.toString("dd") + "/" + System.currentTimeMillis() + RandomUtils.nextInt(100, 9999) + "." + StringUtils.substringAfterLast(sourceFileName, ".");
    }

    /**
     * @author lastwhisper
     * @desc 查看文件列表
     * 文档链接 https://help.aliyun.com/document_detail/84841.html?spm=a2c4g.11186623.2.13.3ad5b5ddqxWWRu#concept-84841-zh
     * @email gaojun56@163.com
     */
    @Override
    public LHResult list() {
        rh = new HashMap<>();
        // 设置最大个数。
        final int maxKeys = 200;
        // 列举文件。
        File allFiles = new File(rootPath);
        List<String> sums = Arrays.stream(Objects.requireNonNull(allFiles.list())).toList();
        rh.put("allFiles", sums);
        return LHResult.succ("全部文件获取OK", rh);
    }

    /**
     * @author lastwhisper
     * @desc 删除文件
     * 文档链接 https://help.aliyun.com/document_detail/84842.html?spm=a2c4g.11186623.6.770.4f9474b4UYlCtr
     * @email gaojun56@163.com
     */
    @Override
    public LHResult delete(Integer fileId) {

        LHFiles.setId(fileId);
        LHFiles getFByTb = filesDao.getFilesById(LHFiles);
        File delF = new File(rootPath + "/" + getFByTb.getFile_path());
        Integer delFByTb = filesDao.deleteById(getFByTb);
        if (!delF.delete() && delFByTb <= 0) {
            return LHResult.fail("删除失败");
        }
        return LHResult.succ("删除OK");
    }

    /**
     * @author lastwhisper
     * @desc 下载文件
     * 文档链接 https://help.aliyun.com/document_detail/84823.html?spm=a2c4g.11186623.2.7.37836e84ZIuZaC#concept-84823-zh
     * @email gaojun56@163.com
     */
    @Override
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

    @Override
    public List<LHFiles> getAllFiles() {
        return filesDao.getAllFiles();
    }

    @Override
    public List<LHFiles> getSomeFilesByUId(LHFiles LHFiles) {
        return filesDao.getSomeFilesByUId(LHFiles);
    }

    @Override
    public List<LHFiles> getFilesByName(LHFiles LHFiles) {
        return filesDao.getFilesByName(LHFiles);
    }

    @Override
    public LHFiles getFilesByPath(LHFiles LHFiles) {
        return filesDao.getFilesByPath(LHFiles);
    }

    @Override
    public Integer addFiles(LHFiles LHFiles) {
        return filesDao.addFiles(LHFiles);
    }

    @Override
    public Integer deleteById(LHFiles LHFiles) {
        return filesDao.deleteById(LHFiles);
    }

    @Override
    public Integer deleteByName(LHFiles LHFiles) {
        return filesDao.deleteByName(LHFiles);
    }

    @Override
    public Integer deleteByPath(LHFiles LHFiles) {
        return filesDao.deleteByPath(LHFiles);
    }

    @Override
    public Integer updateById(LHFiles LHFiles) {
        return filesDao.updateById(LHFiles);
    }

    @Override
    public Integer updateByPath(LHFiles LHFiles) {
        return filesDao.updateByPath(LHFiles);
    }
}
