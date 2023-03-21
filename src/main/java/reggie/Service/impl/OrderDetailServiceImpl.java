package reggie.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.Mapper.OrderDetailMapper;
import reggie.Service.OrderDetailService;
import reggie.entity.OrderDetail;

/**
 * @ClassName: OrderDetailServiceImpl
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/20 10:54
 * @Version: V1
 **/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}