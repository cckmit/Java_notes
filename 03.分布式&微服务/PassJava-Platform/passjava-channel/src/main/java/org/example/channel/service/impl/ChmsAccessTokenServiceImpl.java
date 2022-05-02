package org.example.channel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.utils.PageUtils;
import org.example.common.utils.Query;

import org.example.channel.dao.ChmsAccessTokenDao;
import org.example.channel.entity.ChmsAccessTokenEntity;
import org.example.channel.service.ChmsAccessTokenService;


@Service("chmsAccessTokenService")
public class ChmsAccessTokenServiceImpl extends ServiceImpl<ChmsAccessTokenDao, ChmsAccessTokenEntity> implements ChmsAccessTokenService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ChmsAccessTokenEntity> page = this.page(
                new Query<ChmsAccessTokenEntity>().getPage(params),
                new QueryWrapper<ChmsAccessTokenEntity>()
        );

        return new PageUtils(page);
    }

}