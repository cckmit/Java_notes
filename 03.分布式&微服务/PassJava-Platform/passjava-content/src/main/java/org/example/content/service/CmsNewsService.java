package org.example.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.utils.PageUtils;
import org.example.content.entity.CmsNewsEntity;

import java.util.Map;

/**
 * 内容-资讯表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:00:48
 */
public interface CmsNewsService extends IService<CmsNewsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

