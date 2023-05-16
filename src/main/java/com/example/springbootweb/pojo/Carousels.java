package com.example.springbootweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//满参构造
@AllArgsConstructor
//无参构造
@NoArgsConstructor
public class Carousels {
    private Integer id;
    private String url;
}
