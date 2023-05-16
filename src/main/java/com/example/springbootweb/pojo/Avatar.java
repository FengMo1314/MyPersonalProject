package com.example.springbootweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//满参构造
@AllArgsConstructor
//无参构造
@NoArgsConstructor
public class Avatar {
    private Integer id;
    private Integer uId;
    private String uName;
    private String imgUrl;
}
