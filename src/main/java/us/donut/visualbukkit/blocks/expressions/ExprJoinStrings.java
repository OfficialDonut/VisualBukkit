package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"Joins a list of strings into one string", "Returns: string"})
public class ExprJoinStrings extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax(List.class, "joined with", String.class);
    }

    @Override
    public String toJava() {
        return "String.join(" + arg(1) + "," + arg(0) + ")";
    }
}
