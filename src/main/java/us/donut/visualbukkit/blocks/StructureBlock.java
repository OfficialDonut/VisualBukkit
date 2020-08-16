package us.donut.visualbukkit.blocks;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import us.donut.visualbukkit.blocks.annotations.Category;

@Category(StatementCategory.STRUCTURES)
public abstract class StructureBlock extends StatementBlock {

    public StructureBlock() {
        syntaxBox.getStyleClass().clear();
        syntaxBox.getStyleClass().add("structure-block");
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        if (previous != null) {
            throw new IllegalStateException();
        }
    }

    public abstract void insertInto(JavaClassSource mainClass);

    @Override
    public final String toJava() {
        throw new UnsupportedOperationException();
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
