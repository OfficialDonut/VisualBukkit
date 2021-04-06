package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.TreeMultimap;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class MethodExpression extends Expression {

    private static SetMultimap<String, MethodExpression> methodExpressions = TreeMultimap.create(Comparator.naturalOrder(), Comparator.comparing(o -> o.parameterTypesString));;

    private JSONObject json;
    private String parameterTypesString;
    private List<ClassInfo> parameterTypes = new ArrayList<>();

    public MethodExpression(JSONObject json) {
        super(json.getString("id"), ClassInfo.of(json.getString("return")).isArrayOrCollection() ? ClassInfo.of(List.class) : ClassInfo.of(json.getString("return")));
        this.json = json;
        StringJoiner parameterJoiner = new StringJoiner(", ");
        JSONArray parameterArray = json.optJSONArray("parameters");
        if (parameterArray != null) {
            for (Object obj : parameterArray) {
                ClassInfo type = ClassInfo.of((String) obj);
                parameterTypes.add(type);
                parameterJoiner.add(type.getDisplayClassName());
            }
        }
        parameterTypesString = parameterJoiner.length() == 0 ? VisualBukkitApp.getString("context_menu.no_parameters") : parameterJoiner.toString();
        methodExpressions.put(getTitle(), this);
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this, parameterTypes.stream().map(ExpressionParameter::new).toArray(ExpressionParameter[]::new)) {
            @Override
            public void update() {
                super.update();
                if (json.optBoolean("event-method")) {
                    checkForEvent(ClassInfo.of(json.getString("class")));
                }
            }

            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                PluginModule pluginModule = PluginModule.get(json.optString("plugin-module"));
                if (pluginModule != null) {
                    buildContext.addPluginModule(pluginModule);
                }
            }

            @Override
            public String toJava() {
                StringJoiner parameterJoiner = new StringJoiner(",");
                for (int i = json.optBoolean("static") || json.optBoolean("event-method") ? 0 : 1; i < parameterTypes.size(); i++) {
                    parameterJoiner.add(arg(i));
                }
                String java;
                if (json.optBoolean("static")) {
                    java = ClassInfo.of(json.getString("class")).getCanonicalClassName();
                } else if (json.optBoolean("event-method")) {
                    java = "event";
                } else {
                    java = arg(0);
                }
                java += "." + json.getString("method") + "(" + parameterJoiner + ")";
                ClassInfo classInfo = ClassInfo.of(json.getString("return"));
                return classInfo.isArrayOrCollection() && (classInfo.getClazz() == null || !List.class.isAssignableFrom(classInfo.getClazz())) ? "PluginMain.createList(" + java + ")" : java;
            }
        };

        Set<MethodExpression> overloadedMethods = methodExpressions.get(getTitle());
        if (overloadedMethods.size() > 1) {
            Menu variantMenu = new Menu(VisualBukkitApp.getString("context_menu.select_variant"));
            for (MethodExpression expression : overloadedMethods) {
                MenuItem variantItem = new MenuItem(expression.parameterTypesString);
                variantMenu.getItems().add(variantItem);
                variantItem.setOnAction(e -> UndoManager.run(block.getExpressionParameter().setExpression(expression.createBlock())));
            }
            block.getContextMenu().getItems().add(0, new SeparatorMenuItem());
            block.getContextMenu().getItems().add(0, variantMenu);
        }

        return block;
    }
}
