package com.gmail.visualbukkit.stdlib;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtil {

    private static String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private static Map<String, Class<?>> classCache = new HashMap<>();
    private static Map<String, Constructor<?>> constructorCache = new HashMap<>();
    private static Map<String, Method> methodCache = new HashMap<>();
    private static Map<String, Field> fieldCache = new HashMap<>();

    public static Class<?> getNmsClass(String simpleClassName) {
        return getClass("net.minecraft.server." + version + "." + simpleClassName);
    }

    public static Class<?> getClass(String className) {
        return classCache.computeIfAbsent(className, k -> {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public static Constructor<?> getDeclaredConstructor(Class<?> clazz, Class<?>... parameterTypes) {
        String signature = clazz.getCanonicalName() + Arrays.toString(parameterTypes);
        return constructorCache.computeIfAbsent(signature, k -> {
            try {
                return clazz.getDeclaredConstructor(parameterTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

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
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return null;
            }
        });
    }
}
