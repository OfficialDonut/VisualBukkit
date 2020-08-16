package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.io.File;

@Description("Writes a string to a file")
@Category(StatementCategory.IO)
public class StatWriteToFile extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax(new ChoiceParameter("write", "append"), String.class, "to", File.class);
    }

    @Override
    public String toJava() {
        return "Files.write(" + arg(2) + ".toPath()," +
                "Collections.singleton(" + arg(1) + ")," +
                "java.nio.charset.StandardCharsets.UTF_8," +
                "java.nio.file.StandardOpenOption." + arg(0).toUpperCase() + ");";
    }
}
