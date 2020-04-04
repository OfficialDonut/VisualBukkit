package us.donut.visualbukkit.blocks.expressions;

import javafx.scene.Parent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.statements.StatForLoop;
import us.donut.visualbukkit.blocks.statements.StatLoopList;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.BlockPane;

@Description({"The current element of a list being looped", "Returns: object"})
public class ExprLoopValue extends ExpressionBlock<Object> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("loop value");
    }

    @Override
    public String toJava() {
        StatementBlock loopStatement = getLoopStatement();
        if (loopStatement instanceof StatLoopList) {
            int nestedLoops = getNestedLoops();
            return "loopList" + nestedLoops + ".get(loopIndex" + nestedLoops + ")";
        } else if (loopStatement instanceof StatForLoop) {
            return "new Integer(loopIndex" + getNestedLoops() + ")";
        }
        throw new IllegalStateException();
    }

    private int getNestedLoops() {
        int nestedLoops = -1;
        Parent parent = getParent();
        while (!(parent instanceof BlockPane.BlockArea)) {
            if (parent instanceof StatLoopList || parent instanceof StatForLoop) {
                nestedLoops++;
            }
            parent = parent.getParent();
        }
        return nestedLoops;
    }

    private StatementBlock getLoopStatement() {
        Parent parent = getParent();
        while (!(parent instanceof BlockPane.BlockArea)) {
            if ((parent instanceof StatLoopList || parent instanceof StatForLoop) && !(parent.equals(getParent().getParent().getParent()))) {
                return (StatementBlock) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }
}
