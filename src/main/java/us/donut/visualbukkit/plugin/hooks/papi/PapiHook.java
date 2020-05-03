package us.donut.visualbukkit.plugin.hooks.papi;

import javassist.CtClass;
import javassist.CtMethod;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import us.donut.visualbukkit.plugin.hooks.PluginHook;

public class PapiHook implements PluginHook {

    private Class<?>[] classes = {ExpansionHandler.class, PapiExpansion.class, PlaceholderExpansion.class};

    @Override
    public void insertInto(CtClass mainClass) throws Exception {
        CtMethod enableMethod = mainClass.getDeclaredMethod("onEnable");
        enableMethod.insertAfter(
                "if(Bukkit.getPluginManager().getPlugin(\"PlaceholderAPI\") != null) {" +
                "us.donut.visualbukkit.plugin.hooks.papi.ExpansionHandler.register(PluginMain.getInstance());" +
                "}");
    }

    @Override
    public Class<?>[] getClasses() {
        return classes;
    }
}
