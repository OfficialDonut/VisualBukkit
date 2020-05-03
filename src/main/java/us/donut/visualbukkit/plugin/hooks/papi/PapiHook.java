package us.donut.visualbukkit.plugin.hooks.papi;

import javassist.CtClass;
import javassist.CtMethod;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import us.donut.visualbukkit.plugin.PluginBuilder;
import us.donut.visualbukkit.plugin.hooks.PluginHook;

import java.util.HashMap;
import java.util.Map;

public class PapiHook implements PluginHook {

    @Override
    public void insertInto(CtClass mainClass) throws Exception {
        CtMethod enableMethod = mainClass.getDeclaredMethod("onEnable");
        enableMethod.insertAfter(
                "if(Bukkit.getPluginManager().getPlugin(\"PlaceholderAPI\") != null) {" +
                "us.donut.visualbukkit.plugin.hooks.papi.ExpansionHandler.register(PluginMain.getInstance());" +
                "}");
    }

    @Override
    public Map<Class<?>, CtClass> getCtClasses(String packageName) throws Exception {
        Map<Class<?>, CtClass> classes = new HashMap<>();
        classes.put(ExpansionHandler.class, PluginBuilder.getCtClass(ExpansionHandler.class, packageName));
        classes.put(PapiExpansion.class, PluginBuilder.getCtClass(PapiExpansion.class, packageName));
        classes.put(PlaceholderEvent.class, PluginBuilder.getCtClass(PlaceholderExpansion.class, packageName));
        return classes;
    }
}
