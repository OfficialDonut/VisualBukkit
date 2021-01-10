package com.gmail.visualbukkit.blocks.minescape.quest;

import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Category(Category.MINESCAPE)
@Description("Runs code if a condition is true")
public class StatQuestIfStatement extends StatQuestStatement {

    @Override
    public String getStatement() {
        return "if";
    }
}
