package com.wckj.chasstage.common.util;

import com.wckj.framework.core.utils.StringUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * MAP 相关工具类
 */
public final class MapCollectionUtil {

    private MapCollectionUtil() {
    }

    /**
     * 根据request 请求获取所有请求参数并转换为Map<key,obj>
     *
     * @param request
     * @return
     */
    public static Map<String, Object> conversionRequestToMap(HttpServletRequest request) {
        Map<String, String[]> stringMap = request.getParameterMap();

        Map<String, Object> result = new HashMap<>();
        for (String key : stringMap.keySet()) {
            result.put(key, stringMap.get(key)[0]);
        }
        return result;
    }

    public static Map<String, Object> conversionRequestToMap2(HttpServletRequest request) {
        Map<String, String[]> stringMap = request.getParameterMap();

        Map<String, Object> result = new HashMap<>();
        for (String key : stringMap.keySet()) {
            if (stringMap.get(key)[0] != null && StringUtils.isNotEmpty(stringMap.get(key)[0])) {
                result.put(key, stringMap.get(key)[0]);
            }
        }
        return result;
    }

    /**
     * 填充数据到实体，根据Map  key -- value
     *
     * @param map
     * @param class_
     * @return
     */
    public static Object fillingMapDataToEntity(Map<String, Object> map, Class class_) {
        Object object = null;
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            object = class_.newInstance();
            for (String key : map.keySet()) {
                Object value = map.get(key);
                Field f = class_.getDeclaredField(key);
                f.setAccessible(true);
                if (f.getType().toString().endsWith("Date")) {
                    Date val = sim.parse((String) value);
                    f.set(object, val);
                } else {
                    f.set(object, value);
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return object;
    }


    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }

        return map;
    }

    public static Map<String, Object> parseObj2Map(Object args) {
        return Arrays.stream(BeanUtils.getPropertyDescriptors(args.getClass()))
                .filter(pd -> !"class".equals(pd.getName()))
                .collect(HashMap::new,
                        (map, pd) -> map.put(pd.getName(), ReflectionUtils.invokeMethod(pd.getReadMethod(), args)),
                        HashMap::putAll);
    }


    //将javabean转为map类型，然后返回一个map类型的值
    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!StringUtils.equals(name, "class")) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

}
