package org.example.member.dao;

import org.example.member.entity.UmsMemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员-会员表
 * 
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:08:14
 */
@Mapper
public interface UmsMemberDao extends BaseMapper<UmsMemberEntity> {
	
}
