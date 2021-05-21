package com.gmail.visualbukkit.blocks;

import com.google.common.primitives.Primitives;

import java.util.*;

public class ClassInfo {

    private static Map<String, ClassInfo> cache = new HashMap<>();
    private static Map<String, String> classAliases = new HashMap<>();

    static {
        classAliases.put("boolean", "Boolean");
        classAliases.put("char", "String");
        classAliases.put("void", "Object");
        classAliases.put("java.io.File", "FilePath");
        classAliases.put("java.lang.CharSequence", "String");
        classAliases.put("java.time.LocalDateTime", "Date");
        classAliases.put("org.bukkit.configuration.ConfigurationSection", "Config");
    }

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
        displayClassName = classAliases.get(canonicalClassName);
        if (displayClassName == null) {
            if (isNumber()) {
                displayClassName = "Number";
            } else if (isArrayOrCollection() && !isList()) {
                displayClassName = LIST.displayClassName;
            } else {
                displayClassName = formatClassName(canonicalClassName);
            }
        }
    }

    private ClassInfo(String canonicalClassName) {
        this.canonicalClassName = canonicalClassName;
        displayClassName = classAliases.get(canonicalClassName);
        if (displayClassName == null) {
            if (isArrayOrCollection()) {
                displayClassName = LIST.displayClassName;
            } else {
                displayClassName = formatClassName(canonicalClassName);
            }
        }
    }

    private String formatClassName(String canonicalClassName) {
        for (int i = 0; i < canonicalClassName.length(); i++) {
            if (Character.isUpperCase(canonicalClassName.charAt(i))) {
                return canonicalClassName.substring(i);
            }
        }
        return canonicalClassName;
    }

    public String convertTo(ClassInfo to, String src) {
        if (equals(to) || (to.clazz == Object.class && !isPrimitive())) {
            return src;
        }
        if (clazz == void.class) {
            return "((" + to.canonicalClassName + ")" + (to.isPrimitive() ? "(Object) null" : "null") + ")";
        }
        if ((isPrimitiveNumber() && to.isPrimitiveNumber()) || (to.isPrimitive() && clazz == Primitives.wrap(to.clazz)) || (isPrimitive() && to.clazz == Primitives.wrap(clazz))) {
            return "((" + to.canonicalClassName + ")" + src + ")";
        }
        if (to.clazz == String.class || to.clazz == CharSequence.class) {
            return "String.valueOf(" + src + ")";
        }
        if (to.clazz == char.class) {
            return clazz == String.class ? (src + ".charAt(0)") : ("String.valueOf(" + src + ").charAt(0)");
        }
        if (isCollection() && to.isArray()) {
            return "((" + to.canonicalClassName + ")" + src + ".toArray(new " + to.canonicalClassName + "{}))";
        }
        if (isCollection() && (to.clazz == Set.class || to.clazz == HashSet.class)) {
            return "new HashSet(" + src + ")";
        }
        if (isPrimitiveNumber() && to.clazz == Number.class) {
            return convertTo(ClassInfo.of(Primitives.wrap(clazz)), src);
        }
        if (isPrimitiveNumber() && to.isNumber()) {
            return ClassInfo.of(Primitives.unwrap(to.clazz)).convertTo(to, "((" + Primitives.unwrap(to.clazz) + ")" + src + ")");
        }
        if (clazz != null && Number.class.isAssignableFrom(clazz) && to.isPrimitiveNumber()) {
            return src + "." + to.canonicalClassName + "Value()";
        }
        if (clazz == Object.class && to.isPrimitiveNumber()) {
            return "((Number)" + src + ")." + to.canonicalClassName + "Value()";
        }
        if (clazz == Object.class && to.isArray()) {
            return "((" + to.canonicalClassName + ") ((Collection)" + src + ").toArray(new " + to.canonicalClassName + "{}))";
        }
        return "((" + to.canonicalClassName + ") (Object)" + src + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == ClassInfo.class) {
            return canonicalClassName.equals(((ClassInfo) obj).canonicalClassName);
        }
        return false;
    }

    @Override
    public String toString() {
        return displayClassName;
    }

    public boolean isPrimitive() {
        return clazz != null && clazz.isPrimitive();
    }

    public boolean isPrimitiveNumber() {
        return isPrimitive() && clazz != boolean.class && clazz != char.class;
    }

    public boolean isNumber() {
        return isPrimitiveNumber() || isSubclassOf(Number.class);
    }

    public boolean isArray() {
        return (clazz != null && clazz.isArray()) || canonicalClassName.endsWith("[]");
    }

    public boolean isCollection() {
        return isSubclassOf(Collection.class);
    }

    public boolean isList() {
        return isSubclassOf(List.class);
    }

    public boolean isArrayOrCollection() {
        return isArray() || isCollection();
    }

    public boolean isSubclassOf(Class<?> superClass) {
        return clazz != null && superClass.isAssignableFrom(clazz);
    }

    public Class<?> getClassType() {
        return clazz;
    }

    public String getCanonicalClassName() {
        return canonicalClassName;
    }

    public String getDisplayClassName() {
        return displayClassName;
    }

    public static void addClassAlias(String canonicalClassName, String alias) {
        classAliases.put(canonicalClassName, alias);
    }

    public static ClassInfo of(Class<?> clazz) {
        return cache.computeIfAbsent(clazz.getCanonicalName(), k -> new ClassInfo(clazz));
    }

    public static ClassInfo of(String canonicalClassName) {
        return cache.computeIfAbsent(canonicalClassName, k -> {
            try {
                return new ClassInfo(Class.forName(canonicalClassName));
            } catch (ClassNotFoundException e) {
                return new ClassInfo(canonicalClassName);
            }
        });
    }
}
