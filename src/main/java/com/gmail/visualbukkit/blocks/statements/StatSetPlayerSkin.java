package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

@Category(Category.PLAYER)
@Description("Sets the skin of a player (must be used in PlayerLoginEvent)")
public class StatSetPlayerSkin extends StatementBlock {

    public StatSetPlayerSkin() {
        init("set player skin");
        initLine("player:         ", Player.class);
        initLine("skin value:     ", String.class);
        initLine("skin signature: ", String.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set player skin must be used in a PlayerLoginEvent", PlayerLoginEvent.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.REFLECTION_UTIL);
        context.addUtilMethods(SKIN_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.setPlayerSkin(" + arg(0) + "," + arg(1) + "," + arg(2) + ");";
    }

    private static final String SKIN_METHOD =
            "public static void setPlayerSkin(Player player, String value, String signature) throws Exception {\n" +
            "    Object entityPlayer = ReflectionUtil.getDeclaredMethod(player.getClass(), \"getHandle\").invoke(player);\n" +
            "    Object gameProfile = ReflectionUtil.getDeclaredMethod(ReflectionUtil.getNmsClass(\"EntityHuman\"), \"getProfile\").invoke(entityPlayer);\n" +
            "    Object propertyMap = ReflectionUtil.getDeclaredMethod(gameProfile.getClass(), \"getProperties\").invoke(gameProfile);\n" +
            "    Object property = ReflectionUtil.getDeclaredConstructor(ReflectionUtil.getClass(\"com.mojang.authlib.properties.Property\"), String.class, String.class, String.class).newInstance(\"textures\", value, signature);\n" +
            "    ReflectionUtil.getDeclaredMethod(com.google.common.collect.ForwardingMultimap.class, \"removeAll\", Object.class).invoke(propertyMap, \"textures\");\n" +
            "    ReflectionUtil.getDeclaredMethod(com.google.common.collect.ForwardingMultimap.class, \"put\", Object.class, Object.class).invoke(propertyMap, \"textures\", property);\n" +
            "}";
}
