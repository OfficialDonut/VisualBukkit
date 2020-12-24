package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import org.bukkit.Location;

@Category(Category.WORLD)
@Description("Strikes lightning at a location")
public class StatStrikeLightning extends StatementBlock {

    public StatStrikeLightning() {
        init("strike ", new ChoiceParameter("real", "fake"), " lightning at ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getWorld().strike" + (arg(0).equals("real") ? "Lightning" : "LightningEffect") + "(" + arg(1) + ");";
    }
}
