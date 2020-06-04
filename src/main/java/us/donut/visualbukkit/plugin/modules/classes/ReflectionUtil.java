package us.donut.visualbukkit.plugin.modules.classes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtil {

    private static Map<String, Method> methodCache = new HashMap<>();
    private static Map<String, Field> fieldCache = new HashMap<>();

    public static Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        String signature = clazz.getCanonicalName() + name + Arrays.toString(parameterTypes);
        return methodCache.computeIfAbsent(signature, k -> {
            try {
                return clazz.getDeclaredMethod(name, parameterTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public static Field getDeclaredField(Class<?> clazz, String name) {
        String signature = clazz.getCanonicalName() + name;
        return fieldCache.computeIfAbsent(signature, k -> {
            try {
                return clazz.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return null;
            }
        });
    }
}
