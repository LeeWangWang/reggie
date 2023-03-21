package reggie.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reggie.Mapper.CategoryMapper;
import reggie.Service.CategoryService;
import reggie.Service.DishService;
import reggie.Service.SetmealService;
import reggie.common.CustomException;
import reggie.entity.Category;
import reggie.entity.Dish;
import reggie.entity.Setmeal;

/**
 * @ClassName: CategoryServiceImpl
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/17 10:24
 * @Version: V1
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联菜品,如果已经关联，抛出业务异常
        if(count1 > 0){
            //已经关联菜品，抛出业务异常
            throw new CustomException("已经关联菜品，不能删除");
        }
        //查询当前分类是否关联了套餐，如果已经关联，抛出业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);

        if(count2 > 0){
            //已经关联套餐，抛出业务异常
            throw new CustomException("已经关联套餐，不能删除");
        }
        //正常删除分类
        super.removeById(id);
    }
}