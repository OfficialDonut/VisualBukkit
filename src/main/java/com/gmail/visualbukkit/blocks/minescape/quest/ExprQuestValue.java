package com.gmail.visualbukkit.blocks.minescape.quest;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.minescape.MinescapeType;
import javafx.collections.FXCollections;

import java.util.Collections;

@Description("A boolean (true or false)")
public class ExprQuestValue extends ExpressionBlock<MinescapeType> {


    private ChoiceParameter questChoice = new QuestChoiceParameter();
    private ChoiceParameter checkChoice = new ChoiceParameter("<", "<=", "==", "=>", ">");
    private ChoiceParameter valueChoice = new ChoiceParameter(Collections.emptyList(), false);

    public ExprQuestValue() {
        questChoice.valueProperty().addListener((e) -> setQuestChoiceItems());
        valueChoice.valueProperty().addListener((e) -> update());
        checkChoice.setMaxWidth(20);
        init(questChoice, " ", checkChoice, " ", valueChoice);
    }

    private void setQuestChoiceItems(){
        valueChoice.setItems(FXCollections.observableArrayList(QuestChoiceParameter.getChoiceParameter(questChoice.getValue())));
        update();
    }

    @Override
    public String toJava() {
        return arg(0);
    }
}
