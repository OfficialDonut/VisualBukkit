package us.donut.visualbukkit.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class SimpleList extends ArrayList<Object> {

    public SimpleList() {}

    public SimpleList(Object obj) {
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                add(Array.get(obj, i));
            }
        } else if (obj instanceof Collection<?>) {
            addAll((Collection<?>) obj);
        } else {
            add(obj);
        }
    }
}
