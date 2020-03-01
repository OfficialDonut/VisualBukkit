package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Runs code if the previous if statement failed")
public class StatElse extends ParentBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("else:");
    }

    @Override
    public String toJava() {
        return "else {" + getChildJava() + "}";
    }
}
