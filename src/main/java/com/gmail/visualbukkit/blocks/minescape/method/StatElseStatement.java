package com.gmail.visualbukkit.blocks.minescape.method;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Category(Category.MINESCAPE)
@Description("Runs code if the previous if statement failed")
public class StatElseStatement extends ParentBlock {

    public StatElseStatement() {
        init("else");
    }

    @Override
    public void update() {
        super.update();
        if (hasPrevious() && !(previous instanceof StatIfStatement) && !(previous instanceof StatElseIfStatement)) {
            setInvalid("'else' must be placed directly after an 'if' or 'else if'");
        }
    }

    @Override
    public String toJava() {
        return "else {" + getChildJava() + "}";
    }
}
