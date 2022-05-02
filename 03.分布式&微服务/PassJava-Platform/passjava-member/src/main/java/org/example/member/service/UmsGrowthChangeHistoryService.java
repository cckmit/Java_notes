package org.example.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.utils.PageUtils;
import org.example.member.entity.UmsGrowthChangeHistoryEntity;

import java.util.Map;

/**
 * 会员-积分值变化历史记录表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:08:14
 */
public interface UmsGrowthChangeHistoryService extends IService<UmsGrowthChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

