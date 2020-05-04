package us.donut.visualbukkit.plugin.hooks;

import javassist.CtClass;

public interface PluginHook {

    void insertInto(CtClass mainClass) throws Exception;

    default Class<?>[] getClasses() {
        return new Class<?>[0];
    }
}
