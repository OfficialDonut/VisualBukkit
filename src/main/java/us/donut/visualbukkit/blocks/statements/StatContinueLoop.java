package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Advances loop to next iteration")
@Category(StatementCategory.CONTROLS)
public class StatContinueLoop extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("continue loop");
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        validateParent(StatForLoop.class, StatListLoop.class, StatWhileLoop.class);
    }

    @Override
    public String toJava() {
        return "continue;";
    }
}
