package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;

@Description("Sets the value of a game rule in a world")
public class StatSetGameRuleValue extends StatementBlock {

    public StatSetGameRuleValue() {
        init("set game rule value");
        initLine("world: ", World.class);
        initLine("rule : ", String.class);
        initLine("value: ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setGameRuleValue(" + arg(1) + "," + arg(2) + ");";
    }
}
