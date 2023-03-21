package reggie.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}
