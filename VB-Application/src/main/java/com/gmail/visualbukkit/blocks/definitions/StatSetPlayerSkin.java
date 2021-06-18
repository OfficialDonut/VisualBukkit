package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;

public class StatSetPlayerSkin extends Statement {

    public StatSetPlayerSkin() {
        super("stat-set-player-skin");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.entity.Player")), new ExpressionParameter(ClassInfo.STRING), new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public void update() {
                super.update();
                checkForEvent(ClassInfo.of("org.bukkit.event.player.PlayerLoginEvent"));
            }

            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.REFLECTION_UTIL);
                buildContext.addUtilMethod(SKIN_METHOD);
            }

            @Override
            public String toJava() {
                return "PluginMain.setPlayerSkin(" + arg(0) + "," + arg(1) + "," + arg(2) + ");";
            }
        };
    }

    private static final String SKIN_METHOD =
            "public static void setPlayerSkin(org.bukkit.entity.Player player, String value, String signature) throws Exception {\n" +
            "    Object entityPlayer = ReflectionUtil.getDeclaredMethod(player.getClass(), \"getHandle\").invoke(player);\n" +
            "    Object gameProfile = ReflectionUtil.getDeclaredMethod(entityPlayer.getClass().getSuperclass(), \"getProfile\").invoke(entityPlayer);\n" +
            "    Object propertyMap = ReflectionUtil.getDeclaredMethod(gameProfile.getClass(), \"getProperties\").invoke(gameProfile);\n" +
            "    Object property = ReflectionUtil.getDeclaredConstructor(ReflectionUtil.getClass(\"com.mojang.authlib.properties.Property\"), String.class, String.class, String.class).newInstance(\"textures\", value, signature);\n" +
            "    ReflectionUtil.getDeclaredMethod(com.google.common.collect.ForwardingMultimap.class, \"removeAll\", Object.class).invoke(propertyMap, \"textures\");\n" +
            "    ReflectionUtil.getDeclaredMethod(com.google.common.collect.ForwardingMultimap.class, \"put\", Object.class, Object.class).invoke(propertyMap, \"textures\", property);\n" +
            "}";
}
