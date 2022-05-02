package org.example.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.utils.PageUtils;
import org.example.study.entity.SmsStudyTimeEntity;

import java.util.Map;

/**
 * 学习-用户学习时常表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:05:55
 */
public interface SmsStudyTimeService extends IService<SmsStudyTimeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

