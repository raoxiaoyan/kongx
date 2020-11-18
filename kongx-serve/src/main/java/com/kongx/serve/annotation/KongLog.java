package com.kongx.serve.annotation;


import java.lang.annotation.*;

import static com.kongx.serve.entity.system.OperationLog.OperationTarget;
import static com.kongx.serve.entity.system.OperationLog.OperationType;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface KongLog {

    OperationType operation() default OperationType.OPERATION_DEFAULT;

    OperationTarget target();

    String content() default "";
}
