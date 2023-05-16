package com.example.springbootweb.Constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

public interface Constant {
    Integer TABLE_TRUE=0;
    Integer TABLE_FALSE=1;
//    linux
    String FILES_ROOT_PATH="/tmp/static/img";
//    win11
//    String FILES_ROOT_PATH="D:\\static\\img";
}
