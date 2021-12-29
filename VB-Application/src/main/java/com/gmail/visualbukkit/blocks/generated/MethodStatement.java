package com.gmail.visualbukkit.blocks.generated;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
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

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.StringJoiner;

public class MethodStatement extends Statement {

    private static SetMultimap<String, MethodStatement> methodStatements = TreeMultimap.create(Comparator.naturalOrder(), (o1, o2) -> Comparator.nullsFirst((Comparator<ClassInfo[]>) (a1, a2) -> {
        int i = Integer.compare(a1.length, a2.length);
        return i == 0 ? Arrays.compare(a1, a2) : i;
    }).compare(o1.parameterTypes, o2.parameterTypes));

    private JSONObject json;
    private ClassInfo[] parameterTypes;
    private String[] parameterNames;
    private String parameterTypesString;

    public MethodStatement(JSONObject json) {
        super(json.getString("id"), NameUtil.formatLowerCamelCase(json.getString("method")), NameUtil.formatClassName(json.getString("class")), json.optString("descr"));
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
        methodStatements.put(toString(), this);
    }

    @Override
    public Block createBlock() {
        ExpressionParameter[] parameters = new ExpressionParameter[parameterTypes != null ? parameterTypes.length : 0];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = new ExpressionParameter(parameterNames[i], parameterTypes[i]);
        }

        Block block = new Block(this, parameters) {
            @Override
            public void update() {
                super.update();
                if (json.optBoolean("event")) {
                    checkForEvent(ClassInfo.of(json.getString("class")));
                }
            }

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
                for (int i = json.optBoolean("static") || json.optBoolean("event") ? 0 : 1; i < parameters.length; i++) {
                    parameterJoiner.add(arg(i));
                }
                String java;
                if (json.optBoolean("static")) {
                    java = ClassInfo.of(json.getString("class")).getCanonicalClassName();
                } else if (json.optBoolean("event")) {
                    java = "event";
                } else {
                    java = arg(0);
                }
                return java + "." + json.getString("method") + "(" + parameterJoiner + ");";
            }
        };

        Set<MethodStatement> variants = methodStatements.get(toString());
        if (variants.size() > 1) {
            ContextMenu variantMenu = new ContextMenu();
            for (MethodStatement variant : variants) {
                String variantString = variant.parameterTypesString == null ? LanguageManager.get("context_menu.no_parameters") : variant.parameterTypesString;
                variantMenu.getItems().add(new ActionMenuItem(variantString, e -> UndoManager.run(block.getStatementHolder().replace(block, variant.createBlock()))));
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
