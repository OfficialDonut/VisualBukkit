package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Iterates while a condition is true")
@Category(StatementCategory.CONTROLS)
public class StatWhileLoop extends ParentBlock {

    @Override
    protected Syntax init() {
        return new Syntax("loop while", boolean.class);
    }

    @Override
    public String toJava() {
        return "while (" + arg(0) + ") {" + getChildJava() + "}";
    }
}
