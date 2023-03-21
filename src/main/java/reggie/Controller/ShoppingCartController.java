package reggie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reggie.Service.ShoppingCartService;
import reggie.common.R;
import reggie.entity.ShoppingCart;

import java.util.List;

/**
 * @ClassName: ShoppingCartController
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/20 10:47
 * @Version: V1
 **/
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){

        //先设置用户id,指定当前是哪个用户的购物车数据  因为前端没有传这个id给我们,但是这个id又非常重要（数据库这个字段不能为null）,
        // 所以要想办法获取到,我们在用户登录的时候就已经保存了用户的id
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);

        if (dishId != null){
            //添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            //添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        //查询当前菜品是否或者是套餐是否在购物车中
        //SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id = ?
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);

        if (cartServiceOne != null) {
            //如果已经存在,就在原来的数量基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number+1);
            shoppingCartService.updateById(cartServiceOne);
        }else {
            //如果不存在,则添加到购物车，数量默认是1
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
        return R.success(cartServiceOne);
    }

    /**
     * 查看购物车
     * @return
     * 前端没有传数据给我们，这里就不用接收了
     */
    @GetMapping("list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return R.success(list);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        //前端请求携带的参数是没有用户id的,所以要获取用户id
        Long userId = BaseContext.getCurrentId();
        //创建查询条件
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userId);
        shoppingCartService.remove(queryWrapper);
        return R.success("清空购物车成功");
    }

    /**
     * 客户端的套餐或者是菜品数量减少设置
     * 没必要设置返回值
     * @param shoppingCart
     */
    @PostMapping("/sub")
    @Transactional
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){

        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

        //代表数量减少的是菜品数量
        if (dishId != null){
            //通过dishId查出购物车对象
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
            //这里必须要加两个条件，否则会出现用户互相修改对方与自己购物车中相同套餐或者是菜品的数量
            queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
            ShoppingCart cart1 = shoppingCartService.getOne(queryWrapper);
            cart1.setNumber(cart1.getNumber()-1);
            Integer LatestNumber = cart1.getNumber();
            if (LatestNumber > 0){
                //对数据进行更新操作
                shoppingCartService.updateById(cart1);
            }else if(LatestNumber == 0){
                //如果购物车的菜品数量减为0，那么就把菜品从购物车删除
                shoppingCartService.removeById(cart1.getId());
            }else if (LatestNumber < 0){
                return R.error("操作异常");
            }

            return R.success(cart1);
        }

        Long setmealId = shoppingCart.getSetmealId();
        if (setmealId != null){
            //代表是套餐数量减少
            queryWrapper.eq(ShoppingCart::getSetmealId,setmealId).eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
            ShoppingCart cart2 = shoppingCartService.getOne(queryWrapper);
            cart2.setNumber(cart2.getNumber()-1);
            Integer LatestNumber = cart2.getNumber();
            if (LatestNumber > 0){
                //对数据进行更新操作
                shoppingCartService.updateById(cart2);
            }else if(LatestNumber == 0){
                //如果购物车的套餐数量减为0，那么就把套餐从购物车删除
                shoppingCartService.removeById(cart2.getId());
            }else if (LatestNumber < 0){
                return R.error("操作异常");
            }
            return R.success(cart2);
        }
        //如果两个大if判断都进不去
        return R.error("操作异常");
    }

}