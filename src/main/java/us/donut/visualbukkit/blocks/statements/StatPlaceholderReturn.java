package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.hooks.PlaceholderEvent;

@Description({"Returns a value in a PlaceholderEvent", "Requires: PlaceholderAPI"})
@Event(PlaceholderEvent.class)
public class StatPlaceholderReturn extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("return", String.class);
    }

    @Override
    public String toJava() {
        return "event.setResult(" + arg(0) + ");";
    }
}
