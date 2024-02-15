package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import javafx.scene.layout.Region;

@BlockDefinition(id = "stat-set-local-variable", name = "Set Local Variable", description = "Sets the value of a local variable")
public class StatSetLocalVariable extends StatementBlock {

    private final VariableParameter varParameter = new VariableParameter();
    private final ExpressionParameter valueParameter = new ExpressionParameter(ClassInfo.OBJECT_OR_PRIMITIVE);

    public StatSetLocalVariable() {
        addParameter("Var", varParameter);
        addParameter("Value", valueParameter);
    }

    public StatSetLocalVariable(String var, ExpressionBlock value) {
        this();
        varParameter.inputParameter.setText(var);
        valueParameter.setExpression(value);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        buildInfo.addLocalVariable(ExprLocalVariable.getVariable(arg(0, buildInfo)));
        return ExprLocalVariable.getVariable(arg(0, buildInfo)) + " = " + arg(1, buildInfo) + ";";
    }

    private static class VariableParameter extends Region implements BlockParameter {

        private final InputParameter inputParameter = new InputParameter();

        public VariableParameter() {
            inputParameter.getStyleClass().add("local-variable-field");
            getChildren().add(inputParameter);
        }

        @Override
        public String generateJava(BuildInfo buildInfo) {
            return inputParameter.generateJava(buildInfo);
        }

        @Override
        public Object serialize() {
            return inputParameter.serialize();
        }

        @Override
        public void deserialize(Object obj) {
            inputParameter.deserialize(obj);
        }
    }
}
