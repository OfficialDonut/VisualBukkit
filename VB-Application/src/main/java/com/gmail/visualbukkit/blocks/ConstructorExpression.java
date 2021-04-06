package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
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

public class ConstructorExpression extends Expression {

    private static SetMultimap<String, ConstructorExpression> constructorExpressions = TreeMultimap.create(Comparator.naturalOrder(), Comparator.comparing(o -> o.parameterTypesString));

    private JSONObject json;
    private String parameterTypesString;
    private List<ClassInfo> parameterTypes = new ArrayList<>();

    public ConstructorExpression(JSONObject json) {
        super(json.getString("id"), ClassInfo.of(json.getString("class")));
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
        constructorExpressions.put(getTitle(), this);
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this, parameterTypes.stream().map(ExpressionParameter::new).toArray(ExpressionParameter[]::new)) {
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
                for (BlockParameter parameter : getParameters()) {
                    parameterJoiner.add(parameter.toJava());
                }
                return "new " + ClassInfo.of(json.getString("class")).getCanonicalClassName() + "(" + parameterJoiner + ")";
            }
        };

        Set<ConstructorExpression> overloadedConstructors = constructorExpressions.get(getTitle());
        if (overloadedConstructors.size() > 1) {
            Menu variantMenu = new Menu(VisualBukkitApp.getString("context_menu.select_variant"));
            for (ConstructorExpression expression : overloadedConstructors) {
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
