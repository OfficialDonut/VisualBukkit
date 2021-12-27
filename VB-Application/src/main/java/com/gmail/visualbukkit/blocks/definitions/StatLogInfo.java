package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatLogInfo extends Statement {

    public StatLogInfo() {
        super("stat-log-info", "Log Info", "Bukkit", "Logs an INFO message");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Message", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "PluginMain.getInstance().getLogger().info(" + arg(0) + ");";
            }
        };
    }
}
