package org.example.content.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.example.content.entity.CmsNewsEntity;
import org.example.content.service.CmsNewsService;
import org.example.common.utils.PageUtils;
import org.example.common.utils.R;



/**
 * 内容-资讯表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:00:48
 */
@RestController
@RequestMapping("content/cmsnews")
public class CmsNewsController {
    @Autowired
    private CmsNewsService cmsNewsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cmsNewsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
        public R info(@PathVariable("id") Long id){
		CmsNewsEntity cmsNews = cmsNewsService.getById(id);

        return R.ok().put("cmsNews", cmsNews);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@RequestBody CmsNewsEntity cmsNews){
		cmsNewsService.save(cmsNews);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@RequestBody CmsNewsEntity cmsNews){
		cmsNewsService.updateById(cmsNews);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] ids){
		cmsNewsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
