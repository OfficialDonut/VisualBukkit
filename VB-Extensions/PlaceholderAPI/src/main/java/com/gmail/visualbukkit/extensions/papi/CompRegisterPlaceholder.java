package com.gmail.visualbukkit.extensions.papi;

import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public class CompRegisterPlaceholder extends PluginComponent {

    public CompRegisterPlaceholder() {
        super("comp-register-placeholder");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter()) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(REGISTER_MODULE);
                MethodSource<JavaClassSource> registerMethod = buildContext.getMainClass().getMethod("registerPlaceholders");
                registerMethod.setBody(registerMethod.getBody() +
                        "papiHook.registerPlaceholder(" + arg(0) + ", placeholderPlayer -> {" +
                        "try {" +
                        getChildJava() +
                        "} catch (Exception e) { e.printStackTrace(); }" +
                        "return null;" +
                        "});");
            }
        };
    }

    private static final PluginModule REGISTER_MODULE = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext buildContext) {
            buildContext.addUtilClass(Roaster.parse(JavaClassSource.class, PapiExtension.EXPANSION_CLASS));
            buildContext.addUtilClass(Roaster.parse(JavaClassSource.class, PapiExtension.HOOK_CLASS));
            buildContext.addMavenRepository(
                    "<id>placeholderapi</id>\n" +
                    "<url>https://repo.extendedclip.com/content/repositories/placeholderapi/</url>");
            buildContext.addMavenDependency(
                    "<groupId>me.clip</groupId>\n" +
                    "<artifactId>placeholderapi</artifactId>\n" +
                    "<version>2.10.9</version>\n" +
                    "<scope>provided</scope>");
            buildContext.getMainClass().addMethod(
                    "public static void registerPlaceholders() {" +
                    "PapiHook papiHook = new PapiHook();" +
                    "}");
            MethodSource<JavaClassSource> enableMethod = buildContext.getMainClass().getMethod("onEnable");
            enableMethod.setBody(enableMethod.getBody() +
                    "if (Bukkit.getPluginManager().getPlugin(\"PlaceholderAPI\") != null) {" +
                    "PluginMain.registerPlaceholders();" +
                    "}");
        }
    };
}
