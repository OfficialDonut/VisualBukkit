package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.annotations.BlockColor;
import com.gmail.visualbukkit.blocks.annotations.Category;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

@Category(Category.STRUCTURES)
@BlockColor("#c686f0ff")
public abstract class StructureBlock extends StatementBlock {

    public StructureBlock() {
        syntaxBox.setPadding(new Insets(3));
        syntaxBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10, 10, 0, 0, false), BorderStroke.THIN)));
        syntaxBox.setBackground(new Background(new BackgroundFill(getDefinition().getBlockColor(), new CornerRadii(10, 10, 0, 0, false), Insets.EMPTY)));
    }

    @Override
    public final String toJava() {
        return "";
    }

    public String getChildJava() {
        StringBuilder builder = new StringBuilder();
        StatementBlock child = next;
        while (child != null) {
            builder.append(child.toJava());
            child = child.getNext();
        }
        return builder.toString();
    }

    @Override
    public StructureBlock getStructure() {
        return this;
    }
}
