package org.example.member.dao;

import org.example.member.entity.UmsGrowthChangeHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员-积分值变化历史记录表
 * 
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:08:14
 */
@Mapper
public interface UmsGrowthChangeHistoryDao extends BaseMapper<UmsGrowthChangeHistoryEntity> {
	
}
