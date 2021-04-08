package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

public class StatSetBlockData extends Statement {

    public StatSetBlockData() {
        super("stat-set-block-data");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.block.Block")), new ExpressionParameter(ClassInfo.BYTE)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.REFLECTION_UTIL);
            }

            @Override
            public String toJava() {
                return "ReflectionUtil.getDeclaredMethod(" + arg(0) + ".getClass(), \"setData\", byte.class).invoke(" + arg(0) + "," + arg(1) + ");";
            }
        };
    }
}
