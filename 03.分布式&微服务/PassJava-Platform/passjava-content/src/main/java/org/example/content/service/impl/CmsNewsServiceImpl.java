package org.example.content.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.utils.PageUtils;
import org.example.common.utils.Query;

import org.example.content.dao.CmsNewsDao;
import org.example.content.entity.CmsNewsEntity;
import org.example.content.service.CmsNewsService;


@Service("cmsNewsService")
public class CmsNewsServiceImpl extends ServiceImpl<CmsNewsDao, CmsNewsEntity> implements CmsNewsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CmsNewsEntity> page = this.page(
                new Query<CmsNewsEntity>().getPage(params),
                new QueryWrapper<CmsNewsEntity>()
        );

        return new PageUtils(page);
    }

}