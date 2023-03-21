package reggie.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import reggie.entity.Dish;
import reggie.dto.DishDto;

public interface DishService extends IService<Dish> {

    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}
