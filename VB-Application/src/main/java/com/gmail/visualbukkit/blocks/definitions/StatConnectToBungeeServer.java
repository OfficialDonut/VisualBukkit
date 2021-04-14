package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

public class StatConnectToBungeeServer extends Statement {

    public StatConnectToBungeeServer() {
        super("stat-connect-to-bungee-server");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.entity.Player")), new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.BUNGEE);
                buildContext.addUtilMethod(CONNECT_METHOD);
            }

            @Override
            public String toJava() {
                return "PluginMain.connectToServer(" + arg(0) + "," + arg(1) + ");";
            }
        };
    }

    private static final String CONNECT_METHOD =
            "public static void connectToServer(org.bukkit.entity.Player player, String server) {" +
            "    com.google.common.io.ByteArrayDataOutput out = com.google.common.io.ByteStreams.newDataOutput();" +
            "    out.writeUTF(\"Connect\");" +
            "    out.writeUTF(server);" +
            "    player.sendPluginMessage(PluginMain.getInstance(), \"BungeeCord\", out.toByteArray());" +
            "}";
}
