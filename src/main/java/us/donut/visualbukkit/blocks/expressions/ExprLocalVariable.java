package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.InputParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Description({"A local variable", "Changers: set, delete, clear, add, remove", "Returns: object"})
public class ExprLocalVariable extends ChangeableExpressionBlock<Object> {

    @Override
    protected SyntaxNode init() {
        InputParameter inputParameter = new InputParameter();
        inputParameter.getStyleClass().add("local-variable");
        return new SyntaxNode(inputParameter);
    }

    @Override
    public String toJava() {
        return "VariableManager.getLocalVarValue(localVarScope," + getVariable() + ")";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case SET: return "VariableManager.setLocalVarValue(localVarScope," + getVariable() + "," + delta + ");";
            case ADD: return "VariableManager.addToLocalVar(localVarScope," + getVariable() + "," + delta + ");";
            case REMOVE: return "VariableManager.removeFromLocalVar(localVarScope," + getVariable() + "," + delta + ");";
            case DELETE: case CLEAR: return "VariableManager.deleteLocalVar(localVarScope," + getVariable() + ");" ;
            default: return null;
        }
    }

    private String getVariable() {
        String encodedString = Base64.getEncoder().encodeToString(arg(0).getBytes(StandardCharsets.UTF_8));
        return "PluginMain.decode(\"" + encodedString + "\")";
    }
}
