package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.TreeType;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Generates a tree at a location")
public class StatGenerateTree extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("generate", TreeType.class, "tree at", Location.class);
    }

    @Override
    public String toJava() {
        return "generateTree(" + arg(0) + "," + arg(1) + ");";
    }

    @UtilMethod
    public static void generateTree(TreeType treeType, Location loc) {
        loc.getWorld().generateTree(loc, treeType);
    }
}
