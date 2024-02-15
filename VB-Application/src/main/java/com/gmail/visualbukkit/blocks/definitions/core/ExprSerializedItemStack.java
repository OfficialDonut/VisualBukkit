package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.MultilineStringParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-serialized-itemstack", name = "Serialized ItemStack", description = "Creates an ItemStack from YAML")
public class ExprSerializedItemStack extends ExpressionBlock {

    private final MultilineStringParameter parameter = new MultilineStringParameter();

    public ExprSerializedItemStack() {
        addParameter("Yaml", parameter);
    }

    public ExprSerializedItemStack(String yaml) {
        this();
        parameter.setText(yaml);
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.inventory.ItemStack");
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(new StringReader(" + arg(0, buildInfo) + ")).getItemStack(\"item\")";
    }
}
