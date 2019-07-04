package com.qjw.util;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author : qjw
 * @data : 2019/6/11
 */
public class JsonUtil {

    public static void main(String[] args) {
        String test = "{a:1,b:2,c:3,d:{q:4,w:5,e:6,y:{o:7,p:8}}}";
        HashMap map = jsonToFlatMap(test);
//        Map<String, Object> map = jsonToNestMap(test);
        System.out.println(map);
    }

    /**
     * 递归解析json，返回嵌套map
     * @param json
     * @return
     */
    public static Map<String,Object> jsonToNestMap(String json) {
        Map<String,Object> map = new HashMap<>();

        // 如果不包含：则表示解析完成
        if (!json.contains(":")) {
            return map;
        }

        // 底层是一个嵌套的map
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.toMap();
    }

    /**
     * 递归解析json，返回平map
     * @param json
     * @return
     */
    public static HashMap jsonToFlatMap(String json) {
        HashMap map = new HashMap();

        iterateParse(json, map);

        return map;
    }

    /**
     * 递归解析json，铺平到map
     * @param json
     * @param map
     * @return
     */
    private static boolean iterateParse(String json, HashMap map) {
        // 1.如果不包含：则表示解析完成
        if (!json.contains(":")) {
            return true;
        }

        // 2.底层是一个嵌套的map
        JSONObject jsonObject = new JSONObject(json);
        Iterator<String> keys = jsonObject.keys();

        // 3.遍历map的key
        while (keys.hasNext()){
            String key = keys.next();
            Object value = jsonObject.get(key);

            // 3.1 如果当前value解析完成，则放入map
            if (iterateParse(value.toString(),map)){
                map.put(key,value);
            }
        }

        // 4.返回解析未完成，保证调用者不重复put到map
        return false;
    }


    /**
     * 把map转为Obj
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null){
            return null;
        }

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 静态变量不设置
            int modifiers = field.getModifiers();
            if(Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)){
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    /**
     * 把obj转为map
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null){
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }

}
