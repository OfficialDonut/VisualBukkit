package com.gmail.visualbukkit.blocks.minescape.quest;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.minescape.quest.QuestChoiceParameter;
import javafx.collections.FXCollections;

import java.util.Collections;

public abstract class StatQuestStatement extends ParentBlock {

    private ChoiceParameter questChoice = new QuestChoiceParameter();
    private ChoiceParameter checkChoice = new ChoiceParameter("<", "<=", "==", "=>", ">");
    private ChoiceParameter valueChoice = new ChoiceParameter(Collections.emptyList(), false);

    public StatQuestStatement() {
        questChoice.valueProperty().addListener((e) -> setQuestChoiceItems());
        valueChoice.valueProperty().addListener((e) -> update());
        checkChoice.setMaxWidth(20);
        init(getStatement() + " ", questChoice, " ", checkChoice, " ", valueChoice);
    }

    public abstract String getStatement();

    private void setQuestChoiceItems(){
        valueChoice.setItems(FXCollections.observableArrayList(QuestChoiceParameter.getChoiceParameter(questChoice.getValue())));
        update();
    }

    @Override
    public void update() {
        super.update();
        if(valueChoice.getValue() ==null){
            super.setInvalid("Missing value.");
        }else if(questChoice.getValue() != null && valueChoice.getValue() != null){
            if(!valueChoice.getValue().startsWith(questChoice.getValue())){
                super.setInvalid("Quests do not match");
            }
        }
    }

    @Override
    public String toJava() {
        return "";
    }
}
