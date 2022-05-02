package org.example.member.controller;


import org.example.common.utils.R;
import org.example.member.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//添加注解@RefreshScope开启动态刷新配置功能
@RefreshScope
@RestController
@RequestMapping("member/sample")
public class SampleController {

    @Autowired
    UmsMemberService memberService;

    @Value("${member.nick}")
    private  String nickname;

    @Value("${member.age}")
    private  Integer age;

    @RequestMapping("/test-local-config")
    public R testLocalConfig() {
        return R.ok().put("nickname", nickname).put("age", age);
    }


    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
    }
}
