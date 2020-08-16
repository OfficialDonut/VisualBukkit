package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.TypeHandler;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"An object type", "Returns: type"})
public class ExprObjectType extends ExpressionBlock<Class> {

    @Override
    protected Syntax init() {
        return new Syntax(new ChoiceParameter(TypeHandler.getAliases()));
    }

    @Override
    public String toJava() {
        return TypeHandler.getType(arg(0)).getCanonicalName() + ".class";
    }
}
