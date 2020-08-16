package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Name("If Statement")
@Description("Runs code if a condition is true")
@Category(StatementCategory.CONTROLS)
public class StatIf extends ParentBlock {

    @Override
    protected Syntax init() {
        return new Syntax("if", boolean.class);
    }

    @Override
    public String toJava() {
        return "if (" + arg(0) + ") {" + getChildJava() + "}";
    }
}
