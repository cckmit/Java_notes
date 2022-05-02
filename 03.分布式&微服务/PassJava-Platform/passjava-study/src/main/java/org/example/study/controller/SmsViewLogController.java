package org.example.study.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.example.study.entity.SmsViewLogEntity;
import org.example.study.service.SmsViewLogService;
import org.example.common.utils.PageUtils;
import org.example.common.utils.R;



/**
 * 学习-用户学习浏览记录表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:05:55
 */
@RestController
@RequestMapping("study/smsviewlog")
public class SmsViewLogController {
    @Autowired
    private SmsViewLogService smsViewLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = smsViewLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
        public R info(@PathVariable("id") Long id){
		SmsViewLogEntity smsViewLog = smsViewLogService.getById(id);

        return R.ok().put("smsViewLog", smsViewLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@RequestBody SmsViewLogEntity smsViewLog){
		smsViewLogService.save(smsViewLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@RequestBody SmsViewLogEntity smsViewLog){
		smsViewLogService.updateById(smsViewLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] ids){
		smsViewLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
