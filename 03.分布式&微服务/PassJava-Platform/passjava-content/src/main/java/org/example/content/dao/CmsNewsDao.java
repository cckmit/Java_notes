package org.example.content.dao;

import org.example.content.entity.CmsNewsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 内容-资讯表
 * 
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:00:48
 */
@Mapper
public interface CmsNewsDao extends BaseMapper<CmsNewsEntity> {
	
}
