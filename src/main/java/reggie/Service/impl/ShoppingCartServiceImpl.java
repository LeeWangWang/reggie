package reggie.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reggie.Controller.BaseContext;
import reggie.Mapper.ShoppingCartMapper;
import reggie.Service.ShoppingCartService;
import reggie.common.CustomException;
import reggie.entity.ShoppingCart;

import java.util.List;

/**
 * @ClassName: ShoppingCartServiceImpl
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/20 10:46
 * @Version: V1
 **/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}