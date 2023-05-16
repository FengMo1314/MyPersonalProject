package com.example.springbootweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SYSProduct {
    private Integer product_id;
    private String product_no;
    private String name;
    private Double price;
    private String product_image;
    private Integer product_type_id;
    private SYSProductType productType;
}
