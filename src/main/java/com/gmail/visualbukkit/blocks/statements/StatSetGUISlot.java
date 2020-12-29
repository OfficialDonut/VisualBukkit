package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.blocks.structures.StructCreateGUI;
import org.bukkit.inventory.ItemStack;

@Name("Set GUI Slot")
@Description("Sets the item in a GUI slot")
public class StatSetGUISlot extends StatementBlock {

    public StatSetGUISlot() {
        init("set slot ", int.class, " to ", ItemStack.class);
    }

    @Override
    public void update() {
        super.update();
        validateStructure("Set GUI slot must be used in create GUI", StructCreateGUI.class);
    }

    @Override
    public String toJava() {
        return "gui.setItem(" + arg(0) + "," + arg(1) + ");";
    }
}
