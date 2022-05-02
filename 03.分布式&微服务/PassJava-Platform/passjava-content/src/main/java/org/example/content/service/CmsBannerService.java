package org.example.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.utils.PageUtils;
import org.example.content.entity.CmsBannerEntity;

import java.util.Map;

/**
 * 内容-横幅广告表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:00:48
 */
public interface CmsBannerService extends IService<CmsBannerEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

