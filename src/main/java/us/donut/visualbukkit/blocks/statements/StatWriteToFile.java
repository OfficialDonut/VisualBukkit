package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.io.File;

@Description("Writes a string to a file")
public class StatWriteToFile extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(new ChoiceParameter("write", "append"), String.class, "to", File.class);
    }

    @Override
    public String toJava() {
        String option = "new java.nio.file.OpenOption[]{java.nio.file.StandardOpenOption." + arg(0).toUpperCase() + "}";
        return "Files.write(" + arg(2) + ".toPath(),Collections.singleton(" + arg(1) + "),java.nio.charset.StandardCharsets.UTF_8," + option + ");";
    }
}
