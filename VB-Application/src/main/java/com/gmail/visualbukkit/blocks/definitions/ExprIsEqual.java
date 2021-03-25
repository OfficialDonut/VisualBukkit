package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;

public class ExprIsEqual extends Expression {

    public ExprIsEqual() {
        super("expr-is-equal", boolean.class);
    }

    @Override
    public Block createBlock() {
        ExpressionParameter expr1 = new ExpressionParameter(Object.class);
        ExpressionParameter expr2 = new ExpressionParameter(Object.class);

        Block block = new Block(this, expr1, expr2) {
            @Override
            public void update() {
                super.update();
                if (expr1.getExpression() != null && expr2.getExpression() != null) {
                    Class<?> type1 = expr1.getExpression().getDefinition().getReturnType();
                    Class<?> type2 = expr2.getExpression().getDefinition().getReturnType();
                    if (!type1.isAssignableFrom(type2) && !type2.isAssignableFrom(type1)) {
                        setInvalid(VisualBukkitApp.getString("error.invalid_equals"));
                    }
                }
            }

            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addUtilMethod(EQUALS_METHOD);
            }

            @Override
            public String toJava() {
                return "PluginMain.checkEquals(" + arg(0) + "," + arg(1) + ")";
            }
        };

        expr1.getChildren().addListener((ListChangeListener<Node>) c -> block.update());
        expr2.getChildren().addListener((ListChangeListener<Node>) c -> block.update());

        return block;
    }

    private static final String EQUALS_METHOD =
            "public static boolean checkEquals(Object o1, Object o2) {\n" +
            "    if (o1 == null || o2 == null) {\n" +
            "        return false;\n" +
            "    }\n" +
            "    return o1 instanceof Number && o2 instanceof Number ? ((Number) o1).doubleValue() == ((Number) o2).doubleValue() : o1.equals(o2);\n" +
            "}";
}
