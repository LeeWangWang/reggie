package reggie.dto;

import lombok.Data;
import reggie.entity.Dish;
import reggie.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: DishDto
 * @Description: 封装页面提交的数据
 * 全称为Data Transfer object，即数据传输对象，一般用于展示层与服务层之间的数据传输。
 * @Author: lww
 * @Date: 2023/3/18 20:44
 * @Version: V1
 **/
@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}