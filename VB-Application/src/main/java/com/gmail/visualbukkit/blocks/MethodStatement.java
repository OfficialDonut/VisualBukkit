package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

public class MethodStatement extends Statement {

    private static SetMultimap<String, MethodStatement> methodStatements = HashMultimap.create();

    private JSONObject json;
    private String parameterTypesString;
    private List<ClassInfo> parameterTypes = new ArrayList<>();

    public MethodStatement(JSONObject json) {
        super(json.getString("id"));
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
        parameterTypesString = parameterJoiner.toString();
        methodStatements.put(getTitle(), this);
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
                return java + "." + json.getString("method") + "(" + parameterJoiner + ");";
            }
        };

        Set<MethodStatement> overloadedMethods = methodStatements.get(getTitle());
        if (overloadedMethods.size() > 1) {
            Menu variantMenu = new Menu(VisualBukkitApp.getString("context_menu.select_variant"));
            for (MethodStatement statement : overloadedMethods) {
                MenuItem variantItem = new MenuItem(statement.parameterTypesString);
                variantMenu.getItems().add(variantItem);
                variantItem.setOnAction(e -> {
                    StatementConnector connector = block.getPrevious();
                    UndoManager.RevertableAction deleteAction = block.delete();
                    UndoManager.run(new UndoManager.RevertableAction() {
                        @Override
                        public void run() {
                            deleteAction.run();
                            connector.connect(statement.createBlock()).run();
                        }
                        @Override
                        public void revert() {
                            connector.getConnected().delete().run();
                            deleteAction.revert();
                        }
                    });
                });
            }
            block.getContextMenu().getItems().add(0, new SeparatorMenuItem());
            block.getContextMenu().getItems().add(0, variantMenu);
        }

        return block;
    }
}
