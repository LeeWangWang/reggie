package reggie.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}