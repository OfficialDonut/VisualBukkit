package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.metadata.Metadatable;

@Description("Sets a metadata value")
public class StatSetMetadata extends StatementBlock {

    public StatSetMetadata() {
        init("set metadata");
        initLine("target: ", Metadatable.class);
        initLine("key:    ", String.class);
        initLine("value:  ", Object.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setMetadata(" + arg(1) + ", new org.bukkit.metadata.FixedMetadataValue(PluginMain.getInstance()," + arg(2) + "));";
    }
}
