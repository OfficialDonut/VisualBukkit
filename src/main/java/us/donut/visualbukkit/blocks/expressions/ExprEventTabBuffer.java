package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.server.TabCompleteEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The buffer in a TabCompleteEvent", "Returns: string"})
public class ExprEventTabBuffer extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("tab buffer");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(TabCompleteEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getBuffer()";
    }
}
