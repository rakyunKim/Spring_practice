package kr.ccfc.aspect;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import kr.ccfc.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 内部api调用异常的处理
     */
    @ExceptionHandler(value = ApiException.class)
    public R<Object> handlerApiException(ApiException apiException) {
        IErrorCode errorCode = apiException.getErrorCode();
        if (errorCode != null) {
            return R.fail(errorCode);
        }
        log.error(apiException.getMessage(), apiException);
        return R.fail(apiException.getMessage());
    }

    /**
     * 方法参数校验失败异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        return getRes(exception.getBindingResult(), exception.getMessage());
    }

    /**
     * 方法参数校验异常
     * 对象内部使用Validate 没有校验成功异常
     */
    @ExceptionHandler(BindException.class)
    public R<Object> handlerBindException(BindException exception) {
        log.error(exception.getMessage(), exception);
        return getRes(exception.getBindingResult(), exception.getMessage());
    }

    private R<Object> getRes(BindingResult result, String message) {
        if (result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            if (fieldError != null) {
                return R.fail(fieldError.getField() + fieldError.getDefaultMessage());
            }
        }
        return R.fail(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public R<String> handlerIllegalArgumentException(IllegalArgumentException exception) {
        log.error(exception.getMessage(), exception);
        return R.fail(exception.getMessage());
    }
}
