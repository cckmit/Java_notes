package org.example.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.utils.PageUtils;
import org.example.question.entity.QuestionEntity;

import java.util.Map;

/**
 * 
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 13:35:39
 */
public interface QuestionService extends IService<QuestionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

