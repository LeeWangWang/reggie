package reggie.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reggie.entity.Orders;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
