package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Checks if a material is air", "Returns: boolean"})
public class ExprIsAir extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(Material.class, new ChoiceParameter("is", "is not"), "air");
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".isAir()";
    }
}
