package reggie.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.Mapper.AddressBookMapper;
import reggie.Service.AddressBookService;
import reggie.entity.AddressBook;

/**
 * @ClassName: AddressBookServiceImpl
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/20 10:18
 * @Version: V1
 **/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}