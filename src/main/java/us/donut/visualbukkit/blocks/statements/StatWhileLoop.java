package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Iterates while a condition is true")
public class StatWhileLoop extends ParentBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("loop while", boolean.class);
    }

    @Override
    public String toJava() {
        return "while (" + arg(0) + ") {" + getChildJava() + "}";
    }
}
