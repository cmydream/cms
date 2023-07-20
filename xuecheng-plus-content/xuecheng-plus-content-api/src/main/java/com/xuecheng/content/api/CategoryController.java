package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.CategoryDto;
import com.xuecheng.content.model.dto.SaveCategoryDto;
import com.xuecheng.content.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author itcast
 */
@Slf4j
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    /**
     * @description 输入分类条目ID，以该分类条目为父节点，输出该分类条目所有子节点
     */
    @GetMapping("/down-tree-nodes/{Id}")
    public List<CategoryDto> queryTreeNodes(@PathVariable Long Id) {
        List<CategoryDto> categoryDtos = categoryService.queryTreeNodes(Id);
        return categoryDtos;
    }

    /**
     * @description 查询所有分类条目
    */
    @GetMapping("/all-tree-nodes")
    public List<CategoryDto> AllQueryTreeNodes() {
        List<CategoryDto> categoryDtos = categoryService.allQueryTreeNodes();
        return categoryDtos;
    }

    /**
     * @description 输入分类条目ID，以该分类条目为父节点，输出该分类条目所有子节点
     */
    @GetMapping("/up-tree-nodes/{Id}")
    public CategoryDto upQueryTreeNodes(@PathVariable Long Id) {
        CategoryDto categoryDto = categoryService.upQueryTreeNodes(Id);
        return categoryDto;

    }

    /**
     * @description 新增修改分类目录
    */
    @PostMapping("/save-tree-nodes")
    public void saveTreeNodes(@RequestBody SaveCategoryDto saveCategoryDto){
        categoryService.saveTreeNodes(saveCategoryDto);

    }

    /**
     *
     * @description 分类删除
     */
    @ApiOperation("分类删除")
    @DeleteMapping("/deleteId/{Id}")
    public void deleteId(@PathVariable Long Id) {

        categoryService.deleteId(Id);
    }

    /**
     *
     * @description 分类状态更新
     */
    @ApiOperation("分类更新")
    @PostMapping ("/updateStatus/{Id}")
    public void updateStatus(@PathVariable Long Id) {

        categoryService.updateStatus(Id);
    }
}
