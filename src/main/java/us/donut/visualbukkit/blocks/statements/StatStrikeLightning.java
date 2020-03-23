package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
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
        String method = arg(0).equals("real") ? "strikeLightning" : "strikeLightningEffect";
        String locVar = randomVar();
        return "Location " + locVar + "=" + arg(1) + ";" +
                locVar + ".getWorld()." + method + "(" + locVar + ");";
    }
}
