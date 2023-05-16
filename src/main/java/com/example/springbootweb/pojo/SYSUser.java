package com.example.springbootweb.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SYSUser {
    private Integer user_id;
    private String user_name;
    private String login_name;
    private String password;
    private String phone;
    private String address;
    private Integer is_valid;
    private String regist_date;
}
