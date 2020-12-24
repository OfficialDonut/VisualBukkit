package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import org.bukkit.util.Vector;

@Description("Sets a component of a vector")
public class StatSetVectorComponent extends StatementBlock {

    public StatSetVectorComponent() {
        init("set ", new ChoiceParameter("x", "y", "z"), " of ", Vector.class, " to ", double.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".set" + arg(0).toUpperCase() + "(" + arg(2) + ");";
    }
}
