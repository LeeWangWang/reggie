package reggie.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reggie.common.CustomException;
import reggie.common.R;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @ClassName: GlobalExceptionHandler
 * @Description: 使用异常处理器进行全局异常捕获
 * @Author: lww
 * @Date: 2023/3/14 16:36
 * @Version: V1
 **/
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    //进行异常处理方法
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());

        if(ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg=split[2]+"已存在";
            return R.error(msg);
        }

        return R.error("未知错误");
    }

    /**
     * 进行异常处理方法
     * @param ex
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex){
        // 报错记得打日志
        log.error(ex.getMessage());

        // 这里拿到的message是业务类抛出的异常信息，我们把它显示到前端
        return R.error(ex.getMessage());
    }

}