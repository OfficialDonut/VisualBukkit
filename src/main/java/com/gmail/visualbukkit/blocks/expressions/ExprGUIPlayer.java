package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.blocks.structures.StructCreateGUI;
import org.bukkit.entity.Player;

@Name("GUI Player")
@Description("The player for which a GUI is being created")
public class ExprGUIPlayer extends ExpressionBlock<Player> {

    public ExprGUIPlayer() {
        init("GUI player");
    }

    @Override
    public void update() {
        super.update();
        validateStructure("GUI player must be used in create GUI", StructCreateGUI.class);
    }

    @Override
    public String toJava() {
        return "guiPlayer";
    }
}
