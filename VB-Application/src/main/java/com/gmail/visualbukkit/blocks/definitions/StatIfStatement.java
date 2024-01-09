package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ContainerBlock;
import com.gmail.visualbukkit.blocks.parameters.CheckBoxParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(uid = "stat-if-statement", name = "If Statement")
public class StatIfStatement extends ContainerBlock {

    private final CheckBoxParameter modeParameter = new CheckBoxParameter("Negated");

    public StatIfStatement() {
        addParameter("Mode", modeParameter);
        addParameter("Condition", new ExpressionParameter(ClassInfo.of(boolean.class)));
    }

    @Override
    public String generateJava() {
        return "if (" + (modeParameter.isSelected() ? "!" : "") + arg(1) + ") {" + generateChildrenJava() + "}";
    }
}
