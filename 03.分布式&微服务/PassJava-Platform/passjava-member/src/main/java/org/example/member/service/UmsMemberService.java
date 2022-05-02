package org.example.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.utils.PageUtils;
import org.example.member.entity.UmsMemberEntity;

import java.util.Map;

/**
 * 会员-会员表
 *
 * @author Lemonade19
 * @email 17782975312@163.com
 * @date 2022-05-02 17:08:14
 */
public interface UmsMemberService extends IService<UmsMemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

