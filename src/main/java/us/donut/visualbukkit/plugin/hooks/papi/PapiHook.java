package us.donut.visualbukkit.plugin.hooks.papi;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import us.donut.visualbukkit.plugin.hooks.PluginHook;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PapiHook implements PluginHook {

    private CtClass expansionCtClass;
    private CtClass handlerCtClass;

    @Override
    public void insertInto(CtClass mainClass) throws Exception {
        expansionCtClass = ClassPool.getDefault().getAndRename(VisualBukkitExpansion.class.getCanonicalName(), "visualbukkit.a" + UUID.randomUUID().toString().replace("-", ""));
        handlerCtClass = ClassPool.getDefault().getAndRename(ExpansionHandler.class.getCanonicalName(), "visualbukkit.a" + UUID.randomUUID().toString().replace("-", ""));
        CtMethod registerMethod = handlerCtClass.getDeclaredMethod("register");
        registerMethod.insertBefore("new " + expansionCtClass.getName() + "(plugin).register();");
        CtMethod enableMethod = mainClass.getDeclaredMethod("onEnable");
        enableMethod.insertAfter(
                "if(Bukkit.getPluginManager().getPlugin(\"PlaceholderAPI\") != null) {" +
                handlerCtClass.getName() + ".register(this);" +
                "}");
    }

    @Override
    public List<CtClass> getClasses() throws Exception {
        return Arrays.asList(ClassPool.getDefault().get(PlaceholderEvent.class.getCanonicalName()), expansionCtClass, handlerCtClass);
    }

}
