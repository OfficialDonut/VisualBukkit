package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Cancels an event")
public class StatCancelEvent extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("cancel event");
    }

    @Override
    public String toJava() {
        return "event.setCancelled(true);";
    }
}
