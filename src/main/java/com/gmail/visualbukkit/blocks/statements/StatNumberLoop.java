package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Category(Category.CONTROLS)
@Description("Iterates a certain number of times")
public class StatNumberLoop extends ParentBlock {

    public StatNumberLoop() {
        init("loop ", int.class, " times");
    }

    @Override
    public String toJava() {
        String indexVar = "loopNumber" + getNestedLoops(this);
        return "for (int " + indexVar + "=0;" + indexVar + "<" + arg(0) + ";" + indexVar + "++) {" +
                getChildJava() +
                "}";
    }

    public static int getNestedLoops(StatementBlock block) {
        int num = 0;
        while (block != null) {
            if (block instanceof ParentBlock.ChildConnector) {
                block = ((ParentBlock.ChildConnector) block).getParentStatement();
                if (block instanceof StatNumberLoop) {
                    num++;
                }
            } else {
                block = block.getPrevious();
            }
        }
        return num;
    }
}
