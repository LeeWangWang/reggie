package reggie.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import reggie.dto.SetmealDto;
import reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    // 新增套餐，同时需要新增套餐和菜品的关联数据
    void saveWithDish(SetmealDto setmealDto);

    // 删除套餐，同时需要删除套餐和菜品的关联数据
    void removeWithDish(List<Long> ids);

    // 根据套餐id修改售卖状态
    void updateSetmealStatusById(Integer status,List<Long> ids);

    // 根据套餐id查询套餐
    SetmealDto getDate(Long id);
}
