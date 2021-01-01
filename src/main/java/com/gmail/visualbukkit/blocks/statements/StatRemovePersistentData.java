package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.persistence.PersistentDataHolder;

@Description("Removes a persistent data value")
public class StatRemovePersistentData extends StatementBlock {

    public StatRemovePersistentData() {
        init("remove persistent data ", String.class, " from ", PersistentDataHolder.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getPersistentDataContainer().remove(new NamespacedKey(PluginMain.getInstance()," + arg(0) + "));";
    }
}
