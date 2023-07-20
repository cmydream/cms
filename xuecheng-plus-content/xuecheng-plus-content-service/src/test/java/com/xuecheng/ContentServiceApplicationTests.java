package com.xuecheng;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CategoryMapper;
import com.xuecheng.content.model.dto.CategoryDto;
import com.xuecheng.content.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ContentServiceApplicationTests {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryService categoryService;



    @Test
    void testAllQueryTreeNodes(){
        List<CategoryDto> categoryDtos = categoryService.allQueryTreeNodes();
        System.out.println(categoryDtos);
    }

    @Test
    void testUpQueryTreeNodes(){
        CategoryDto categoryDto = categoryService.upQueryTreeNodes(10L);
        System.out.println(categoryDto);

    }

    @Test
    void testUpQueryTreeNodesMapper(){
        List<CategoryDto> categoryDtos = categoryMapper.upQueryTreeNodes(10L);
        System.out.println(categoryDtos);

    }

}
