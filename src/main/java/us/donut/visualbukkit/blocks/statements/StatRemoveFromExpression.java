package us.donut.visualbukkit.blocks.statements;

import javafx.scene.text.Text;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.util.DataConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Description("Removes from the value of an expression")
@Category(StatementCategory.MODIFIERS)
public class StatRemoveFromExpression extends StatementBlock {

    private static Predicate<ExpressionDefinition<?>> validator = expression -> expression.getModifiers().contains(ModificationType.REMOVE);
    private ExpressionParameter exprParameter;
    private ExpressionParameter deltaParameter;
    private Text placeholder;

    @Override
    protected Syntax init() {
        exprParameter = new ExpressionParameter(Object.class, validator);
        return new Syntax("remove", placeholder = new Text("<...>"), "from", exprParameter);
    }

    @Override
    public void update() {
        super.update();
        ExpressionBlock<?> expression = exprParameter.getExpression();
        if (expression != null) {
            Class<?> deltaType = expression.getDeltaType(ModificationType.REMOVE);
            if (deltaParameter == null || deltaParameter.getReturnType() != deltaType) {
                getSyntaxLine(0).getChildren().set(1, deltaParameter = new ExpressionParameter(deltaType));
            }
        } else {
            deltaParameter = null;
            getSyntaxLine(0).getChildren().set(1, placeholder);
        }
    }

    @Override
    public String toJava() {
        ExpressionBlock<?> expression = exprParameter.getExpression();
        return expression != null ? expression.modify(ModificationType.REMOVE, deltaParameter.toJava()) : "";
    }

    @Override
    public void saveTo(DataConfig config) {
        List<DataConfig> parameterConfigs = new ArrayList<>(2);
        DataConfig exprConfig = new DataConfig();
        exprParameter.saveTo(exprConfig);
        parameterConfigs.add(exprConfig);
        if (deltaParameter != null) {
            DataConfig deltaConfig = new DataConfig();
            deltaParameter.saveTo(deltaConfig);
            parameterConfigs.add(deltaConfig);
        }
        config.set("parameters", parameterConfigs);
    }


    @Override
    public void loadFrom(DataConfig config) {
        List<DataConfig> parameterConfigs = config.getConfigList("parameters");
        if (!parameterConfigs.isEmpty()) {
            exprParameter.loadFrom(parameterConfigs.get(0));
            ExpressionBlock<?> expression = exprParameter.getExpression();
            if (expression != null && parameterConfigs.size() > 1) {
                deltaParameter = new ExpressionParameter(expression.getDeltaType(ModificationType.REMOVE));
                getSyntaxLine(0).getChildren().set(1, deltaParameter);
                deltaParameter.loadFrom(parameterConfigs.get(1));
            }
        }
    }
}
