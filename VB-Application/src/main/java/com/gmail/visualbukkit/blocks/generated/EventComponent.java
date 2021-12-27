package com.gmail.visualbukkit.blocks.generated;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;
import org.json.JSONObject;

public class EventComponent extends PluginComponent {

    private static String[] priorities = {"HIGH", "HIGHEST", "LOW", "LOWEST", "MONITOR", "NORMAL"};
    private JSONObject json;

    public EventComponent(JSONObject json) {
        super(json.getString("class"), NameUtil.formatClassName(json.getString("class")), "Event", json.optString("descr"));
        this.json = json;
    }

    @Override
    public Block createBlock() {
        return new Block(new ChoiceParameter("Priority", priorities));
    }

    public class Block extends PluginComponent.Block {

        public Block(ChoiceParameter priorityChoice) {
            super(EventComponent.this, priorityChoice);
        }

        @Override
        public void prepareBuild(BuildContext buildContext) {
            super.prepareBuild(buildContext);
            PluginModule module = PluginModule.get(json.optString("module"));
            if (module != null) {
                buildContext.addPluginModule(module);
            }
            buildContext.getMetadata().increment("event-number");
            buildContext.getMainClass().addMethod(
                    "@EventHandler(priority=EventPriority." + arg(0) + ")" +
                    "public void event" + buildContext.getMetadata().getInt("event-number") + "(" + json.getString("class") + " event) throws Exception {" +
                    buildContext.getLocalVariableDeclarations() +
                    getChildJava() +
                    "}");
        }

        public ClassInfo getEvent() {
            return ClassInfo.of(json.getString("class"));
        }
    }
}
