package us.donut.visualbukkit.blocks.statements;

import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Description("Sets the skin of a skull item stack")
public class StatSetSkullSkin extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("set skull skin", Syntax.LINE_SEPARATOR,
                "item stack:", ItemStack.class, Syntax.LINE_SEPARATOR,
                "skin value:", String.class, Syntax.LINE_SEPARATOR,
                "signature: ", String.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.REFLECTION_UTIL);
        BuildContext.addUtilMethod(UtilMethod.SET_SKULL_SKIN);
        return "UtilMethods.setSkullSkin(" + arg(0) + "," + arg(1) + "," + arg(2) + ");";
    }
}
