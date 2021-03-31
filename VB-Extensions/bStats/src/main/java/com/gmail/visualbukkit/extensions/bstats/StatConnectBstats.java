package com.gmail.visualbukkit.extensions.bstats;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

public class StatConnectBstats extends Statement {

    public StatConnectBstats() {
        super("connect-bstats");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.INT)) {

            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addMavenRepository(
                        "<id>CodeMC</id>" +
                        "<url>https://repo.codemc.org/repository/maven-public</url>");
                buildContext.addMavenDependency(
                        "<groupId>org.bstats</groupId>" +
                        "<artifactId>bstats-bukkit</artifactId>" +
                        "<version>1.7</version>");
            }

            @Override
            public String toJava() {
                return "new org.bstats.bukkit.Metrics(PluginMain.getInstance()," + arg(0) + ");";
            }
        };
    }
}
