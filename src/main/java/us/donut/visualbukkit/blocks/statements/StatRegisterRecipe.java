package us.donut.visualbukkit.blocks.statements;

import org.bukkit.inventory.Recipe;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Registers a recipe")
public class StatRegisterRecipe extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("register", Recipe.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.addRecipe(" + arg(0) + ");";
    }
}
