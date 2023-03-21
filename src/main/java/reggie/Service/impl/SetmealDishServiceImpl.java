package reggie.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reggie.Mapper.SetmealDishMapper;
import reggie.Service.SetmealDishService;
import reggie.entity.SetmealDish;

/**
 * @ClassName: SetmealDishServiceImpl
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/19 15:15
 * @Version: V1
 **/
@Slf4j
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}