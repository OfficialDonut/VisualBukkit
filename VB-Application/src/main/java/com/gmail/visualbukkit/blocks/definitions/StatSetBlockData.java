package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;

public class StatSetBlockData extends Statement {

    public StatSetBlockData() {
        super("stat-set-block-data", "Set Data", "Block", "Sets the data value of a block");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Block", ClassInfo.of("org.bukkit.block.Block")), new ExpressionParameter("Value", ClassInfo.BYTE)) {
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
