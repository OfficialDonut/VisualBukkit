package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "stat-set-player-skin", name = "Set Player Skin", description = "Sets the skin of a player")
public class StatSetPlayerSkin extends StatementBlock {

    public StatSetPlayerSkin() {
        addParameter("Player", new ExpressionParameter(ClassInfo.of("org.bukkit.entity.Player")));
        addParameter("Skin Value", new ExpressionParameter(ClassInfo.of(String.class)));
        addParameter("Skin Signature", new ExpressionParameter(ClassInfo.of(String.class)));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        if (buildInfo.getMetadata().putIfAbsent("setPlayerSkin()", true) == null) {
            buildInfo.getMainClass().addMethod(
                    """
                    public static void setPlayerSkin(org.bukkit.entity.Player player, String value, String signature) throws Exception {
                         Object entityPlayer = player.getClass().getDeclaredMethod("getHandle").invoke(player);
                         Object gameProfile = entityPlayer.getClass().getSuperclass().getDeclaredMethod("getProfile").invoke(entityPlayer);
                         Object propertyMap = gameProfile.getClass().getDeclaredMethod("getProperties").invoke(gameProfile);
                         Object property = Class.forName("com.mojang.authlib.properties.Property").getDeclaredConstructor(String.class, String.class, String.class).newInstance("textures", value, signature);
                         com.google.common.collect.ForwardingMultimap.class.getDeclaredMethod("removeAll", Object.class).invoke(propertyMap, "textures");
                         com.google.common.collect.ForwardingMultimap.class.getDeclaredMethod("put", Object.class, Object.class).invoke(propertyMap, "textures", property);
                     }
                    """);
        }
        return "PluginMain.setPlayerSkin(" + arg(0, buildInfo) + "," + arg(1, buildInfo) + "," + arg(2, buildInfo) + ");";
    }
}
