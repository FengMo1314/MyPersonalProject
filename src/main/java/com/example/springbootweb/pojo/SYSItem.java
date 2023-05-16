package com.example.springbootweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SYSItem {
  private Integer id;
  private Integer product_id;
  private Integer num;
  private Integer order_id;
  private Double price;
}
