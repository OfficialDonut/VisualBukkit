package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.statements.StatListLoop;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The current element of a list being looped", "Returns: object"})
public class ExprLoopValue extends ExpressionBlock<Object> {

    @Override
    protected Syntax init() {
        return new Syntax("loop value");
    }

    @Override
    public void update() {
        super.update();
        validateParent(StatListLoop.class);
    }

    @Override
    public String toJava() {
        return "loopValue" + getNestedLoops();
    }

    private int getNestedLoops() {
        int num = 0;
        StatementBlock block = getStatement();
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
        return num - 1;
    }
}
