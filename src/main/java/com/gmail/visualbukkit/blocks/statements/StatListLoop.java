package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Category(Category.CONTROLS)
@Description("Iterates through the elements of a list")
public class StatListLoop extends ParentBlock {

    public StatListLoop() {
        init("loop ", List.class);
    }

    @Override
    public String toJava() {
        return "for (Object loopValue" + getNestedLoops(this) + ":" + arg(0) + ") {" +
                getChildJava() +
                "}";
    }

    public static int getNestedLoops(StatementBlock block) {
        int num = 0;
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
