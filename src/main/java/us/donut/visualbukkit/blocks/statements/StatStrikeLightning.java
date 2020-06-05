package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Strikes lightning at a location")
public class StatStrikeLightning extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("strike", new ChoiceParameter("real", "fake"), "lightning at", Location.class);
    }

    @Override
    public String toJava() {
        return "strikeLightning(" + arg(1) + "," + arg(0).equals("fake") + ");";
    }

    @UtilMethod
    public static void strikeLightning(Location loc, boolean fake) {
        if (fake) {
            loc.getWorld().strikeLightningEffect(loc);
        } else {
            loc.getWorld().strikeLightning(loc);
        }
    }
}
