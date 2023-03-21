package reggie.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.Mapper.DishFlavorMapper;
import reggie.Service.DishFlavorService;
import reggie.entity.DishFlavor;

/**
 * @ClassName: DishFlavorServiceImpl
 * @Description: 业务层实现类
 * @Author: lww
 * @Date: 2023/3/18 16:08
 * @Version: V1
 **/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}