package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.List;

@Description("Sorts a list (its elements must be comparable)")
public class StatSortList extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("sort", List.class);
    }

    @Override
    public String toJava() {
        return "Collections.sort(" + arg(0) + ");";
    }
}
