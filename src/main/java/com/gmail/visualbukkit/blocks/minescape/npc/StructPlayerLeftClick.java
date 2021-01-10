package com.gmail.visualbukkit.blocks.minescape.npc;

import com.gmail.visualbukkit.blocks.StructureBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.List;

@Category(Category.MINESCAPE)
@Description("On player left click")
public class StructPlayerLeftClick extends StructureBlock {

    public StructPlayerLeftClick() {
        init("Player left click");
    }

    @Override
    public void prepareBuild(BuildContext context) {
        MethodSource<JavaClassSource> functionMethod = context.getMainClass().getMethod("function", String.class, List.class);
        functionMethod.setBody(
                "if (function.equalsIgnoreCase(" + arg(0) + ")) {" +
                getChildJava() +
                "}" +
                functionMethod.getBody());
    }
}
