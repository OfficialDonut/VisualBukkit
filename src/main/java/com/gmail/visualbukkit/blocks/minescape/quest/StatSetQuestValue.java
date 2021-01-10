package com.gmail.visualbukkit.blocks.minescape.quest;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Category(Category.MINESCAPE)
@Description("Sets the quest value of a player")
public class StatSetQuestValue extends StatementBlock {

    private ChoiceParameter questChoice = new ChoiceParameter(QuestChoiceParameter.getQuestListValues());
    private ChoiceParameter valueChoice = new ChoiceParameter(Collections.emptyList(), false);

    public StatSetQuestValue() {
        questChoice.valueProperty().addListener((e) -> setQuestChoiceItems());
        valueChoice.valueProperty().addListener((e) -> update());
        init("Set Quest ", questChoice, " = ", valueChoice);
    }

    private void setQuestChoiceItems(){
        valueChoice.setItems(FXCollections.observableArrayList(QuestChoiceParameter.getChoiceParameter(questChoice.getValue())));
        update();
    }

    @Override
    public String toJava() {
        return "";
    }
}
