package us.donut.visualbukkit.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleList extends ArrayList<Object> {

    public SimpleList() {}

    public SimpleList(Object obj) {
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                add(Array.get(obj, i));
            }
        } else if (obj instanceof Collection<?>) {
            ((Collection<?>) obj).forEach(o -> add(o instanceof Collection ? new SimpleList(o) : o));
        } else {
            add(obj);
        }
    }

    public byte[] toByteArray() {
        byte[] bytes = new byte[size()];
        List<Byte> byteList = toList(Byte.class);
        for (int i = 0; i < size(); i++) {
            bytes[i] = byteList.get(i);
        }
        return bytes;
    }

    public short[] toShortArray() {
        short[] shorts = new short[size()];
        List<Short> shortList = toList(Short.class);
        for (int i = 0; i < size(); i++) {
            shorts[i] = shortList.get(i);
        }
        return shorts;
    }

    public float[] toFloatArray() {
        float[] floats = new float[size()];
        List<Float> floatList = toList(Float.class);
        for (int i = 0; i < size(); i++) {
            floats[i] = floatList.get(i);
        }
        return floats;
    }

    public int[] toIntArray() {
        return toList(Integer.class).stream().mapToInt(Integer::intValue).toArray();
    }

    public long[] toLongArray() {
        return toList(Long.class).stream().mapToLong(Long::longValue).toArray();
    }

    public double[] toDoubleArray() {
        return toList(Double.class).stream().mapToDouble(Double::doubleValue).toArray();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> toList(Class<T> type) {
        return stream()
                .map(o -> {
                    Class<?> clazz = o.getClass();
                    if (type.isAssignableFrom(clazz)) {
                        return (T) o;
                    } else if (Number.class.isAssignableFrom(type) && Number.class.isAssignableFrom(clazz)) {
                        return (T) convertNumber((Number) o, type);
                    } else {
                        return null;
                    }
                }).collect(Collectors.toList());
    }

    private Number convertNumber(Number number, Class<?> to) {
        if (to == Number.class) {
            return number;
        }
        if (to == Byte.class) {
            return number.byteValue();
        }
        if (to == Short.class) {
            return number.shortValue();
        }
        if (to == Integer.class) {
            return number.intValue();
        }
        if (to == Long.class) {
            return number.longValue();
        }
        if (to == Float.class) {
            return number.floatValue();
        }
        if (to == Double.class) {
            return number.doubleValue();
        }
        return null;
    }
}
