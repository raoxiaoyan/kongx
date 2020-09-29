package com.kongx.common.exception;

import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = "com.kongx.kong")
@Slf4j
public class ExceptionGlobalHandler {
    @ResponseBody
    @ExceptionHandler(value = FeignException.class)
    public <T> JsonHeaderWrapper<T> errorHandler(FeignException ex) {
        FeignException exception = (FeignException) ex.getCause();
        JsonHeaderWrapper<T> jsonWrapper = new JsonHeaderWrapper<>();
        jsonWrapper.setStatus(ex.status());
        jsonWrapper.setErrmsg(ex.getMessage());
        log.info("Error msg{}", ex.getMessage());
        return jsonWrapper;
    }
}
