package org.example.study.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.utils.PageUtils;
import org.example.common.utils.Query;

import org.example.study.dao.SmsViewLogDao;
import org.example.study.entity.SmsViewLogEntity;
import org.example.study.service.SmsViewLogService;


@Service("smsViewLogService")
public class SmsViewLogServiceImpl extends ServiceImpl<SmsViewLogDao, SmsViewLogEntity> implements SmsViewLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SmsViewLogEntity> page = this.page(
                new Query<SmsViewLogEntity>().getPage(params),
                new QueryWrapper<SmsViewLogEntity>()
        );

        return new PageUtils(page);
    }

}