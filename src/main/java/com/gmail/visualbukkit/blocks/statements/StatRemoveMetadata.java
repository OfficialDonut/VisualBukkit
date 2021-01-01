package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.metadata.Metadatable;

@Description("Removes a metadata value")
public class StatRemoveMetadata extends StatementBlock {

    public StatRemoveMetadata() {
        init("remove metadata ", String.class, " from ", Metadatable.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".removeMetadata(" + arg(0) + ", PluginMain.getInstance());";
    }
}
