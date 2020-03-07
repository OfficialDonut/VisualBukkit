package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Damageable;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Damages a living entity")
public class StatDamage extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("damage", Damageable.class, "by", double.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".damage(" + arg(1) + ");";
    }
}
