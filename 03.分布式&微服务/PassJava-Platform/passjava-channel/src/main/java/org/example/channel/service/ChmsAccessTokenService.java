package org.example.channel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.utils.PageUtils;
import org.example.channel.entity.ChmsAccessTokenEntity;

import java.util.Map;

/**
 * 渠道-认证表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 14:55:18
 */
public interface ChmsAccessTokenService extends IService<ChmsAccessTokenEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

