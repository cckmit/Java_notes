package org.example.member.controller;

import java.util.Arrays;
import java.util.Map;

import org.example.member.feign.StudyTimeFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.example.member.entity.UmsMemberEntity;
import org.example.member.service.UmsMemberService;
import org.example.common.utils.PageUtils;
import org.example.common.utils.R;

import javax.annotation.Resource;


/**
 * 会员-会员表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:08:14
 */
@RestController
@RequestMapping("member/umsmember")
public class UmsMemberController {
    @Autowired
    private UmsMemberService umsMemberService;

    @Autowired
    private StudyTimeFeignService studyTimeFeignService;


    @RequestMapping("/studytime/list/test/{id}")
    public R getMemberStudyTimeListTest(@PathVariable("id") Long id) {
        //mock数据库查到的会员信息
        UmsMemberEntity memberEntity = new UmsMemberEntity();
        memberEntity.setId(id); // 学习时长：100分钟
        memberEntity.setNickname("悟空聊架构");

        //远程调用拿到该用户的学习时长（学习时长是mock数据）
        R memberStudyTimeList = studyTimeFeignService.memberStudyTimeTest();
        return R.ok().put("member", memberEntity).put("studytime", memberStudyTimeList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = umsMemberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
        public R info(@PathVariable("id") Long id){
		UmsMemberEntity umsMember = umsMemberService.getById(id);

        return R.ok().put("umsMember", umsMember);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@RequestBody UmsMemberEntity umsMember){
		umsMemberService.save(umsMember);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@RequestBody UmsMemberEntity umsMember){
		umsMemberService.updateById(umsMember);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] ids){
		umsMemberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
