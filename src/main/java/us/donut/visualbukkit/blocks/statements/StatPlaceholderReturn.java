package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;
import us.donut.visualbukkit.plugin.modules.classes.PlaceholderEvent;

@Description({"Returns a value in a PlaceholderEvent", "Requires: PlaceholderAPI"})
public class StatPlaceholderReturn extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("return", String.class);
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        validateEvent(PlaceholderEvent.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.PlACEHOLDERAPI);
        return "event.setResult(" + arg(0) + ");";
    }
}
