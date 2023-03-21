package reggie.dto;

import lombok.Data;
import reggie.entity.Setmeal;
import reggie.entity.SetmealDish;

import java.util.List;

/**
 * @ClassName: SetmealDto
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/19 15:11
 * @Version: V1
 **/
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}