package reggie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reggie.Service.CategoryService;
import reggie.Service.SetmealDishService;
import reggie.Service.SetmealService;
import reggie.common.R;
import reggie.dto.SetmealDto;
import reggie.entity.Category;
import reggie.entity.Orders;
import reggie.entity.Setmeal;
import reggie.entity.SetmealDish;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: SetmealController
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/19 15:30
 * @Version: V1
 **/
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐: 涉及两张表的操作：套餐表和菜品表；
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        // 分页构造器对象
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>(page,pageSize);

        //构造条件查询对象
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        //添加查询条件，根据name进行like模糊查询
        queryWrapper.like(name != null,Setmeal::getName,name);

        //添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);

        //对象的拷贝  注意这里要把分页数据的全集合records给忽略掉
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        //对records对象进行处理然后封装好赋值给list
        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();

            //对setmealDto进行除categoryName的属性进行拷贝(因为item里面没有categoryName)
            BeanUtils.copyProperties(item,setmealDto);

            //获取分类id  通过分类id获取分类对象  然后再通过分类对象获取分类名
            Long categoryId = item.getCategoryId();

            //根据分类id获取分类对象  判断是否为null
            Category category = categoryService.getById(categoryId);

            if (category != null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);

        /**
         * 注意如果这里直接返回R.success(pageInfo)，虽然不会报错但是分页的数据的套餐分类的名字是显示不了的；
         * 因为这个分页的泛型是Setmeal,Setmeal只封装了f分类的Id categoryId，没有分类的名称 name
         * 所以又需要进行name的获取和设值
         */
        return R.success(pageInfo);
    }

    /**
     * 根据套餐id修改售卖状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable("status") Integer status, @RequestParam List<Long> ids) {
        setmealService.updateSetmealStatusById(status, ids);
        return R.success("售卖状态修改成功");
    }

    /**
     * 回显套餐数据：根据套餐id查询套餐
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getData(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getDate(id);
        return R.success(setmealDto);
    }

    /**
     * 修改套餐内容
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> edit(@RequestBody SetmealDto setmealDto){

        if (setmealDto==null){
            return R.error("请求异常");
        }

        if (setmealDto.getSetmealDishes()==null){
            return R.error("套餐没有菜品,请添加套餐");
        }
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        Long setmealId = setmealDto.getId();

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealId);
        setmealDishService.remove(queryWrapper);

        //为setmeal_dish表填充相关的属性
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealId);
        }
        //批量把setmealDish保存到setmeal_dish表
        setmealDishService.saveBatch(setmealDishes);
        setmealService.updateById(setmealDto);

        return R.success("套餐修改成功");
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);

    }
}