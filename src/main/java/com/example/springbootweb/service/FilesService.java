package com.example.springbootweb.service;
import com.example.springbootweb.pojo.LHFiles;
import com.example.springbootweb.pojo.LHResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Transactional
public interface FilesService {
    LHResult upload(MultipartFile uploadFile, Integer uId, HttpServletRequest request) throws RuntimeException;

    LHResult list();

    LHResult delete(Integer fileId);

    void exportOssFile(OutputStream os, String objectName) throws IOException;

    List<LHFiles> getAllFiles();

    List<LHFiles> getSomeFilesByUId(LHFiles LHFiles);

    List<LHFiles> getFilesByName(LHFiles LHFiles);

    LHFiles getFilesByPath(LHFiles LHFiles);

    Integer addFiles(LHFiles LHFiles);

    Integer deleteById(LHFiles LHFiles);

    Integer deleteByName(LHFiles LHFiles);

    Integer deleteByPath(LHFiles LHFiles);

    Integer updateById(LHFiles LHFiles);

    //   Integer updateByUId(@Param("LHFiles") LHFiles LHFiles);
    Integer updateByPath(LHFiles LHFiles);
}
