package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatLogWarning extends Statement {

    public StatLogWarning() {
        super("stat-log-warning", "Log Warning", "Bukkit", "Logs a WARNING message");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Message", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "PluginMain.getInstance().getLogger().warning(" + arg(0) + ");";
            }
        };
    }
}
