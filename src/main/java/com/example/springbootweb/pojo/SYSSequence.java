package com.example.springbootweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SYSSequence {
    private Integer id;
    private String name;
    private String value;
}
