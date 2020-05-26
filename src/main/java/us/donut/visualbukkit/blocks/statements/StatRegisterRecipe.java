package us.donut.visualbukkit.blocks.statements;

import org.bukkit.inventory.Recipe;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("registers a recipe")
public class StatRegisterRecipe extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("register", Recipe.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.addRecipe(" + arg(0) + ");";
    }
}
