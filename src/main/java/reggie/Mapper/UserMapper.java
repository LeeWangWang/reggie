package reggie.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reggie.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
