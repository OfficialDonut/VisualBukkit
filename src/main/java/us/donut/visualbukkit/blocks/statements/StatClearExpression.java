package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.function.Predicate;

@Description("Clears the value of an expression")
@Category(StatementCategory.MODIFIERS)
public class StatClearExpression extends StatementBlock {

    private static Predicate<ExpressionDefinition<?>> validator = expression -> expression.getModifiers().contains(ModificationType.CLEAR);
    private ExpressionParameter exprParameter;

    @Override
    protected Syntax init() {
        exprParameter = new ExpressionParameter(Object.class, validator);
        return new Syntax("clear", exprParameter);
    }

    @Override
    public String toJava() {
        ExpressionBlock<?> expression = exprParameter.getExpression();
        return expression != null ? expression.modify(ModificationType.CLEAR, "") : "";
    }
}
