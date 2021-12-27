package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.ui.LanguageManager;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public abstract class BlockSource<T extends BlockDefinition> extends Label {

    private T block;

    public BlockSource(T block) {
        super(block.toString());
        this.block = block;
        getStyleClass().add("block-source");
        String desc = block.getDescription();
        Tooltip tooltip = new Tooltip(desc == null ? LanguageManager.get("tooltip.no_description") : desc);
        tooltip.setShowDelay(Duration.millis(500));
        setTooltip(tooltip);
    }

    public T getBlockDefinition() {
        return block;
    }
}
