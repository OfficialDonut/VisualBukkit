package us.donut.visualbukkit.blocks.expressions;

import org.apache.commons.lang.StringEscapeUtils;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.InputParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"A temporary variable", "Returns: object"})
public class ExprTempVariable extends ChangeableExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("temp variable(", new InputParameter(), ")");
    }

    @Override
    public String toJava() {
        return "tempVariables.get(" + getVariable() + ")";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case SET: return "tempVariables.put(" + getVariable() + "," + delta + ");";
            case ADD: return "addToVariable(" + getVariable() + "," + delta + ", tempVariables);";
            case REMOVE: return "removeFromVariable(" + getVariable() + "," + delta + ", tempVariables);";
            case DELETE: return "tempVariables.remove(" + getVariable() + ");" ;
            default: return null;
        }
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    private String getVariable() {
        return "\"" + StringEscapeUtils.escapeJava(arg(0)) + "\"";
    }
}
