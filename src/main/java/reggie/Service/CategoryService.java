package reggie.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import reggie.entity.Category;

public interface CategoryService  extends IService<Category> {

    void remove(Long id);
}
