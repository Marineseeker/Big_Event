package com.example.demo.Validation;

import com.example.demo.annotation.StateConstraint;
import  jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidator implements ConstraintValidator<StateConstraint, String> {

    @Override
    public void initialize(StateConstraint constraintAnnotation) {
        // 初始化逻辑（如果需要）
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 允许为空的情况下，直接返回 true
        if (value == null || value.isEmpty()) {
            return true;
        }
        // 校验状态是否为 "已发布" 或 "草稿"
        return "已发布".equals(value) || "草稿".equals(value);
    }
}
