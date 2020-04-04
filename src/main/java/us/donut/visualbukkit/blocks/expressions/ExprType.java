package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.TypeHandler;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"An object type", "Returns: type"})
public class ExprType extends ExpressionBlock<Class> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(new ChoiceParameter(TypeHandler.getAliases()));
    }

    @Override
    public String toJava() {
        return TypeHandler.getType(arg(0)).getCanonicalName() + ".class";
    }
}
