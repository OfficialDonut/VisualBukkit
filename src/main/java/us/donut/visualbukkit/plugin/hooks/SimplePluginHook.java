package us.donut.visualbukkit.plugin.hooks;

import javassist.CtClass;

public class SimplePluginHook implements PluginHook {

    private Class<?>[] classes;

    public SimplePluginHook(Class<?>... classes) {
        this.classes = classes;
    }

    @Override
    public void insertInto(CtClass mainClass) {}

    @Override
    public Class<?>[] getClasses() {
        return classes;
    }
}
