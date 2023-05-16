package com.example.springbootweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SYSOrder {
    private Integer id;
    private String no;
    private Double price;
    private Integer user_id;
}
