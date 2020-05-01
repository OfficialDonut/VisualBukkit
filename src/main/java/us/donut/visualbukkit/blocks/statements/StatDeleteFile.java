package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.io.File;

@Description("Deletes a file")
public class StatDeleteFile extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("delete", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".delete();";
    }
}
