package us.donut.visualbukkit.plugin.hooks;

import javassist.CtClass;

public interface PluginHook {

    void insertInto(CtClass mainClass) throws Exception;
}
