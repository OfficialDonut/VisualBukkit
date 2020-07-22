package us.donut.visualbukkit.blocks.expressions;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.InputParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;

import java.nio.charset.StandardCharsets;

@Description({"A local variable", "Returns: object"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE, ModificationType.DELETE})
@SuppressWarnings("UnstableApiUsage")
public class ExprLocalVariable extends ModifiableExpressionBlock<Object> {

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
    public String modify(ModificationType modificationType, String delta) {
        String variable = getVariableName();
        BuildContext.addLocalVariable(variable);
        switch (modificationType) {
            case SET: return variable + "=" + delta + ";";
            case ADD: return modify(ModificationType.SET, "VariableManager.addToObject(" + variable + "," + delta + ");");
            case REMOVE: return modify(ModificationType.SET, "VariableManager.removeFromObject(" + variable + "," + delta + ");");
            case DELETE: return modify(ModificationType.SET, "null");
            default: return null;
        }
    }

    private String getVariableName() {
        return "a" + hashFunction.hashString(arg(0), StandardCharsets.UTF_8);
    }
}
