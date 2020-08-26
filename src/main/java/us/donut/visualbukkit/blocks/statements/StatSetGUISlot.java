package us.donut.visualbukkit.blocks.statements;

import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.structures.StructGUI;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Name("Set GUI Slot")
@Description("Sets the item stack in a GUI slot")
public class StatSetGUISlot extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("set slot", int.class, "to", ItemStack.class);
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        validateStructure(StructGUI.class);
    }

    @Override
    public String toJava() {
        return "gui.setItem(" + arg(0) + "," + arg(1) + ");";
    }
}
