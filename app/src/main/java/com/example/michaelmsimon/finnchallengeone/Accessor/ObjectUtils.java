package com.example.michaelmsimon.finnchallengeone.Accessor;

/**
 * Created by Michael M. Simon on 2/16/2018.
 */
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ObjectUtils {


        private ObjectUtils() {}

        public static Map<String, Object> getFieldNamesAndValues(final Object obj, boolean publicOnly)
                throws IllegalArgumentException,IllegalAccessException
        {
            Class<? extends Object> c1 = obj.getClass();
            Map<String, Object> map = new HashMap<String, Object>();
            Field[] fields = c1.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                String name = fields[i].getName();
                if (publicOnly) {
                    if(Modifier.isPublic(fields[i].getModifiers())) {
                        Object value = fields[i].get(obj);
                        map.put(name, value);
                    }
                }
                else {
                    fields[i].setAccessible(true);
                    Object value = fields[i].get(obj);
                    map.put(name, value);
                }
            }
            return map;





}}
