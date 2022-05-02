package org.example.channel.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.example.channel.entity.ChmsChannelEntity;
import org.example.channel.service.ChmsChannelService;
import org.example.common.utils.PageUtils;
import org.example.common.utils.R;



/**
 * 渠道-渠道表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 14:55:18
 */
@RestController
@RequestMapping("channel/chmschannel")
public class ChmsChannelController {
    @Autowired
    private ChmsChannelService chmsChannelService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("channel:chmschannel:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = chmsChannelService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("channel:chmschannel:info")
    public R info(@PathVariable("id") Long id){
		ChmsChannelEntity chmsChannel = chmsChannelService.getById(id);

        return R.ok().put("chmsChannel", chmsChannel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("channel:chmschannel:save")
    public R save(@RequestBody ChmsChannelEntity chmsChannel){
		chmsChannelService.save(chmsChannel);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("channel:chmschannel:update")
    public R update(@RequestBody ChmsChannelEntity chmsChannel){
		chmsChannelService.updateById(chmsChannel);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("channel:chmschannel:delete")
    public R delete(@RequestBody Long[] ids){
		chmsChannelService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
