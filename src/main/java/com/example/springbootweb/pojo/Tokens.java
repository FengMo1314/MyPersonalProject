package com.example.springbootweb.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Tokens {
    private Integer id;
    private Integer uId;
    private String token;
    private Date creation_time;
    private Date end_time;
    private String active;
}
