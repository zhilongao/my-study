package com.study.mq.trans.controller;

import com.study.mq.trans.entity.SysUser;
import com.study.mq.trans.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/2/21 10:25
 * @since v1.0.0001
 */
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/test")
    public String message() {
        return "say hello";
    }

    @PostMapping("/insert")
    public String insert(@RequestBody SysUser sysUser) {
        sysUserService.insert(sysUser);
        return "ok";
    }

}
