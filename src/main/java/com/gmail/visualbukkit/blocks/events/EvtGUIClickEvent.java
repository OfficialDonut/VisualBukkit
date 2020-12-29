package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import com.gmail.visualbukkit.stdlib.GUIClickEvent;

@Name("GUI Click Event")
public class EvtGUIClickEvent extends EventBlock {

    public EvtGUIClickEvent() {
        super(GUIClickEvent.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        super.prepareBuild(context);
        context.addPluginModules(PluginModule.GUI);
    }
}
