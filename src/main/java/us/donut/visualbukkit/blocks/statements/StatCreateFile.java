package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.io.File;

@Description("Creates a file")
@Category(StatementCategory.IO)
public class StatCreateFile extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("create", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".createNewFile();";
    }
}
