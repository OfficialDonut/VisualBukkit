package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.io.File;

@Description({"Checks if a file is a directory", "Returns: boolean"})
public class ExprIsDirectory extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(File.class, new BinaryChoiceParameter("is", "is not"), "a directory");
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".isDirectory()";
    }
}
