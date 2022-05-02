package org.example.content.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.example.content.entity.CmsBannerEntity;
import org.example.content.service.CmsBannerService;
import org.example.common.utils.PageUtils;
import org.example.common.utils.R;



/**
 * 内容-横幅广告表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:00:48
 */
@RestController
@RequestMapping("content/cmsbanner")
public class CmsBannerController {
    @Autowired
    private CmsBannerService cmsBannerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cmsBannerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
        public R info(@PathVariable("id") Long id){
		CmsBannerEntity cmsBanner = cmsBannerService.getById(id);

        return R.ok().put("cmsBanner", cmsBanner);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@RequestBody CmsBannerEntity cmsBanner){
		cmsBannerService.save(cmsBanner);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@RequestBody CmsBannerEntity cmsBanner){
		cmsBannerService.updateById(cmsBanner);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] ids){
		cmsBannerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
