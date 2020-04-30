package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.InputParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("A comment which does not do anything")
public class StatComment extends StatementBlock {

    public StatComment() {
        getContextMenu().getItems().remove(getContextMenu().getItems().size() - 1);
        setOnContextMenuRequested(e -> {
            getContextMenu().show(this, e.getScreenX(), e.getScreenY());
            e.consume();
        });
    }

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("//", new InputParameter());
    }

    @Override
    public void onDragDrop() {
        if (isEnabled()) {
            disable();
        }
    }

    @Override
    public String toJava() {
        return "";
    }
}
