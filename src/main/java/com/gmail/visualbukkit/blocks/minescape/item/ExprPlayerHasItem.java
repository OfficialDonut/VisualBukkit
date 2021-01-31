package com.gmail.visualbukkit.blocks.minescape.item;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.blocks.minescape.MinescapeType;
import com.gmail.visualbukkit.blocks.minescape.quest.QuestChoiceParameter;
import javafx.collections.FXCollections;

import java.util.Collections;

@Description("A boolean (true or false)")
public class ExprPlayerHasItem extends ExpressionBlock<MinescapeType> {

    public ExprPlayerHasItem() {
        init("Player has item ", new StringLiteralParameter(), " x ", new StringLiteralParameter());
    }

    @Override
    public String toJava() {
        return arg(0);
    }
}
