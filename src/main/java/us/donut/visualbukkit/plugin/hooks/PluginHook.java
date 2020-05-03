package us.donut.visualbukkit.plugin.hooks;

import javassist.CtClass;

import java.util.Collections;
import java.util.List;

public interface PluginHook {

    void insertInto(CtClass mainClass) throws Exception;

    default List<CtClass> getClasses() throws Exception {
        return Collections.emptyList();
    }
}
