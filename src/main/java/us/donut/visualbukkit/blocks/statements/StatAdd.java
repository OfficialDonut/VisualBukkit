package us.donut.visualbukkit.blocks.statements;

import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

public class StatAdd extends ModifierBlock {

    private ExpressionParameter deltaParameter;

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode();
    }

    @Override
    public void init(ExpressionBlockInfo<?> expressionBlockInfo) {
        super.init(expressionBlockInfo);
        deltaParameter = new ExpressionParameter(expressionBlock.getDeltaType(ModificationType.ADD));
        ExpressionParameter expressionParameter = new ExpressionParameter(Object.class);
        expressionParameter.setExpression(expressionBlock);
        getSyntaxNode().add("add", deltaParameter, "to", expressionParameter);
    }

    @Override
    public String toJava() {
        ExpressionBlock<?> deltaExpr = deltaParameter.getExpression();
        String delta = TypeHandler.convert(deltaExpr.getReturnType(), expressionBlock.getDeltaType(ModificationType.ADD), deltaExpr.toJava());
        return expressionBlock.modify(ModificationType.ADD, delta);
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        BlockInfo<?> expressionBlockInfo = BlockRegistry.getInfo(section.getString("parameters.1.block-type"));
        if (expressionBlockInfo instanceof ExpressionBlockInfo) {
            init((ExpressionBlockInfo<?>) expressionBlockInfo);
            deltaParameter.load(section.getConfigurationSection("parameters.0"));
            expressionBlock.load(section.getConfigurationSection("parameters.1"));
        } else {
            throw new IllegalStateException();
        }
    }
}
