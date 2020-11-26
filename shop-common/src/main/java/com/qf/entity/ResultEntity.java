package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultEntity<T> {

    public static final String SUCEESS= "success";

    public static final String ERROR= "error";

    private static final String NO_MSG= "no_msg";

    private static final String SUCCESS_MSG= "操作成功";
    private static final String ERROR_MSG= "操作失败";


    private String status;

    private String msg;

    private T data;

    /**
     * 返回成功没有提示消息，也没有数据
     * @param <T>
     * @return
     */
    public static <T> ResultEntity<T> success(){
        return new ResultEntity<>(SUCEESS,SUCCESS_MSG,null);
    }

    public static <T> ResultEntity<T> success(T t){
        return new ResultEntity<>(SUCEESS,SUCCESS_MSG,t);
    }

    /**
     * 返回失败
     * @param msg 错误信息
     * @param <T>
     * @return
     */
    public static <T> ResultEntity<T> error(String msg){
        return new ResultEntity<>(ERROR,msg,null);
    }

    public static <T> ResultEntity<T> error(){
        return new ResultEntity<>(ERROR,ERROR_MSG,null);
    }

    /**
     *
     * @param t
     * @param <T> int,bollean,object
     * @return
     */
    public static <T> ResultEntity<T> response(T t){

        // 判断返回值是否是int类型
        if(t instanceof  Integer){
            Integer x = (Integer) t;
            if(x > 0){
                return success(t);
            }else{
                return error();
            }
        }else if(t instanceof Boolean){
            Boolean x = (Boolean) t;
            if(x){
                return  success(t);
            }else{
                return  error();
            }
        }else{
            return  success(t);
        }
    }

}
