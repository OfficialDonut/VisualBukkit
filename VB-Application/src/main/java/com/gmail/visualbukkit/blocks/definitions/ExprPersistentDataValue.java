package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprPersistentDataValue extends Expression {

    public ExprPersistentDataValue() {
        super("expr-persistent-data-value", "Persistent Data Value", "Bukkit", "A persistent data value");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Target", ClassInfo.of("org.bukkit.persistence.PersistentDataHolder")), new ExpressionParameter("Key", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return arg(0) + ".getPersistentDataContainer().get(new NamespacedKey(PluginMain.getInstance()," + arg(1) + "), org.bukkit.persistence.PersistentDataType.STRING)";
            }
        };
    }
}
