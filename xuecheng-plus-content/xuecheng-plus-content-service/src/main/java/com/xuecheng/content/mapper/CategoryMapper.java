package com.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.dto.CategoryDto;
import com.xuecheng.content.model.po.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itcast
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
    List<CategoryDto> selectTreeNodes(Long id);
    List<CategoryDto> allQueryTreeNodes();

    List<CategoryDto> upQueryTreeNodes(Long id);

    Integer getMAXOrderby(Long parentid);


}
