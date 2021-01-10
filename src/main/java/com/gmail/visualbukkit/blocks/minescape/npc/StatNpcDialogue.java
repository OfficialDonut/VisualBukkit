package com.gmail.visualbukkit.blocks.minescape.npc;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import javafx.scene.control.Button;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Category(Category.MINESCAPE)
@Description("Defines a npc dialogue")
public class StatNpcDialogue extends StatementBlock {

    private StringLiteralParameter title = new StringLiteralParameter();
    private List<StringLiteralParameter> dialogueLines = new ArrayList<>();
    private Button addLine = new Button("+");

    public StatNpcDialogue() {
        build();
        addLine.setOnMouseClicked((e)->addLine());
    }

    protected void addLine(){
        dialogueLines.add(new StringLiteralParameter());
        build();
    }

    protected void removeLine(int line){
        dialogueLines.remove(line);
        build();
    }

    protected void build(){
        clear();
        init("Title: ", title, " ", addLine);
        for(int line = 0; line < dialogueLines.size(); line++){
            initLine(createRemoveButton(line), " ", dialogueLines.get(line));
        }
    }

    protected Button createRemoveButton(int line){
        Button button = new Button("-");
        button.setOnMouseClicked((e)-> removeLine(line));
        return button;
    }

    @Override
    public String toJava() {
        return "";
    }


    @Override
    public void deserialize(JSONObject obj) {
        JSONArray parameterArray = obj.optJSONArray("parameters");
        if (parameterArray != null) {
            for (int i = 0; i < parameterArray.length() - 1; i++) {
                dialogueLines.add(new StringLiteralParameter());
            }
            build();
        }
        super.deserialize(obj);
    }
}
