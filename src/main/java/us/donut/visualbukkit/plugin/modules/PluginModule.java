package us.donut.visualbukkit.plugin.modules;

import javassist.CtClass;
import javassist.CtMethod;
import us.donut.visualbukkit.plugin.modules.classes.*;

public enum PluginModule {

    PROCEDURE_RUNNABLE(ProcedureRunnable.class),
    REFLECTION_UTIL(ReflectionUtil.class),
    WORLDGUARD(WorldGuardHook.class),
    VAULT(VaultHook.class),

    PlACEHOLDERAPI(ExpansionHandler.class, PapiExpansion.class, PlaceholderEvent.class) {
        @Override
        public void insertInto(CtClass mainClass) throws Exception {
            CtMethod enableMethod = mainClass.getDeclaredMethod("onEnable");
            enableMethod.insertAfter(
                    "if(Bukkit.getPluginManager().getPlugin(\"PlaceholderAPI\") != null) {" +
                    "us.donut.visualbukkit.plugin.modules.classes.ExpansionHandler.register(PluginMain.getInstance());" +
                    "}");
        }
    };

    private Class<?>[] classes;

    PluginModule(Class<?>... classes) {
        this.classes = classes;
    }

    public void insertInto(CtClass mainClass) throws Exception {}

    public Class<?>[] getClasses() {
        return classes;
    }
}
