package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.ui.IconButton;
import org.json.JSONObject;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public abstract class SizedExpressionBlock extends ExpressionBlock {

    private int size = 0;

    public SizedExpressionBlock() {
        addToHeader(new IconButton(FontAwesomeSolid.PLUS, e -> {
            UndoManager.current().execute(() -> {
                incrementSize();
                size++;
            });
        }));
        addToHeader(new IconButton(FontAwesomeSolid.MINUS, e -> {
            if (size > 0) {
                UndoManager.current().execute(() -> {
                    decrementSize();
                    size--;
                });
            }
        }));
    }

    protected abstract void incrementSize();

    protected abstract void decrementSize();

    @Override
    public JSONObject serialize() {
        JSONObject json = super.serialize();
        json.put("size", size);
        return json;
    }

    @Override
    public void deserialize(JSONObject json) {
        for (int i = 0; i < json.optInt("size"); i++) {
            incrementSize();
            size++;
        }
        super.deserialize(json);
    }
}
