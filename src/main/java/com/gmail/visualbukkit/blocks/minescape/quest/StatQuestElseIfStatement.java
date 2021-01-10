package com.gmail.visualbukkit.blocks.minescape.quest;

import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Category(Category.MINESCAPE)
@Description("Runs code if a condition is true")
public class StatQuestElseIfStatement extends StatQuestStatement {

    @Override
    public String getStatement() {
        return "else if";
    }

    @Override
    public void update() {
        super.update();
        if (hasPrevious() && !(previous instanceof StatQuestIfStatement) && !(previous instanceof StatQuestElseIfStatement)) {
            setInvalid("'else' must be placed directly after an quest 'if' or 'else if'");
        }
    }
}
