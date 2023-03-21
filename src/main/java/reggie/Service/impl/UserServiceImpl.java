package reggie.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.Mapper.UserMapper;
import reggie.Service.UserService;
import reggie.entity.User;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/19 19:51
 * @Version: V1
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}