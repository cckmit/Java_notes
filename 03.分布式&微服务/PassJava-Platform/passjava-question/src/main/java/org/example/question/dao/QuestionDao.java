package org.example.question.dao;

import org.example.question.entity.QuestionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 13:35:39
 */
@Mapper
public interface QuestionDao extends BaseMapper<QuestionEntity> {
	
}
