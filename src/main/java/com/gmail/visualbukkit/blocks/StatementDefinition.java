package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.annotations.BlockColor;
import javafx.scene.paint.Color;

public class StatementDefinition<T extends StatementBlock> extends BlockDefinition<T> {

    private Color blockColor;

    public StatementDefinition(Class<T> clazz) throws NoSuchMethodException {
        super(clazz);
        blockColor = clazz.isAnnotationPresent(BlockColor.class) ?
                Color.valueOf(clazz.getAnnotation(BlockColor.class).value()) :
                Color.WHITE;
    }

    public Color getBlockColor() {
        return blockColor;
    }
}
