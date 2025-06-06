package com.trade.common.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 参数校验工具类
 * @author Trade Team
 */
public class ValidationUtil {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验对象
     * @param obj 待校验对象
     * @param <T> 对象类型
     * @throws IllegalArgumentException 如果校验失败，则抛出此异常
     */
    public static <T> void validate(T obj) {
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(obj);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getMessage()).append(";");
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /**
     * 校验对象的指定属性
     * @param obj 待校验对象
     * @param propertyName 待校验属性名
     * @param <T> 对象类型
     * @throws IllegalArgumentException 如果校验失败，则抛出此异常
     */
    public static <T> void validateProperty(T obj, String propertyName) {
        Set<ConstraintViolation<T>> violations = VALIDATOR.validateProperty(obj, propertyName);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getMessage()).append(";");
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }
}