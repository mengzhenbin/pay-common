/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.dream.pay.validators;


import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengzhenbin
 * @version V1.0
 * @date 2017年3月1日
 * @descrption 参数枚举校验<br/>
 */
public class InEnumValidator implements ConstraintValidator<InEnum, Object> {

    List<Object> valueList = null;

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (!valueList.contains(value)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void initialize(InEnum annotation) {

        if (StringUtils.isEmpty(annotation.property())) {
            //如果没有设置属性命，取枚举名称
            valueList = this.getNameList(annotation);
        } else {
            //如果设置了属性名，取属性的值。
            valueList = this.getPropertyValueList(annotation, annotation.property());
        }

    }


    /**
     * 获取名称列表，
     *
     * @param annotation 枚举注解
     * @return 名称列表
     */
    public List<Object> getNameList(InEnum annotation) {
        Class<? extends Enum<?>> enumClass = annotation.clazz();
        Enum[] enumValArr = enumClass.getEnumConstants();

        List<Object> names = new ArrayList<Object>();
        for (Enum enumVal : enumValArr) {
            names.add(enumVal.name().toUpperCase());
        }
        return names;
    }

    /**
     * 获取枚举所有特定属性值
     *
     * @param annotation
     * @param property
     * @return
     */
    public List<Object> getPropertyValueList(InEnum annotation, String property) {
        List<Object> values = new ArrayList<Object>();
        Class<? extends Enum<?>> enumClass = annotation.clazz();
        Enum[] enumValArr = enumClass.getEnumConstants();
        //方法名
        String methodName = "get" + StringUtils.capitalize(property);
        for (Enum enumVal : enumValArr) {
            //反射调用getter方法，获取属性值哦
            Method method = ReflectionUtils.findMethod(enumVal.getClass(), methodName);
            Object value = ReflectionUtils.invokeMethod(method, enumVal);
            values.add(value);
        }
        return values;
    }
}