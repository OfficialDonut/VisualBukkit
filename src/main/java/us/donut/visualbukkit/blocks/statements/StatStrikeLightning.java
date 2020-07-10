package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;

@Description("Strikes lightning at a location")
public class StatStrikeLightning extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("strike", new ChoiceParameter("real", "fake"), "lightning at", Location.class);
    }

    @Override
    public String toJava() {
        BuildContext.addUtilMethod("strikeLightning");
        return "UtilMethods.strikeLightning(" + arg(1) + "," + arg(0).equals("fake") + ");";
    }
}
