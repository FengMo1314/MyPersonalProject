package com.example.springbootweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SYSAttache {
    private Integer attache_id;
    private Integer file_type;
    private String file_path;
    private String create_date;
    private Integer user_id;
}
