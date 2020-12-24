package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import org.bukkit.Location;

@Category(Category.WORLD)
@Description("Sets a coordinate of a location")
public class StatSetLocationCoordinate extends StatementBlock {

    public StatSetLocationCoordinate() {
        init("set ", new ChoiceParameter("x", "y", "z"), " of ", Location.class, " to ", double.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".set" + arg(0).toUpperCase() + "(" + arg(2) + ");";
    }
}
