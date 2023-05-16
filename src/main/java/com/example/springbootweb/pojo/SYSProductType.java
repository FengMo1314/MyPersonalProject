package com.example.springbootweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品类型表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SYSProductType {
    private Integer id;
    private String name;
    private Integer status;
}
