package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatSetMetadata extends Statement {

    public StatSetMetadata() {
        super("stat-set-metadata", "Set Metadata", "Bukkit", "Sets a metadata value");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Target", ClassInfo.of("org.bukkit.metadata.Metadatable")), new ExpressionParameter("Key", ClassInfo.STRING), new ExpressionParameter("Value", ClassInfo.OBJECT)) {
            @Override
            public String toJava() {
                return arg(0) + ".setMetadata(" + arg(1) + ", new org.bukkit.metadata.FixedMetadataValue(PluginMain.getInstance()," + arg(2) + "));";
            }
        };
    }
}
