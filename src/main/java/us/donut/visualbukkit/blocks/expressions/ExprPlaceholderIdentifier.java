package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;
import us.donut.visualbukkit.plugin.modules.classes.PlaceholderEvent;

@Description({"The identifier in a PlaceholderEvent", "Returns: string", "Requires: PlaceholderAPI"})
@Event(PlaceholderEvent.class)
public class ExprPlaceholderIdentifier extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("placeholder identifier");
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.PlACEHOLDERAPI);
        return "event.getIdentifier()";
    }
}
