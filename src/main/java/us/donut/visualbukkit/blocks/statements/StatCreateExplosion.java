package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Creates an explosion at a location")
@Category(StatementCategory.WORLD)
public class StatCreateExplosion extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("create explosion", Syntax.LINE_SEPARATOR,
                "location:", Location.class, Syntax.LINE_SEPARATOR,
                "power:   ", float.class, Syntax.LINE_SEPARATOR,
                "fire:    ", new BinaryChoiceParameter("make fire", "do not make fire"), Syntax.LINE_SEPARATOR,
                "blocks:  ", new BinaryChoiceParameter("break blocks", "do not break blocks"));
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWorld().createExplosion(" + arg(0) + "," + arg(1) + "," + arg(2).equals("make fire") + "," + arg(3).equals("break blocks") + ");";
    }
}
