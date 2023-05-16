package com.example.springbootweb.controller;

import com.example.springbootweb.Constant.Constant;
import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.pojo.SYSDept;
import com.example.springbootweb.pojo.SYSStaff;
import com.example.springbootweb.service.SYSDeptService;
import com.example.springbootweb.service.SYSStaffService;
import com.example.springbootweb.util.JwtUtil;
import com.example.springbootweb.util.LHAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门表控制
 */
@LHAuth("1")
@RestController
@RequestMapping("/sys_dept")
@Slf4j//日志打印
public class SYSDeptController {
    @Autowired
    private SYSDeptService sysDeptService;
    @Autowired
    private SYSStaffService sysStaffService;
    private Map<String, Object> rm;
    private SYSDept dept;

    @GetMapping
    @ResponseBody
    public LHResult getAllDept() {
        rm = new HashMap<>();
        List<SYSDept> all = sysDeptService.getAllDept();
        rm.put("alldept", all);
        return LHResult.succ("获取所Dept成功", rm);
    }

    @PostMapping
    @ResponseBody
    public LHResult addDept(@RequestHeader("Authorization") String token, @RequestBody SYSDept getDept) {
        Integer uId = JwtUtil.verifyToken(token).get("uId").asInt();
        System.out.println("uId" + uId);
        Integer staffId = sysStaffService.getUserByUId(uId).getStaff_id();
        System.out.println("staffId" + staffId);
        dept = new SYSDept();
        dept.setDept_name(getDept.getDept_name());
        dept.setRemark(getDept.getRemark());
        dept.setCreate_staff_id(staffId);
        dept.setFather_dept_id(getDept.getFather_dept_id());
        if (sysDeptService.getDeptByName(getDept.getDept_name()) != null) {
            return LHResult.others("部门已存在");
        }
        Integer row = sysDeptService.addDept(dept);
        if (row <= 0) {
            return LHResult.fail("入库失败");
        }
        return LHResult.succ("添加部门OK");
    }

    @PutMapping("/disable/{dept_id}")
    @ResponseBody
    public LHResult disable(@PathVariable("dept_id") Integer deptId) {
        dept = new SYSDept();
        dept.setDept_id(deptId);
        disable(dept);
        return LHResult.succ("禁用部门"+dept.getDept_name()+"成功");
    }

    private void disable(SYSDept dept) {
        dept.setIs_valid(Constant.TABLE_FALSE);
        sysDeptService.updateDeptByDeptId(dept);
        //获取当前部门的子部门
        List<SYSDept> deptList = sysDeptService.getAllByFatherDeptId(dept.getDept_id());
        for (SYSDept d : deptList) {
            disable(d);
        }
    }

    /**
     * 开启部门
     *
     */
    @PutMapping("/enable/{dept_id}")
    @ResponseBody
    public LHResult enable(@PathVariable("dept_id") Integer deptId) {
//       查询需要被开启的Dept
        dept = sysDeptService.getDeptByDeptId(deptId);
        System.out.println("getDeptF_id" + dept.getFather_dept_id());
        if (dept.getFather_dept_id() != null) {
//通过父级id查询其父级部门的
            SYSDept fatherDept = sysDeptService.getDeptByDeptId(dept.getFather_dept_id());
            if (fatherDept != null && fatherDept.getIs_valid().equals(Constant.TABLE_FALSE)) {
                return LHResult.others("父级部门"+fatherDept.getDept_name()+"已被禁用");
            }
        }
        System.out.println("d:" + dept);
//       修改状态
        dept.setIs_valid(Constant.TABLE_TRUE);
        Integer row = sysDeptService.updateDeptByDeptId(dept);
        if (row <= 0) {
            return LHResult.fail("入库失败 ");
        }
        return LHResult.succ("启用部门"+dept.getDept_name()+"成功");
    }
    @PutMapping
    @ResponseBody
    public LHResult updateDeptById(@RequestParam Map<String,String> getMesg) {
        String deptName=getMesg.get("dept_name");
        Integer deptId= Integer.valueOf(getMesg.get("dept_id"));
        String deptRemark=getMesg.get("remark");
        dept = sysDeptService.getDeptByName(deptName);
        if (dept!=null){
            if (!dept.getDept_id().equals(deptId)) {
               return LHResult.others("部门名称已存在");
            }
        }
        dept=new SYSDept();
        dept.setDept_name(deptName);
        dept.setDept_id(deptId);
        dept.setRemark(deptRemark);
//        updateDeptById(dept);
        sysDeptService.updateDeptByDeptId(dept);
        return LHResult.succ("更新成功");
    }
    public void updateDeptById(SYSDept dept) {
        //        获取其所有子部门
        List<SYSDept> sonList=sysDeptService.getAllByFatherDeptId(dept.getDept_id());
        for (SYSDept s:sonList) {
            s.setDept_name(dept.getDept_name());
            sysDeptService.updateDeptByDeptId(s);
            updateDeptById(s);
        }
    }
}
