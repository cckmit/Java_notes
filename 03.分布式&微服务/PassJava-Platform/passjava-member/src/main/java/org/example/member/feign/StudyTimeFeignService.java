package org.example.member.feign;

import org.example.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cat
 * @description
 * @date 2022/5/2 下午5:56
 */
@FeignClient("passjava-study")
@RequestMapping("study/smsstudytime")
public interface StudyTimeFeignService {

    @RequestMapping("/member/list/test")
    public R memberStudyTimeTest();
}
