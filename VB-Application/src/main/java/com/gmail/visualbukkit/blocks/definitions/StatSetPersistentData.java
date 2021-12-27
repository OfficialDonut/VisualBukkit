package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatSetPersistentData extends Statement {

    public StatSetPersistentData() {
        super("stat-set-persistent-data", "Set Persistent Data", "Bukkit", "Sets a persistent data value");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Target", ClassInfo.of("org.bukkit.persistence.PersistentDataHolder")), new ExpressionParameter("Key", ClassInfo.STRING), new ExpressionParameter("Value", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return arg(0) + ".getPersistentDataContainer().set(" +
                        "new NamespacedKey(PluginMain.getInstance()," + arg(1) + "), " +
                        "org.bukkit.persistence.PersistentDataType.STRING," + arg(2) + ");";
            }
        };
    }
}
