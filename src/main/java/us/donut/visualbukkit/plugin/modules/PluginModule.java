package us.donut.visualbukkit.plugin.modules;

import com.google.common.collect.ObjectArrays;
import org.bstats.bukkit.Metrics;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import us.donut.visualbukkit.plugin.modules.classes.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum PluginModule {

    BSTATS(Metrics.class),
    DATABASE(ObjectArrays.concat(DatabaseManager.class, getClasses("com.zaxxer.hikari"))),
    REFLECTION_UTIL(ReflectionUtil.class),
    WORLDGUARD(WorldGuardHook.class),
    VAULT(VaultHook.class),

    PlACEHOLDERAPI(ExpansionHandler.class, PapiExpansion.class, PlaceholderEvent.class) {
        @Override
        public void insertInto(JavaClassSource mainClass) {
            MethodSource<JavaClassSource> enableMethod = mainClass.getMethod("onEnable");
            enableMethod.setBody(enableMethod.getBody() +
                    "if (Bukkit.getPluginManager().getPlugin(\"PlaceholderAPI\") != null) {" +
                    "ExpansionHandler.register(this);" +
                    "}");
        }
    },

    VARIABLES(VariableManager.class) {
        @Override
        public void insertInto(JavaClassSource mainClass) {
            MethodSource<JavaClassSource> enableMethod = mainClass.getMethod("onEnable");
            enableMethod.setBody("VariableManager.loadVariables(this);" + enableMethod.getBody());
            MethodSource<JavaClassSource> disableMethod = mainClass.getMethod("onDisable");
            disableMethod.setBody(disableMethod.getBody() + "VariableManager.saveVariables();");
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

    public void insertInto(JavaClassSource mainClass) {}

    public Class<?>[] getClasses() {
        return classes;
    }
}
