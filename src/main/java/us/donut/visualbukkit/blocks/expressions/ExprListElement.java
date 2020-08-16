package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"An element at an index in a list", "Returns: object"})
@Modifier(ModificationType.SET)
public class ExprListElement extends ExpressionBlock<Object> {

    @Override
    protected Syntax init() {
        return new Syntax("element", int.class, "of", List.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".get(" + arg(0) + ")";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(1) + ".set(" + arg(0) + "," + delta + ");" : null;
    }
}
