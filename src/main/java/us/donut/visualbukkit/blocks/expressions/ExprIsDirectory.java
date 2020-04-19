package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.io.File;

@Description({"Checks if a file is a directory", "Returns: boolean"})
public class ExprIsDirectory extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(File.class, new ChoiceParameter("is", "is not"), "a directory");
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".isDirectory()";
    }
}
