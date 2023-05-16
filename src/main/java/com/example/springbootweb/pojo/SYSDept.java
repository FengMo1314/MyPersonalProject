package com.example.springbootweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SYSDept {
    private Integer dept_id;
    private String dept_name;
//    部门编号
    private String dept_no;
//    父级部门
    private Integer father_dept_id;
// 部门职能
    private String remark;
    private String create_date;
    private Integer create_staff_id;
//    是否启用：0启用，1禁用
    private Integer is_valid;
    private SYSDept fatherDept;

}
