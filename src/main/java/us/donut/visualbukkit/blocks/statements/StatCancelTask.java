package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.syntax.Syntax;

public class StatCancelTask extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("cancel task");
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        validateParent(StatScheduleTask.class);
    }

    @Override
    public String toJava() {
        return "cancel();";
    }
}
