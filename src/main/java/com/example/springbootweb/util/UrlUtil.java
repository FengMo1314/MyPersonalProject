package com.example.springbootweb.util;

import jakarta.servlet.http.HttpServletRequest;

public class UrlUtil {

    public static String doUrlAboutStart(HttpServletRequest request,String path){
        String urlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/static/img/"+path;
       return urlPath;
    }
}
