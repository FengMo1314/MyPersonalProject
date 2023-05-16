package com.example.springbootweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SYSStaff {
    private Integer staff_id;
    private String staff_name;
    private String login_name;
    private String password;
    private String phone;
    private String email;
    private Integer dept_id;
//    角色：0-普通，1-管理员
    private String role;
//    启用：0-启用，1-禁用
    private Integer is_valid;
    private String create_date;
    private Integer create_staff_id;
}
