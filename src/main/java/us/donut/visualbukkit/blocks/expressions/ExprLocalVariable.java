package us.donut.visualbukkit.blocks.expressions;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.InputParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;

import java.nio.charset.StandardCharsets;

@Description({"A local variable", "Returns: object"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE, ModificationType.CLEAR})
@SuppressWarnings("UnstableApiUsage")
public class ExprLocalVariable extends ExpressionBlock<Object> {

    private static HashFunction hashFunction = Hashing.md5();

    @Override
    protected Syntax init() {
        getStyleClass().clear();
        InputParameter inputParameter = new InputParameter();
        inputParameter.setStyle("-fx-background-color: yellow;");
        return new Syntax(inputParameter);
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
            case ADD:
                BuildContext.addUtilMethod(UtilMethod.ADD_TO_OBJECT);
                return modify(ModificationType.SET, "UtilMethods.addToObject(" + variable + "," + delta + ")");
            case REMOVE:
                BuildContext.addUtilMethod(UtilMethod.REMOVE_FROM_OBJECT);
                return modify(ModificationType.SET, "UtilMethods.removeFromObject(" + variable + "," + delta + ")");
            case CLEAR:
                return modify(ModificationType.SET, "null");
            default: return null;
        }
    }

    private String getVariableName() {
        return "a" + hashFunction.hashString(arg(0), StandardCharsets.UTF_8);
    }
}
