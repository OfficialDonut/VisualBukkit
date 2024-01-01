package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;

public class StatSetPlayerHeadSkin extends Statement {

    public StatSetPlayerHeadSkin() {
        super("stat-set-player-head-skin", "Set Player Head Skin", "ItemStack", "Sets the skin of a player head");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Head", ClassInfo.of("org.bukkit.inventory.ItemStack")), new ExpressionParameter("URL", ClassInfo.STRING)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.REFLECTION_UTIL);
                buildContext.addUtilMethod(SKIN_METHOD);
            }

            @Override
            public String toJava() {
                return "PluginMain.setPlayerHeadSkin(" + arg(0) + "," + arg(1) + ");";
            }
        };
    }

    private static final String SKIN_METHOD =
            """
            public static void setPlayerHeadSkin(org.bukkit.inventory.ItemStack item, String url) throws Exception {
                org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
                Object profile = ReflectionUtil.getDeclaredConstructor(ReflectionUtil.getClass("com.mojang.authlib.GameProfile"), UUID.class, String.class).newInstance(UUID.randomUUID(), "");
                Object property = ReflectionUtil.getDeclaredConstructor(ReflectionUtil.getClass("com.mojang.authlib.properties.Property"), String.class, String.class).newInstance("textures", Base64.getEncoder().encodeToString(String.format("{textures:{SKIN:{url:\\"%s\\"}}}", url).getBytes(java.nio.charset.StandardCharsets.UTF_8)));
                Object properties = ReflectionUtil.getDeclaredMethod(profile.getClass(), "getProperties").invoke(profile);
                ReflectionUtil.getDeclaredMethod(com.google.common.collect.ForwardingMultimap.class, "put", Object.class, Object.class).invoke(properties, "textures", property);
                java.lang.reflect.Field field = ReflectionUtil.getDeclaredField(itemMeta.getClass(), "profile");
                field.setAccessible(true);
                field.set(itemMeta, profile);
                item.setItemMeta(itemMeta);
            }
            """;
}
