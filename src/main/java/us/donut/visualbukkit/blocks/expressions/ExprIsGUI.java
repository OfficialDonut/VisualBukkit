package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.StringLiteralParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Name("Is GUI")
@Description({"Checks if an InventoryEvent applies to a GUI", "Returns: boolean"})
public class ExprIsGUI extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax("event is for GUI", new StringLiteralParameter());
    }

    @Override
    public void update() {
        super.update();
        validateEvent(InventoryEvent.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.GUI);
        return "GuiManager.getInstance().isGui(" + arg(0) + ",event)";
    }
}
