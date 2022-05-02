package org.example.study.dao;

import org.example.study.entity.SmsViewLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学习-用户学习浏览记录表
 * 
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:05:55
 */
@Mapper
public interface SmsViewLogDao extends BaseMapper<SmsViewLogEntity> {
	
}
