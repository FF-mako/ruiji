package com.itheima.exception;

import com.itheima.common.R;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@ControllerAdvice(annotations = RestController.class)
public class ProjectExceptionHandler extends Exception{

    @ExceptionHandler(CategoryException.class)
    public R handerException(Exception e){
        return R.error(e.getMessage());
    }
}
