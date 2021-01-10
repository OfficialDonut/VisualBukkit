package com.gmail.visualbukkit.blocks.minescape.quest;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.statements.StatElseIfStatement;
import com.gmail.visualbukkit.blocks.statements.StatIfStatement;

@Category(Category.MINESCAPE)
@Description("Runs code if the previous if statement failed")
public class StatQuestElseStatement extends ParentBlock {

    public StatQuestElseStatement() {
        init("else");
    }

    @Override
    public void update() {
        super.update();
        if (hasPrevious() && !(previous instanceof StatQuestIfStatement) && !(previous instanceof StatQuestElseIfStatement)) {
            setInvalid("'else' must be placed directly after an 'if' or 'else if'");
        }
    }

    @Override
    public String toJava() {
        return "";
    }
}
