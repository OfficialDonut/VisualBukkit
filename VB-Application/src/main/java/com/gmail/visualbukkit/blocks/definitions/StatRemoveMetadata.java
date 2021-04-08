package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatRemoveMetadata extends Statement {

    public StatRemoveMetadata() {
        super("stat-remove-metadata");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.metadata.Metadatable")), new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return arg(0) + ".removeMetadata(" + arg(1) + ", PluginMain.getInstance());";
            }
        };
    }
}
