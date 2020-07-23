package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Category("String")
@Description({"Joins a list of strings into one string", "Returns: string"})
public class ExprJoinStrings extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(SimpleList.class, "joined with", String.class);
    }

    @Override
    public String toJava() {
        return "String.join(" + arg(1) + "," + arg(0) + ")";
    }
}
