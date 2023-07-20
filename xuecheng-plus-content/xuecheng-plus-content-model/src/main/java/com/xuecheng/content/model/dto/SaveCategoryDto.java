package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Category;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author cuimy
 * @version 1.0
 * @description TODO
 * @date 2023/7/19 9:31
 */
@Data
@ToString
public class SaveCategoryDto extends Category implements Serializable {
}
