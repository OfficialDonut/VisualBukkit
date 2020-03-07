package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description("Removes an entity")
public class StatRemoveEntity extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("remove", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".remove();";
    }
}
