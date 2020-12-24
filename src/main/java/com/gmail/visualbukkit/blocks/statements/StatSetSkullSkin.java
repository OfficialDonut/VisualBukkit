package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.bukkit.inventory.ItemStack;

@Description("Sets the skin of a skull item")
public class StatSetSkullSkin extends StatementBlock {

    public StatSetSkullSkin() {
        init("set skull skin");
        initLine("skull item:     ", ItemStack.class);
        initLine("skin value:     ", String.class);
        initLine("skin signature: ", String.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.REFLECTION_UTIL);
        context.addUtilMethods(SKIN_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.setSkullSkin(" + arg(0) + "," + arg(1) + "," + arg(2) + ");";
    }

    private static final String SKIN_METHOD =
            "public static void setSkullSkin(ItemStack item, String value, String signature) throws Exception {\n" +
            "    ItemMeta itemMeta = item.getItemMeta();\n" +
            "    if (itemMeta instanceof SkullMeta) {\n" +
            "        SkullMeta skullMeta = (SkullMeta) itemMeta;\n" +
            "        Object gameProfile = ReflectionUtil.getClass(\"com.mojang.authlib.GameProfile\").getConstructor(UUID.class, String.class).newInstance(UUID.randomUUID(), null);\n" +
            "        Object propertyMap = ReflectionUtil.getDeclaredMethod(gameProfile.getClass(), \"getProperties\").invoke(gameProfile);\n" +
            "        Object property = ReflectionUtil.getDeclaredConstructor(ReflectionUtil.getClass(\"com.mojang.authlib.properties.Property\"), String.class, String.class, String.class).newInstance(\"textures\", value, signature);\n" +
            "        ReflectionUtil.getDeclaredMethod(com.google.common.collect.ForwardingMultimap.class, \"removeAll\", Object.class).invoke(propertyMap, \"textures\");\n" +
            "        ReflectionUtil.getDeclaredMethod(com.google.common.collect.ForwardingMultimap.class, \"put\", Object.class, Object.class).invoke(propertyMap, \"textures\", property);\n" +
            "        ReflectionUtil.getDeclaredField(skullMeta.getClass(), \"profile\").set(skullMeta, gameProfile);\n" +
            "        item.setItemMeta(skullMeta);\n" +
            "    }\n" +
            "}";
}
