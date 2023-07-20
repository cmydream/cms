package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.CategoryMapper;
import com.xuecheng.content.model.dto.CategoryDto;
import com.xuecheng.content.model.dto.SaveCategoryDto;
import com.xuecheng.content.model.po.Category;
import com.xuecheng.content.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itcast
 */
@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    public List<CategoryDto> queryTreeNodes(Long id){
        //调用mapper递归查询出分类信息
        List<CategoryDto> categoryDtos = categoryMapper.selectTreeNodes(id);
        //找到每个节点的子节点，最终封装成List<CategoryDto>
        //先将list转成map，key就是结点的id，value就是CategoryDto对象，目的就是为了方便从map获取结点,filter(item->!id.equals(item.getId()))把根结点排除
        Map<Long, CategoryDto> mapTemp = categoryDtos.stream().collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        //定义一个list作为最终返回的list
        List<CategoryDto> categoryList = new ArrayList<>();
        //从头遍历 List<CategoryDto> ，一边遍历一边找子节点放在父节点的childrenTreeNodes
        categoryDtos.stream().forEach(item -> {
            if (item.getId().equals(id)) {
                categoryList.add(item);
            }
            //找到节点的父节点
            CategoryDto categoryParent = mapTemp.get(item.getParentid());
            if(categoryParent!=null){
                if(categoryParent.getChildrenTreeNodes()==null){
                    //如果该父节点的ChildrenTreeNodes属性为空要new一个集合，因为要向该集合中放它的子节点
                    categoryParent.setChildrenTreeNodes(new ArrayList<CategoryDto>());
                }
                //到每个节点的子节点放在父节点的childrenTreeNodes属性中
                categoryParent.getChildrenTreeNodes().add(item);
            }

        });

        return categoryList;

    }

    @Override
    public List<CategoryDto> allQueryTreeNodes() {
        //调用mapper查询出整张表
        List<CategoryDto> categoryDtos = categoryMapper.allQueryTreeNodes();
        //找到每个节点的子节点，最终封装成List<CategoryDto>
        //先将list转成map，key就是结点的id，value就是CategoryDto对象，目的就是为了方便从map获取结点,filter(item->!id.equals(item.getId()))把根结点排除
        Map<Long, CategoryDto> mapTemp = categoryDtos.stream().collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        //定义一个list作为最终返回的list
        List<CategoryDto> categoryList = new ArrayList<>();
        //从头遍历 List<CategoryDto> ，一边遍历一边找子节点放在父节点的childrenTreeNodes
        categoryDtos.stream().forEach(item -> {
            if (item.getParentid() == 0) {
                categoryList.add(item);
            }
            //找到节点的父节点
            CategoryDto categoryParent = mapTemp.get(item.getParentid());
            if(categoryParent!=null){
                if(categoryParent.getChildrenTreeNodes()==null){
                    //如果该父节点的ChildrenTreeNodes属性为空要new一个集合，因为要向该集合中放它的子节点
                    categoryParent.setChildrenTreeNodes(new ArrayList<CategoryDto>());
                }
                //到每个节点的子节点放在父节点的childrenTreeNodes属性中
                categoryParent.getChildrenTreeNodes().add(item);
            }

        });

        return categoryList;

    }

    @Override
    public CategoryDto upQueryTreeNodes(Long id) {
        List<CategoryDto> categoryDtos = categoryMapper.upQueryTreeNodes(id);
        if(categoryDtos == null)
            return null;
        CategoryDto categoryDto = categoryDtos.get(0);
        CategoryDto temp = categoryDto;
        for(int i =1; i < categoryDtos.size();i++){
            temp.setChildrenTreeNodes(new ArrayList<CategoryDto>());
            temp.getChildrenTreeNodes().add(categoryDtos.get(i));
            temp = (CategoryDto) temp.getChildrenTreeNodes().get(0);
        }
        return categoryDto;

    }

    @Override
    public void saveTreeNodes(SaveCategoryDto saveCategoryDto) {
        //有无分类id
        //有id为修改，无id为新增
        Long id = saveCategoryDto.getId();
        if(id!=null){
            Category category = categoryMapper.selectById(id);
            BeanUtils.copyProperties(saveCategoryDto,category);
            categoryMapper.updateById(category);
        }else {
            Integer maxOrderby = categoryMapper.getMAXOrderby(saveCategoryDto.getParentid());
            Category category = new Category();
            BeanUtils.copyProperties(saveCategoryDto,category);
            category.setOrderby(maxOrderby+1);
            categoryMapper.insert(category);
        }

    }

    @Transactional
    @Override
    public void deleteId(Long Id) {
        if (Id == null) {
            XueChengPlusException.cast("分类id为空");
        }
        List<CategoryDto> categoryDtos = categoryMapper.selectTreeNodes(Id);
        for (CategoryDto cate :
                categoryDtos) {
            Long id = cate.getId();
            categoryMapper.deleteById(id);
        }

    }

    @Transactional
    @Override
    public void updateStatus(Long id) {
        if (id == null) {
            XueChengPlusException.cast("分类id为空");
        }
        List<CategoryDto> categoryDtos = categoryMapper.selectTreeNodesWithoutStatus(id);
        for (CategoryDto cate :
                categoryDtos) {
            int status = cate.getStatus();
            if(status == 1)status=0;else status=1;
            cate.setStatus(status);
            categoryMapper.updateById(cate);
        }
    }
}
