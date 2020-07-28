package us.donut.visualbukkit.blocks.statements;

import javafx.event.Event;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

public class StatSet extends ModifierBlock {

    private ExpressionParameter deltaParameter;

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode();
    }

    @Override
    public void init(ExpressionBlockInfo<?> expressionBlockInfo) {
        super.init(expressionBlockInfo);
        deltaParameter = new ExpressionParameter(expressionBlock.getDeltaType(ModificationType.SET));
        ExpressionParameter expressionParameter = new ExpressionParameter(Object.class);
        expressionParameter.setOnDragOver(Event::consume);
        expressionParameter.setExpression(expressionBlock);
        getSyntaxNode().add("set", expressionParameter, "to", deltaParameter);
    }

    @Override
    public String toJava() {
        ExpressionBlock<?> deltaExpr = deltaParameter.getExpression();
        String delta = TypeHandler.convert(deltaExpr.getReturnType(), expressionBlock.getDeltaType(ModificationType.SET), deltaExpr.toJava());
        return expressionBlock.modify(ModificationType.SET, delta);
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        BlockInfo<?> expressionBlockInfo = BlockRegistry.getInfo(section.getString("parameters.0.block-type"));
        if (expressionBlockInfo instanceof ExpressionBlockInfo) {
            init((ExpressionBlockInfo<?>) expressionBlockInfo);
            expressionBlock.load(section.getConfigurationSection("parameters.0"));
            deltaParameter.load(section.getConfigurationSection("parameters.1"));
        } else {
            throw new IllegalStateException();
        }
    }
}
