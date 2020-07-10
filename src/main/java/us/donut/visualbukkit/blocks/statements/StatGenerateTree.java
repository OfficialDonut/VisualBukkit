package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.TreeType;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;

@Description("Generates a tree at a location")
public class StatGenerateTree extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("generate", TreeType.class, "tree at", Location.class);
    }

    @Override
    public String toJava() {
        BuildContext.addUtilMethod("generateTree");
        return "UtilMethods.generateTree(" + arg(0) + "," + arg(1) + ");";
    }
}
