package com.example.demo.annotation;

import com.example.demo.Validation.StateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StateValidator.class) // 指定校验器
@Target({ ElementType.FIELD }) // 应用于字段
@Retention(RetentionPolicy.RUNTIME)
public @interface StateConstraint {
    String message() default "发布状态只能是'已发布'或'草稿'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
