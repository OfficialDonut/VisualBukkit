package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.annotations.BlockColor;
import com.gmail.visualbukkit.blocks.annotations.Category;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StatementDefinition<T extends StatementBlock> extends BlockDefinition<T> {

    private Color blockColor;
    private Set<String> categories;

    public StatementDefinition(Class<T> clazz) throws NoSuchMethodException {
        super(clazz);
        blockColor = clazz.isAnnotationPresent(BlockColor.class) ?
                Color.valueOf(clazz.getAnnotation(BlockColor.class).value()) :
                Color.WHITE;

        categories = clazz.isAnnotationPresent(Category.class) ?
                Arrays.stream(clazz.getAnnotation(Category.class).value()).collect(Collectors.toSet()) :
                new HashSet<>(1);
        categories.add(Category.STATEMENTS);
    }

    public Color getBlockColor() {
        return blockColor;
    }

    public Set<String> getCategories() {
        return categories;
    }
}
