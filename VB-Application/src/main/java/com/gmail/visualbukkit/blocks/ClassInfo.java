package com.gmail.visualbukkit.blocks;

import org.apache.commons.lang3.ClassUtils;

import java.util.*;

public class ClassInfo {

    private static Map<String, ClassInfo> classInfoCache = new HashMap<>();

    public static final ClassInfo OBJECT = ClassInfo.of(Object.class);
    public static final ClassInfo STRING = ClassInfo.of(String.class);
    public static final ClassInfo LIST = ClassInfo.of(List.class);
    public static final ClassInfo VOID = ClassInfo.of(void.class);
    public static final ClassInfo BYTE = ClassInfo.of(byte.class);
    public static final ClassInfo SHORT = ClassInfo.of(short.class);
    public static final ClassInfo INT = ClassInfo.of(int.class);
    public static final ClassInfo LONG = ClassInfo.of(long.class);
    public static final ClassInfo FLOAT = ClassInfo.of(float.class);
    public static final ClassInfo DOUBLE = ClassInfo.of(double.class);
    public static final ClassInfo BOOLEAN = ClassInfo.of(boolean.class);
    public static final ClassInfo CHAR = ClassInfo.of(char.class);

    private Class<?> clazz;
    private String canonicalClassName;
    private String displayClassName;

    private ClassInfo(Class<?> clazz) {
        this.clazz = clazz;
        canonicalClassName = clazz.getCanonicalName();
        displayClassName = TypeHandler.getDisplayClassName(clazz.getName());
        if (displayClassName == null) {
            if (isNumber()) {
                displayClassName = TypeHandler.getDisplayClassName("number");
            } else if (isArrayOrCollection() && clazz != List.class) {
                displayClassName = LIST.displayClassName;
            } else {
                displayClassName = ClassUtils.getShortClassName(clazz);
            }
        }
    }

    private ClassInfo(String className) {
        canonicalClassName = className.contains("$") ? className.replace("$", ".") : className;
        canonicalClassName = canonicalClassName.startsWith("[L") ? canonicalClassName.substring(2, canonicalClassName.length() - 1) + "[]" : canonicalClassName;
        displayClassName = TypeHandler.getDisplayClassName(className);
        if (displayClassName == null) {
            if (isArrayOrCollection()) {
                displayClassName = LIST.displayClassName;
            } else {
                displayClassName = ClassUtils.getShortClassName(className);
            }
        }
    }

    public String convert(String src, ClassInfo classInfo) {
        if (equals(classInfo)) {
            return src;
        }
        if (clazz == void.class) {
            return "((" + classInfo.canonicalClassName + ")" + (classInfo.isPrimitive() ? "(Object) null" : "null") + ")";
        }
        if ((isPrimitiveNumber() && classInfo.isPrimitiveNumber()) || (classInfo.isPrimitive() && clazz == ClassUtils.primitiveToWrapper(classInfo.clazz)) || (isPrimitive() && classInfo.clazz == ClassUtils.primitiveToWrapper(clazz))) {
            return "((" + classInfo.canonicalClassName + ")" + src + ")";
        }
        if (classInfo.clazz == String.class) {
            return "String.valueOf(" + src + ")";
        }
        if (clazz == String.class && classInfo.clazz == char.class) {
            return src + ".charAt(0)";
        }
        if (clazz != null && Collection.class.isAssignableFrom(clazz) && classInfo.isArray()) {
            return "((" + classInfo.canonicalClassName + ")" + src + ".toArray(new " + classInfo.canonicalClassName + "{}))";
        }
        if (isPrimitiveNumber() && classInfo.clazz == Number.class) {
            return convert(src, ClassInfo.of(ClassUtils.primitiveToWrapper(clazz)));
        }
        if (isPrimitiveNumber() && classInfo.isNumber()) {
            return convert("((" + ClassUtils.wrapperToPrimitive(classInfo.clazz) + ")" + src, classInfo);
        }
        if (clazz != null && Number.class.isAssignableFrom(clazz) && classInfo.isPrimitiveNumber()) {
            return src + "." + classInfo.canonicalClassName + "Value()";
        }
        if (clazz == Object.class && classInfo.isPrimitiveNumber()) {
            return "((Number)" + src + ")." + classInfo.canonicalClassName + "Value()";
        }
        if (clazz == Object.class && classInfo.isArray()) {
            return "((" + classInfo.canonicalClassName + ") ((List)" + src + ").toArray(new " + classInfo.canonicalClassName + "{}))";
        }
        return "((" + classInfo.canonicalClassName + ") (Object)" + src + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == ClassInfo.class) {
            return canonicalClassName.equals(((ClassInfo) obj).canonicalClassName);
        }
        return false;
    }

    public boolean isPrimitive() {
        return clazz != null && clazz.isPrimitive();
    }

    public boolean isPrimitiveNumber() {
        return isPrimitive() && clazz != boolean.class && clazz != char.class;
    }

    public boolean isNumber() {
        return isPrimitiveNumber() || (clazz != null && Number.class.isAssignableFrom(clazz));
    }

    public boolean isArray() {
        return (clazz != null && clazz.isArray()) || canonicalClassName.endsWith("[]");
    }

    public boolean isArrayOrCollection() {
        return isArray() || (clazz != null && Collection.class.isAssignableFrom(clazz));
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getCanonicalClassName() {
        return canonicalClassName;
    }

    public String getDisplayClassName() {
        return displayClassName;
    }

    public static ClassInfo of(Class<?> clazz) {
        return classInfoCache.computeIfAbsent(clazz.getName(), k -> new ClassInfo(clazz));
    }

    public static ClassInfo of(String className) {
        return classInfoCache.computeIfAbsent(className, k -> {
            try {
                return new ClassInfo(Class.forName(className));
            } catch (ClassNotFoundException e) {
                return new ClassInfo(className);
            }
        });
    }
}
