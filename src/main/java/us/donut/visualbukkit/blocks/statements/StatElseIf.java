package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Runs code if the previous if statement failed and a condition is true")
public class StatElseIf extends StatIf {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("else if", boolean.class, ":");
    }

    @Override
    public String toJava() {
        return "else if (" + arg(0) + ") " + "{" + getChildJava() + "}";
    }
}
