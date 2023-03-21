package reggie.dto;

import lombok.Data;
import reggie.entity.OrderDetail;
import reggie.entity.Orders;

import java.util.List;

/**
 * @ClassName: OrderDto
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/20 14:16
 * @Version: V1
 **/
@Data
public class OrderDto extends Orders {
    private List<OrderDetail> orderDetails;
}