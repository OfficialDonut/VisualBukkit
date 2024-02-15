package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.ui.PopOverSelector;
import javafx.scene.control.Tooltip;

public class ExpressionSelector extends PopOverSelector<BlockFactory<ExpressionBlock>> {

    public ExpressionSelector(ClassInfo type) {
        super("pinned-expressions", BlockRegistry.getExpressions());
        getStyleClass().add("expression-parameter");
        setPromptText("<" + type.getSimpleName() + ">");
        setTooltip(new Tooltip(type.getName()));
        setCellTooltip(t -> new Tooltip(t.getBlockDefinition().description()));
    }
}
