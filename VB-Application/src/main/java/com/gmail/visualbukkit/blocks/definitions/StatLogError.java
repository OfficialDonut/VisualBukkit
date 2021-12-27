package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatLogError extends Statement {

    public StatLogError() {
        super("stat-log-error", "Log Error", "Bukkit", "Logs a SEVERE message");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Message", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "PluginMain.getInstance().getLogger().severe(" + arg(0) + ");";
            }
        };
    }
}
