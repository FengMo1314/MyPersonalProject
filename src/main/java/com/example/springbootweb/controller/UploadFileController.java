package com.example.springbootweb.controller;
import com.example.springbootweb.pojo.LHFiles;
import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.service.FilesService;
import com.example.springbootweb.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class UploadFileController {
    @Autowired
    FilesService filesService;
    LHFiles LHFiles;
    List<LHFiles> LHFilesList;
    Map<String, Object> rh;

    @PostMapping
    @ResponseBody
    public LHResult upload(@RequestParam("file") MultipartFile uploadFile, @RequestHeader("Authorization") String token, HttpServletRequest request)
            throws Exception {
        System.out.println("传入文件：\t" + uploadFile);
        Integer uId = JwtUtil.verifyToken(token).get("uId").asInt();
        return this.filesService.upload(uploadFile, uId, request);
    }

    /**
     * @return FileUploadResult
     * @desc 根据文件名删除oss上的文件
     * http://localhost:8080/file/delete?fileName=images/2019/04/28/1556429167175766.jpg
     * @author lastwhisper
     * @Param objectName
     */
    @DeleteMapping("/{fileId}")
    @ResponseBody
    public LHResult delete(@PathVariable("fileId") Integer fileId)
            throws Exception {
        return this.filesService.delete(fileId);
    }

    /**
     * @return List<OSSObjectSummary>
     * @author lastwhisper
     * @desc 查询oss上的所有文件
     * http://localhost:8080/file/list
     * @Param
     */
    @GetMapping("/list")
    @ResponseBody
    public LHResult list()
            throws Exception {
        return this.filesService.list();
    }

    /**
     * @return
     * @author lastwhisper
     * @desc 根据文件名下载oss上的文件
     * @Param objectName
     */
    @RequestMapping("/download")
    @ResponseBody
    public void download(@RequestParam("fileName") String objectName, HttpServletResponse response) throws IOException {
        //通知浏览器以附件形式下载
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(objectName.getBytes(), StandardCharsets.ISO_8859_1));
        this.filesService.exportOssFile(response.getOutputStream(), objectName);
    }

    @GetMapping
    @ResponseBody
    public LHResult getFilesByUId(@RequestHeader("Authorization") String token) {
        LHFiles = new LHFiles();
        rh = new HashMap<>();
        Integer uId = null;
        try {
            uId = JwtUtil.parseToken(token).get("uId").asInt();
        } catch (Exception e) {
            e.printStackTrace();
            return LHResult.fail("系统错误");
        }
        LHFiles.setUId(uId);
        LHFilesList = filesService.getSomeFilesByUId(LHFiles);
        rh.put("somefiles", LHFilesList);
        return LHResult.succ("Get LHFiles By UId Is True", rh);
    }

}
