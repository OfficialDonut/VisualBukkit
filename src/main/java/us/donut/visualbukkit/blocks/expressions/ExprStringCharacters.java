package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Category("String")
@Description({"The characters of a string", "Returns: list of strings"})
public class ExprStringCharacters extends ExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("characters of", String.class);
    }

    @Override
    public String toJava() {
        return "new SimpleList(" + arg(0) + ".split(\"(?!^)\"))";
    }
}
