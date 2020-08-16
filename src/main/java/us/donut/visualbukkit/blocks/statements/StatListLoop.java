package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description("Iterates through the elements of a list")
@Category(StatementCategory.CONTROLS)
public class StatListLoop extends ParentBlock {

    @Override
    protected Syntax init() {
        return new Syntax("loop", List.class);
    }

    @Override
    public String toJava() {
        return "for (Object loopValue" + getNestedLoops() + ":" + arg(0) + ") {" +
                getChildJava() +
                "}";
    }

    private int getNestedLoops() {
        int num = 0;
        StatementBlock block = this;
        while (block != null) {
            if (block instanceof ParentBlock.ChildConnector) {
                block = ((ParentBlock.ChildConnector) block).getParentStatement();
                if (block instanceof StatListLoop) {
                    num++;
                }
            } else {
                block = block.getPrevious();
            }
        }
        return num;
    }
}
