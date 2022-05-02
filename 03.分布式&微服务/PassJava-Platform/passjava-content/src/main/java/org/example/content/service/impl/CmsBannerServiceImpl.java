package org.example.content.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.utils.PageUtils;
import org.example.common.utils.Query;

import org.example.content.dao.CmsBannerDao;
import org.example.content.entity.CmsBannerEntity;
import org.example.content.service.CmsBannerService;


@Service("cmsBannerService")
public class CmsBannerServiceImpl extends ServiceImpl<CmsBannerDao, CmsBannerEntity> implements CmsBannerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CmsBannerEntity> page = this.page(
                new Query<CmsBannerEntity>().getPage(params),
                new QueryWrapper<CmsBannerEntity>()
        );

        return new PageUtils(page);
    }

}