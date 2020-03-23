package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Reloads the plugin config")
public class StatReloadConfig extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("reload plugin config");
    }

    @Override
    public String toJava() {
        return "reloadConfig();";
    }
}
