package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;
import us.donut.visualbukkit.plugin.modules.classes.PlaceholderEvent;

@Description({"The identifier in a PlaceholderEvent", "Returns: string", "Requires: PlaceholderAPI"})
public class ExprEventPlaceholderIdentifier extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("placeholder identifier");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlaceholderEvent.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.PlACEHOLDERAPI);
        return "event.getIdentifier()";
    }
}
