package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.ui.StyleableVBox;

import java.util.function.Consumer;

public abstract class StatementConnector extends StyleableVBox implements Consumer<Statement.Block> {

    private static StatementConnector current;

    public StatementConnector() {
        getStyleClass().add("statement-connector");
        setDisable(true);
    }

    public void show() {
        if (current != this) {
            hideCurrent();
            setDisable(false);
            current = this;
        }
    }

    public static void hideCurrent() {
        if (current != null) {
            current.setDisable(true);
            current = null;
        }
    }

    public static StatementConnector getCurrent() {
        return current;
    }
}
