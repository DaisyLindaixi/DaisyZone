package com.daisyZone.core.utils.pageQuery;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

/**
 * 分页拦截器反射帮助类
 * 
 * @ClassName: ReflectHelper
 * @author: fuyinan
 * @date: 2016年7月7日 下午3:04:29
 */
public class ReflectHelper {
  /**
   * 反射获取变量的值
   * 
   * @Title: getFieldValue
   * @param obj 反射对象
   * @param fieldName
   * @return
   * @return: Object
   */
  public static Object getFieldValue(Object obj, String fieldName) {

    if (obj == null) {
      return null;
    }

    Field targetField = getTargetField(obj.getClass(), fieldName);

    try {
      // 读取私有或公共变量的值
      return FieldUtils.readField(targetField, obj, true);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 反射获取变量
   * 
   * @Title: getTargetField
   * @param targetClass
   * @param fieldName
   * @return
   * @return: Field
   */
  public static Field getTargetField(Class<?> targetClass, String fieldName) {
    Field field = null;

    try {
      if (targetClass == null) {
        return field;
      }

      if (Object.class.equals(targetClass)) {
        return field;
      }
      // 反射获取变量
      field = FieldUtils.getDeclaredField(targetClass, fieldName, true);
      if (field == null) {
        field = getTargetField(targetClass.getSuperclass(), fieldName);
      }
    } catch (Exception e) {
    }

    return field;
  }

  /**
   * 反射给变量赋值
   * 
   * @Title: setFieldValue
   * @param obj
   * @param fieldName
   * @param value
   * @return: void
   */
  public static void setFieldValue(Object obj, String fieldName, Object value) {
    if (null == obj) {
      return;
    }
    Field targetField = getTargetField(obj.getClass(), fieldName);
    try {
      FieldUtils.writeField(targetField, obj, value);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
