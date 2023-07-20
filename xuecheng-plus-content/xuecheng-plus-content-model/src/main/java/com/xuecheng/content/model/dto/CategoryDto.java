package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Category;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author cuimy
 * @version 1.0
 * @description TODO
 * @date 2023/7/18 15:59
 */
@Data
@ToString
public class CategoryDto extends Category implements Serializable {
    List childrenTreeNodes;
}
