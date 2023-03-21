package reggie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reggie.Service.CategoryService;
import reggie.common.R;
import reggie.entity.Category;

import java.util.List;

/**
 * @ClassName: CategoryController
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/17 10:25
 * @Version: V1
 **/
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分类信息分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        // 构造分页构造器
        Page<Category> pageInfo=new Page<>(page,pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Category::getSort);
        //进行分页查询
        categoryService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据分类id删除菜品分类信息
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") Long id) {
        log.info("删除分类，id为：", id);
//        categoryService.removeById(id);
        categoryService.remove(id);
        // 代码完善之后categoryService.remove(id);
        return R.success("分类信息删除成功");
    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("分类修改成功");
    }

    /**
     * 根据条件查询分类数据
     * 这个接口接收到参数其实就是一个前端传过来的type,这里之所以使用Category这个类来接受前端的数据，是为了以后方便
     * 因为这个Category类里面包含了type这个数据,返回的数据多了，你自己用啥取啥就行
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        // 条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        // 添加查询条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        // 添加排序条件,使用两个排序条件,如果sort相同的情况下就使用更新时间进行排序
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);
    }
}