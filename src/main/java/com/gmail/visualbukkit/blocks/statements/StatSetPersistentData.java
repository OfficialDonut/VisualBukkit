package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.persistence.PersistentDataHolder;

@Description("Sets a persistent data value")
public class StatSetPersistentData extends StatementBlock {

    public StatSetPersistentData() {
        init("set persistent data");
        initLine("target: ", PersistentDataHolder.class);
        initLine("key:    ", String.class);
        initLine("value:  ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPersistentDataContainer().set(" +
                "new NamespacedKey(PluginMain.getInstance()," + arg(1) + "), " +
                "org.bukkit.persistence.PersistentDataType.STRING," + arg(2) + ");";
    }
}
