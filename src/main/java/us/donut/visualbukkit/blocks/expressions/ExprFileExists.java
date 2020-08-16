package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.io.File;

@Description({"Checks if a file exists", "Returns: boolean"})
public class ExprFileExists extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(File.class, new ChoiceParameter("exists", "does not exist"));
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".exists()";
    }
}
