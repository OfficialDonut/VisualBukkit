package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;

import java.util.List;

@Description({"Gets a random element from a list", "Returns: object"})
public class ExprRandomElement extends ExpressionBlock<Object> {

    @Override
    protected Syntax init() {
        return new Syntax("random element of", List.class);
    }

    @Override
    public String toJava() {
        BuildContext.addUtilMethod(UtilMethod.GET_RANDOM_ELEMENT);
        return "UtilMethods.getRandomElement(" + arg(0) + ")";
    }
}
