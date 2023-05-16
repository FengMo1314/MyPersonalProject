package com.example.springbootweb.dao;

import com.example.springbootweb.pojo.LHFiles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FilesDao {
    List<LHFiles> getAllFiles();

    List<LHFiles> getSomeFilesByUId(@Param("LHFiles") LHFiles LHFiles);

    List<LHFiles> getFilesByName(@Param("LHFiles") LHFiles LHFiles);

    LHFiles getFilesByPath(@Param("LHFiles") LHFiles LHFiles);

    LHFiles getFilesById(@Param("LHFiles") LHFiles LHFiles);

    Integer addFiles(@Param("LHFiles") LHFiles LHFiles);

    Integer deleteById(@Param("LHFiles") LHFiles LHFiles);

    Integer deleteByName(@Param("LHFiles") LHFiles LHFiles);

    Integer deleteByPath(@Param("LHFiles") LHFiles LHFiles);

    Integer updateById(@Param("LHFiles") LHFiles LHFiles);

    //   Integer updateByUId(@Param("LHFiles") LHFiles LHFiles);
    Integer updateByPath(@Param("LHFiles") LHFiles LHFiles);

}
