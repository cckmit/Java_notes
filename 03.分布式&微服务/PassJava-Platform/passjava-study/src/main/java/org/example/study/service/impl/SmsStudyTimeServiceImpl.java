package org.example.study.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.utils.PageUtils;
import org.example.common.utils.Query;

import org.example.study.dao.SmsStudyTimeDao;
import org.example.study.entity.SmsStudyTimeEntity;
import org.example.study.service.SmsStudyTimeService;


@Service("smsStudyTimeService")
public class SmsStudyTimeServiceImpl extends ServiceImpl<SmsStudyTimeDao, SmsStudyTimeEntity> implements SmsStudyTimeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SmsStudyTimeEntity> page = this.page(
                new Query<SmsStudyTimeEntity>().getPage(params),
                new QueryWrapper<SmsStudyTimeEntity>()
        );

        return new PageUtils(page);
    }

}