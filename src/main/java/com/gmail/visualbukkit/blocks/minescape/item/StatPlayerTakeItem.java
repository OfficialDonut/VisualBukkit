package com.gmail.visualbukkit.blocks.minescape.item;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.blocks.minescape.quest.QuestChoiceParameter;
import javafx.collections.FXCollections;

import java.util.Collections;

@Category(Category.MINESCAPE)
@Description("Takes item from Player")
public class StatPlayerTakeItem extends StatementBlock {

    public StatPlayerTakeItem() {
        init("Take ", new StringLiteralParameter(), " x ", new StringLiteralParameter(), " from Player");
    }

    @Override
    public String toJava() {
        return "";
    }
}
