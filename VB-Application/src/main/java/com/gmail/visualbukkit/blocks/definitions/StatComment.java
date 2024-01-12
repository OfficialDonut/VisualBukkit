package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.Collections;

@BlockDefinition(id = "stat-comment", name = "Comment")
public class StatComment extends StatementBlock {

    public StatComment() {
        InputParameter inputParameter = new InputParameter();
        getChildren().setAll(new HBox(new Label("//"), inputParameter));
        setOpacity(0.75);
        parameters = Collections.singletonList(inputParameter);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "";
    }
}
