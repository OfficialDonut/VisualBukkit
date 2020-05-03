package us.donut.visualbukkit.plugin.hooks.worldguard;

import javassist.CtClass;
import us.donut.visualbukkit.plugin.PluginBuilder;
import us.donut.visualbukkit.plugin.hooks.PluginHook;

import java.util.HashMap;
import java.util.Map;

public class WorldGuardHook implements PluginHook {

    @Override
    public void insertInto(CtClass mainClass) {}

    @Override
    public Map<Class<?>, CtClass> getCtClasses(String packageName) throws Exception {
        Map<Class<?>, CtClass> classes = new HashMap<>();
        classes.put(WorldGuardHandler.class, PluginBuilder.getCtClass(WorldGuardHandler.class, packageName));
        return classes;
    }
}
