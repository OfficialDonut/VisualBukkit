package us.donut.visualbukkit.blocks.statements;

import javafx.scene.Parent;
import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.BlockPane;
import us.donut.visualbukkit.util.SimpleList;

@Description("Iterates through the elements of a list")
public class StatLoopList extends ParentBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("loop", SimpleList.class, ":");
    }

    @Override
    public String toJava() {
        int nestedLoops = getNestedLoops();
        String list = "loopList" + nestedLoops;
        String index = "loopIndex" + nestedLoops;
        return "SimpleList " + list + " = " + arg(0) + ";" +
                "for (int " + index + "=0;" + index + "<" + list + ".size();" + index + "++) {" +
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
