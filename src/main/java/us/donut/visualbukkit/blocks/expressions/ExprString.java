package us.donut.visualbukkit.blocks.expressions;

import javafx.event.Event;
import javafx.scene.input.ContextMenuEvent;
import org.apache.commons.lang.StringEscapeUtils;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.InputParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"A custom string", "Returns: string"})
public class ExprString extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        InputParameter inputParameter = new InputParameter();
        inputParameter.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, e -> {
            Event.fireEvent(this, e);
            e.consume();
        });
        return new SyntaxNode(inputParameter);
    }

    @Override
    public String toJava() {
        return "\"" + StringEscapeUtils.escapeJava(arg(0)) + "\"";
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
