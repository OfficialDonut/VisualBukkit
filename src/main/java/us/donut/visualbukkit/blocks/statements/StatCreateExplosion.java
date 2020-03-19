package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Creates an explosion at a location")
public class StatCreateExplosion extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("create explosion at", Location.class, "with power", float.class, "with",
                new ChoiceParameter("fire", "no fire"), "and", new ChoiceParameter("break blocks", "do not break blocks"));
    }

    @Override
    public String toJava() {
        boolean fire = arg(2).equals("fire");
        boolean blocks = arg(3).equals("break blocks");
        String locVar = randomVar();
        return "Location " + locVar + "=" + arg(0) + ";" +
                locVar + ".getWorld().createExplosion(" + locVar + "," + arg(1) + "," + fire + "," + blocks + ");";
    }
}
