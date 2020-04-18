package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"An element at an index in a list", "Changers: set", "Returns: object"})
public class ExprListElement extends ChangeableExpressionBlock<Object> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("element", int.class, "of", SimpleList.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".get(" + arg(0) + ")";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(1) + ".set(" + arg(0) + "," + delta + ");" : null;
    }
}
