package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.dto.CategoryDto;
import com.xuecheng.content.model.dto.SaveCategoryDto;
import com.xuecheng.content.model.po.Category;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author itcast
 * @since 2023-07-18
 */
public interface CategoryService extends IService<Category> {
    List<CategoryDto> queryTreeNodes(Long id);

    List<CategoryDto> allQueryTreeNodes();

    CategoryDto upQueryTreeNodes(Long id);

    void saveTreeNodes(SaveCategoryDto saveCategoryDto);

    void deleteId(Long Id);

    void updateStatus(Long id);
}
