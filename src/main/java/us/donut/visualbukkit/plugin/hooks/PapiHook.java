package us.donut.visualbukkit.plugin.hooks;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import us.donut.visualbukkit.editor.ProjectManager;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PapiHook implements PluginHook {

    private CtClass expansionCtClass;

    @Override
    public void insertInto(CtClass mainClass) throws Exception {
        String name = VisualBukkitExpansion.class.getCanonicalName();
        expansionCtClass = ClassPool.getDefault().getAndRename(name, name.replace("VisualBukkitExpansion", "a" + UUID.randomUUID().toString().replace("-", "")));
        CtMethod enableMethod = mainClass.getDeclaredMethod("onEnable");
        enableMethod.insertAfter(
                "if(Bukkit.getPluginManager().getPlugin(\"PlaceholderAPI\") != null) {" +
                "new " + expansionCtClass.getName() + "(this).register();" +
                "}");
    }

    @Override
    public List<CtClass> getClasses() throws Exception {
        CtMethod identifierMethod = expansionCtClass.getDeclaredMethod("getIdentifier");
        identifierMethod.insertBefore("return \"" + ProjectManager.getCurrentProject().getPluginName().toUpperCase() + "\";");
        return Arrays.asList(ClassPool.getDefault().get(PlaceholderEvent.class.getCanonicalName()), expansionCtClass);
    }

}
