package org.example.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.utils.PageUtils;
import org.example.question.entity.TypeEntity;

import java.util.Map;

/**
 * 题目-题目类型表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 13:35:39
 */
public interface TypeService extends IService<TypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

