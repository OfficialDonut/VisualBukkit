package com.gmail.visualbukkit.blocks.minescape.method;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.minescape.MinescapeType;

@Category(Category.MINESCAPE)
@Description("Runs code if the previous if statement failed and a condition is true")
public class StatElseIfStatement extends ParentBlock {

    public StatElseIfStatement() {
        init("else if ", MinescapeType.class);
    }

    @Override
    public void update() {
        super.update();
        if (hasPrevious() && !(previous instanceof StatIfStatement) && !(previous instanceof StatElseIfStatement)) {
            setInvalid("'else if' must be placed directly after an 'if' or 'else if'");
        }
    }

    @Override
    public String toJava() {
        return "";
    }
}
