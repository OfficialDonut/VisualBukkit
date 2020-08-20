package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Strikes lightning at a location")
@Category(StatementCategory.WORLD)
public class StatStrikeLightning extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("strike", new ChoiceParameter("real", "fake"), "lightning at", Location.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getWorld().strike" + (arg(0).equals("real") ? "Lightning" : "LightningEffect") + "(" + arg(1) + ");";
    }
}
