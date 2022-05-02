package org.example.study.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.example.study.entity.SmsStudyTimeEntity;
import org.example.study.service.SmsStudyTimeService;
import org.example.common.utils.PageUtils;
import org.example.common.utils.R;



/**
 * 学习-用户学习时常表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:05:55
 */
@RestController
@RequestMapping("study/smsstudytime")
public class SmsStudyTimeController {
    @Autowired
    private SmsStudyTimeService smsStudyTimeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = smsStudyTimeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
        public R info(@PathVariable("id") Long id){
		SmsStudyTimeEntity smsStudyTime = smsStudyTimeService.getById(id);

        return R.ok().put("smsStudyTime", smsStudyTime);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@RequestBody SmsStudyTimeEntity smsStudyTime){
		smsStudyTimeService.save(smsStudyTime);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@RequestBody SmsStudyTimeEntity smsStudyTime){
		smsStudyTimeService.updateById(smsStudyTime);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] ids){
		smsStudyTimeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
