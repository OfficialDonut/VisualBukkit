package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.persistence.PersistentDataHolder;

@Description("A persistent data value")
public class ExprPersistentDataValue extends ExpressionBlock<String> {

    public ExprPersistentDataValue() {
        init("value of persistent data ", String.class, " for ", PersistentDataHolder.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getPersistentDataContainer().get(new NamespacedKey(PluginMain.getInstance()," + arg(0) + "), org.bukkit.persistence.PersistentDataType.STRING)";
    }
}
