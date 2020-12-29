package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.bukkit.entity.Player;

@Name("Open GUI")
@Description("Opens a GUI to a player")
public class StatOpenGUI extends StatementBlock {

    public StatOpenGUI() {
        init("open GUI ", new StringLiteralParameter(), " to ", Player.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.GUI);
    }

    @Override
    public String toJava() {
        return "GUIManager.getInstance().open(" + arg(0) + "," + arg(1) + ");";
    }
}
