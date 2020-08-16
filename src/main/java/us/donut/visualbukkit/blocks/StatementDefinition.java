package us.donut.visualbukkit.blocks;

import us.donut.visualbukkit.blocks.annotations.Category;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StatementDefinition<T extends StatementBlock> extends BlockDefinition<T> {

    private Set<StatementCategory> categories;

    public StatementDefinition(Class<T> clazz) throws NoSuchMethodException {
        super(clazz);
        if (clazz.isAnnotationPresent(Category.class)) {
            categories = Arrays.stream(clazz.getAnnotation(Category.class).value()).collect(Collectors.toSet());
        } else {
            categories = new HashSet<>(2);
            categories.add(StatementCategory.MISC);
        }
        categories.add(StatementCategory.ALL);
    }

    public StatementLabel createLabel() {
        return new StatementLabel(this);
    }

    public Set<StatementCategory> getCategories() {
        return categories;
    }
}
