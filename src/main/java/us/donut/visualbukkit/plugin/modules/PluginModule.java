package us.donut.visualbukkit.plugin.modules;

import com.google.common.collect.ObjectArrays;
import javassist.CtClass;
import javassist.CtMethod;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import us.donut.visualbukkit.plugin.modules.classes.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum PluginModule {

    DATABASE(ObjectArrays.concat(DatabaseManager.class, getClasses("com.zaxxer.hikari"))),
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

    private static Class<?>[] getClasses(String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        for (Class<?> clazz : new Reflections(packageName, new SubTypesScanner(false)).getSubTypesOf(Object.class)) {
            classes.add(clazz);
            try {
                classes.addAll(Arrays.asList(clazz.getDeclaredClasses()));
            } catch (NoClassDefFoundError ignored) {}
        }
        return classes.toArray(new Class<?>[0]);
    }

    private Class<?>[] classes;

    PluginModule(Class<?>... classes) {
        this.classes = classes;
    }

    public void insertInto(CtClass mainClass) throws Exception {}

    public Class<?>[] getClasses() {
        return classes;
    }
}
