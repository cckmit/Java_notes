package org.example.channel.dao;

import org.example.channel.entity.ChmsAccessTokenEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 渠道-认证表
 * 
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 14:55:18
 */
@Mapper
public interface ChmsAccessTokenDao extends BaseMapper<ChmsAccessTokenEntity> {
	
}
