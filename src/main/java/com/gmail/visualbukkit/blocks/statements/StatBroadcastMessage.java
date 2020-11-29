package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.components.ExpressionParameter;

@Description("Broadcasts a message to the server")
public class StatBroadcastMessage extends StatementBlock {

    private ExpressionParameter string = new ExpressionParameter(String.class);

    public StatBroadcastMessage() {
        init("broadcast ", string);
    }

    @Override
    public String toJava() {
        return "Bukkit.broadcastMessage(" + string + ");";
    }
}
