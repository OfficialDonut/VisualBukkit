package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Saves the plugin config")
public class StatSaveConfig extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("save plugin config");
    }

    @Override
    public String toJava() {
        return "saveConfig();";
    }
}
