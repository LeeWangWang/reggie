package reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: reggie.ReggieApplication
 * @Description: 启动类
 * @Author: lww
 * @Date: 2023/3/12 19:52
 * @Version: V1
 **/
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
        log.info("项目开始启动");
    }
}