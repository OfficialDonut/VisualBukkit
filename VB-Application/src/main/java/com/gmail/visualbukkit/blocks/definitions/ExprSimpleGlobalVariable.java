package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

public class ExprSimpleGlobalVariable extends Expression {

    public ExprSimpleGlobalVariable() {
        super("expr-simple-global-variable", ClassInfo.OBJECT);
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                String varName = getVariable(arg(0));
                if (!buildContext.getMainClass().hasField(varName)) {
                    buildContext.getMainClass().addField("public static Object " + varName + ";");
                }
            }

            @Override
            public String toJava() {
                return getVariable(arg(0));
            }
        };

        InputParameter inputParameter = new InputParameter();
        inputParameter.getStyleClass().add("simple-global-variable");
        block.getHeaderBox().getChildren().clear();
        block.addToHeader(inputParameter);
        block.getHeaderBox().getStyleClass().clear();
        block.getSyntaxBox().getStyleClass().clear();

        return block;
    }

    protected static String getVariable(String string) {
        return ExprSimpleLocalVariable.getVariable(string).replace("$", "GLOBAL_");
    }
}
