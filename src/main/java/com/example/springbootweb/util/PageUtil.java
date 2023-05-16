package com.example.springbootweb.util;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageUtil<T> {
    /**
     * 分页设计。输入为当前页码，每页展示的条数，查询出来的所有数据List
     * @param currentPage
     * @param pageSize
     * @param resultList
     * @return
     */
        public PageInfo getPage(Integer currentPage, Integer pageSize, List<T> resultList){
            int size = resultList.size();
            if (size % pageSize!= 0){
                size = size / pageSize+ 1;
                if(currentPage== size){
                    resultList = resultList.subList((currentPage - 1) * pageSize,resultList.size());
                }else {
                    resultList = resultList.subList((currentPage - 1) * pageSize,currentPage * pageSize);
                }

            }else {
                if(size!=0) {
                    resultList = resultList.subList((currentPage - 1) * pageSize, currentPage * pageSize);
                }
            }

//            Page page =new Page(currentPage,pageSize);
//            page.setTotal(resultList.size());
//            int startIndex = (currentPage-1)*pageSize;
//            int endIndex = Math.min(resultList.size(),startIndex+pageSize);
//            page.addAll(resultList.subList(startIndex,endIndex));
            return new PageInfo(resultList);
        }
    }

