package us.donut.visualbukkit.blocks.expressions;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.InputParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;

import java.nio.charset.StandardCharsets;

@Description({"A local variable", "Changers: set, delete, clear, add, remove", "Returns: object"})
@SuppressWarnings("UnstableApiUsage")
public class ExprLocalVariable extends ChangeableExpressionBlock<Object> {

    private static HashFunction hashFunction = Hashing.md5();

    @Override
    protected SyntaxNode init() {
        InputParameter inputParameter = new InputParameter();
        inputParameter.getStyleClass().add("local-variable");
        return new SyntaxNode(inputParameter);
    }

    @Override
    public String toJava() {
        String variable = getVariableName();
        BuildContext.addLocalVariable(variable);
        return variable;
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        String variable = getVariableName();
        BuildContext.addLocalVariable(variable);
        switch (changeType) {
            case SET: return variable + "=" + delta + ";";
            case ADD: return change(ChangeType.SET, "VariableManager.addToObject(" + variable + "," + delta + ");");
            case REMOVE: return change(ChangeType.SET, "VariableManager.removeFromObject(" + variable + "," + delta + ");");
            case DELETE: case CLEAR: return change(ChangeType.SET, "null");
            default: return null;
        }
    }

    private String getVariableName() {
        return "a" + hashFunction.hashString(arg(0), StandardCharsets.UTF_8);
    }
}
