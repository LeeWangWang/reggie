package reggie.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.Mapper.EmployeeMapper;
import reggie.Service.EmployeeService;
import reggie.entity.Employee;

/**
 * @ClassName: EmployeeServiceImpl
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/12 20:32
 * @Version: V1
 **/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}