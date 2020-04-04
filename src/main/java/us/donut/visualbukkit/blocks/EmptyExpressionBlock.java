package us.donut.visualbukkit.blocks;

import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

public class EmptyExpressionBlock extends ChangeableExpressionBlock {

    private Class<?> returnType;

    public EmptyExpressionBlock(Class<?> returnType) {
        this.returnType = returnType;
    }

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode();
    }

    @Override
    public String toJava() {
        return "((" + returnType.getCanonicalName() + ") null)";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return getReturnType().getCanonicalName() + " " + randomVar() + "=" + delta + ";";
    }

    @Override
    public Class<?> getReturnType() {
        return returnType;
    }

    @Override
    public void unload(ConfigurationSection section) {}

    @Override
    public void load(ConfigurationSection section) {}
}
