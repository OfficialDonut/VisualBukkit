package us.donut.visualbukkit.blocks.statements;

import javafx.scene.Parent;
import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.BlockPane;

@Description("Iterates a certain number of times")
public class StatForLoop extends ParentBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("loop", int.class, "times");
    }

    @Override
    public String toJava() {
        String index = "loopIndex" + getNestedLoops();
        return "for (int " + index + "=0;" + index + "<" + arg(0) + ";" + index + "++) {" +
                getChildJava() + "}";
    }

    private int getNestedLoops() {
        int nestedLoops = 0;
        Parent parent = getParent();
        while (!(parent instanceof BlockPane.BlockArea)) {
            if (parent instanceof StatLoopList || parent instanceof StatForLoop) {
                nestedLoops++;
            }
            parent = parent.getParent();
        }
        return nestedLoops;
    }
}
