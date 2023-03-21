package reggie.common;

/**
 * @ClassName: CustomException
 * @Description: TODO
 * @Author: lww
 * @Date: 2023/3/17 20:52
 * @Version: V1
 **/
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}