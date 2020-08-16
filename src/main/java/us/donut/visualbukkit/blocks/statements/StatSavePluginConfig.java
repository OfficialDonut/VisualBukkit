package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Saves the plugin config")
@Category(StatementCategory.IO)
public class StatSavePluginConfig extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("save plugin config");
    }

    @Override
    public String toJava() {
        return "saveConfig();";
    }
}
