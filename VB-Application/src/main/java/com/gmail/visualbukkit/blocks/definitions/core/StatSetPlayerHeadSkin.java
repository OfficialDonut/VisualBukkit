package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "stat-set-player-head-skin", name = "Set Player Head Skin", description = "Sets the skin of a player head")
public class StatSetPlayerHeadSkin extends StatementBlock {

    public StatSetPlayerHeadSkin() {
        addParameter("Head", new ExpressionParameter(ClassInfo.of("org.bukkit.inventory.ItemStack")));
        addParameter("URL", new ExpressionParameter(ClassInfo.of(String.class)));
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/inventory/meta/SkullMeta.html#setOwner(java.lang.String)"));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        if (buildInfo.getMetadata().putIfAbsent("setPlayerHeadSkin()", true) == null) {
            buildInfo.getMainClass().addMethod(
                    """
                    public static void setPlayerHeadSkin(org.bukkit.inventory.ItemStack item, String url) throws Exception {
                        org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
                        Object profile = Class.forName("com.mojang.authlib.GameProfile").getDeclaredConstructor(UUID.class, String.class).newInstance(UUID.randomUUID(), "");
                        Object property = Class.forName("com.mojang.authlib.properties.Property").getDeclaredConstructor(String.class, String.class).newInstance("textures", Base64.getEncoder().encodeToString(String.format("{textures:{SKIN:{url:\\"%s\\"}}}", url).getBytes(java.nio.charset.StandardCharsets.UTF_8)));
                        Object properties = profile.getClass().getDeclaredMethod("getProperties").invoke(profile);
                        com.google.common.collect.ForwardingMultimap.class.getDeclaredMethod("put", Object.class, Object.class).invoke(properties, "textures", property);
                        java.lang.reflect.Field field = itemMeta.getClass().getDeclaredField("profile");
                        field.setAccessible(true);
                        field.set(itemMeta, profile);
                        item.setItemMeta(itemMeta);
                    }
                    """);
        }
        return "PluginMain.setPlayerHeadSkin(" + arg(0, buildInfo) + "," + arg(1, buildInfo) + ");";
    }
}
