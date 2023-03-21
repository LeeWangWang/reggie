package reggie.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reggie.Mapper.SetmealMapper;
import reggie.Service.SetmealDishService;
import reggie.Service.SetmealService;
import reggie.common.CustomException;
import reggie.dto.SetmealDto;
import reggie.entity.Setmeal;
import reggie.entity.SetmealDish;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: SetmealServiceImpl
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/17 16:27
 * @Version: V1
 **/
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal,执行insert
        this.save(setmealDto);
        log.info(setmealDto.toString()); //查看一下这个套餐的基本信息是什么

        //保存套餐和菜品的关联信息，操作setmeal_dish ,执行insert操作
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        //注意上面拿到的setmealDishes是没有setmeanlId这个的值的，通过debug可以发现
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item; //这里返回的就是集合的泛型
        }).collect(Collectors.toList());

        // 批量保存
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        // sql语句应该是这样的:select count(*) setmeal where id in () and status = 1;
        // 查询套餐的状态，看是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        // 如果不能删除，抛出一个业务异常
        if (count > 0){
            throw new CustomException("套餐正在售卖中,不能删除");
        }
        // 如果可以删除，先删除套餐表中的数据--setmeal
        this.removeByIds(ids);
        // 删除关系表中的数据--setmeal_dish
        // delete from setmeal_dish where setmeal_id in (1,2,3)
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(lambdaQueryWrapper);
    }

    /**
     * 根据套餐id修改售卖状态
     * @param status
     * @param ids
     */
    @Override
    @Transactional
    public void updateSetmealStatusById(Integer status, List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(ids !=null,Setmeal::getId,ids);
        List<Setmeal> list = this.list(queryWrapper);

        for (Setmeal setmeal : list) {
            if (setmeal != null){
                setmeal.setStatus(status);
                this.updateById(setmeal);
            }
        }
    }

    /**
     * 根据套餐id查询套餐
     * @param id
     * @return
     */
    @Override
    public SetmealDto getDate(Long id) {
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper();
        //在关联表中查询，setmealdish
        queryWrapper.eq(id!=null,SetmealDish::getSetmealId,id);

        if (setmeal != null){
            BeanUtils.copyProperties(setmeal,setmealDto);
            List<SetmealDish> list = setmealDishService.list(queryWrapper);
            setmealDto.setSetmealDishes(list);
            return setmealDto;
        }
        return null;
    }

}