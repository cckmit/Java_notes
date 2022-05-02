package org.example.question;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.question.entity.TypeEntity;
import org.example.question.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author cat
 * @description
 * @date 2022/5/2 下午2:40
 */
@SpringBootTest
public class MyTest {

    @Autowired
    TypeService typeService;

    // 创建题目类型
    @Test
    void testCreateType() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setType("javaBasic");
        typeService.save(typeEntity);
        System.out.println("创建成功");
    }

    // 查询题目类型
    @Test
    void testSelectType() {
        List<TypeEntity> typeEntityList = typeService.list(new QueryWrapper<TypeEntity>().eq("id",2L));
        typeEntityList.forEach((item)-> {
            System.out.println(item);
        });
        System.out.println("查询成功");
    }



}
