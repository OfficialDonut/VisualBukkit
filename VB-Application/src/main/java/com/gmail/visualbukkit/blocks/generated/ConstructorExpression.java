package com.gmail.visualbukkit.blocks.generated;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
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

    private static SetMultimap<String, ConstructorExpression> constructorExpressions = TreeMultimap.create(Comparator.naturalOrder(), (o1, o2) -> Comparator.nullsFirst(Comparator.comparingInt((ClassInfo[] a) -> a.length).thenComparing(a -> a[0])).compare(o1.parameterTypes, o2.parameterTypes));

    private JSONObject json;
    private ClassInfo[] parameterTypes;
    private String[] parameterNames;
    private String parameterTypesString;

    public ConstructorExpression(JSONObject json) {
        super(json.getString("id"), "New " + NameUtil.formatClassName(json.getString("class")), NameUtil.formatClassName(json.getString("class")), json.optString("descr"));
        this.json = json;
        JSONArray paramTypesArr = json.optJSONArray("param-types");
        JSONArray paramNamesArr = json.optJSONArray("param-names");
        if (paramTypesArr != null && paramNamesArr != null) {
            StringJoiner parameterJoiner = new StringJoiner(", ");
            int len = paramTypesArr.length();
            parameterTypes = new ClassInfo[len];
            parameterNames = new String[len];
            for (int i = 0; i < len; i++) {
                parameterTypes[i] = ClassInfo.of(paramTypesArr.getString(i));
                parameterNames[i] = paramNamesArr.getString(i);
                parameterJoiner.add(parameterTypes[i].getDisplayClassName());
            }
            parameterTypesString = parameterJoiner.toString();
        }
        constructorExpressions.put(toString(), this);
    }

    @Override
    public ClassInfo getReturnType() {
        ClassInfo returnType = ClassInfo.of(json.getString("class"));
        return returnType.isArrayOrCollection() ? ClassInfo.of(List.class) : returnType;
    }

    @Override
    public Block createBlock() {
        ExpressionParameter[] parameters = new ExpressionParameter[parameterTypes != null ? parameterTypes.length : 0];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = new ExpressionParameter(parameterNames[i], parameterTypes[i]);
        }

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
                for (BlockParameter<?> parameter : parameters) {
                    parameterJoiner.add(parameter.toJava());
                }
                return "new " + ClassInfo.of(json.getString("class")).getCanonicalClassName() + "(" + parameterJoiner + ")";
            }
        };

        Set<ConstructorExpression> variants = constructorExpressions.get(toString());
        if (variants.size() > 1) {
            ContextMenu variantMenu = new ContextMenu();
            for (ConstructorExpression variant : variants) {
                String variantString = variant.parameterTypesString == null ? LanguageManager.get("context_menu.no_parameters") : variant.parameterTypesString;
                variantMenu.getItems().add(new ActionMenuItem(variantString, e -> UndoManager.run(block.getExpressionParameter().setExpression(variant.createBlock()))));
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
