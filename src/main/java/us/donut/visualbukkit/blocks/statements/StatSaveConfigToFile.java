package us.donut.visualbukkit.blocks.statements;

import org.bukkit.configuration.file.FileConfiguration;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.io.File;

@Description("Saves a config to a file")
public class StatSaveConfigToFile extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("save", FileConfiguration.class, "to", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".save(" + arg(1) + ");";
    }
}
