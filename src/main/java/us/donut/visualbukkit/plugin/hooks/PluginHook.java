package us.donut.visualbukkit.plugin.hooks;

import javassist.CtClass;

import java.util.Collections;
import java.util.Map;

public interface PluginHook {

    void insertInto(CtClass mainClass) throws Exception;

    default Map<Class<?>, CtClass> getCtClasses(String packageName) throws Exception {
        return Collections.emptyMap();
    }
}
