package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import com.gmail.visualbukkit.ui.IconButton;
import com.gmail.visualbukkit.ui.LanguageManager;
import com.gmail.visualbukkit.ui.UndoManager;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.TreeMultimap;
import javafx.scene.control.ContextMenu;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class ConstructorExpression extends Expression {

    private static SetMultimap<String, ConstructorExpression> constructorExpressions = TreeMultimap.create(Comparator.naturalOrder(), Comparator.comparing(o -> o.parameterTypesString));

    private JSONObject json;
    private String parameterTypesString;
    private List<ClassInfo> parameterTypes;

    public ConstructorExpression(JSONObject json) {
        super(json.getString("id"));
        this.json = json;
        StringJoiner parameterJoiner = new StringJoiner(", ");
        JSONArray parameterArray = json.optJSONArray("param");
        if (parameterArray != null) {
            parameterTypes = new ArrayList<>();
            for (Object obj : parameterArray) {
                ClassInfo type = ClassInfo.of((String) obj);
                parameterTypes.add(type);
                parameterJoiner.add(type.getDisplayClassName());
            }
        }
        parameterTypesString = parameterTypes == null ? LanguageManager.get("context_menu.no_parameters") : parameterJoiner.toString();
        constructorExpressions.put(getTitle(), this);
    }

    @Override
    public ClassInfo getReturnType() {
        ClassInfo returnType = ClassInfo.of(json.getString("class"));
        return returnType.isArrayOrCollection() ? ClassInfo.of(List.class) : returnType;
    }

    @Override
    public Block createBlock() {
        ExpressionParameter[] parameters = parameterTypes != null ?
                parameterTypes.stream().map(ExpressionParameter::new).toArray(ExpressionParameter[]::new) :
                new ExpressionParameter[]{};

        Block block = new Block(this, parameters) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                PluginModule pluginModule = PluginModule.get(json.optString("module"));
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
            ContextMenu variantMenu = new ContextMenu();
            for (ConstructorExpression expression : overloadedConstructors) {
                variantMenu.getItems().add(new ActionMenuItem(expression.parameterTypesString, e -> UndoManager.run(block.getExpressionParameter().setExpression(expression.createBlock()))));
            }
            IconButton variantButton = new IconButton("list", null, null);
            variantButton.setOnMouseClicked(e -> {
                variantMenu.show(VisualBukkitApp.getScene().getWindow(), e.getScreenX(), e.getScreenY());
                VisualBukkitApp.getScene().getWindow().requestFocus();
                e.consume();
            });
            block.addToHeader(variantButton);
        }

        return block;
    }
}
