package com.gmail.visualbukkit.blocks.definitions.bungee;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "stat-bungeecord-connect", name = "BungeeCord Connect", description = "Connects a player to a BungeeCord server")
public class StatBungeeCordConnect extends StatementBlock {

    public StatBungeeCordConnect() {
        addParameter("Player", new ExpressionParameter(ClassInfo.of("org.bukkit.entity.Player")));
        addParameter("Server", new ExpressionParameter(ClassInfo.of(String.class)));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        if (buildInfo.getMetadata().putIfAbsent("bungeecordConnect()", true) == null) {
            buildInfo.getMainClass().addMethod(
                    """
                       public static void bungeecordConnect(org.bukkit.entity.Player player, String server) {
                           com.google.common.io.ByteArrayDataOutput out = com.google.common.io.ByteStreams.newDataOutput();
                           out.writeUTF("Connect");
                           out.writeUTF(server);
                           player.sendPluginMessage(PluginMain.getInstance(), "BungeeCord", out.toByteArray());
                      }
                    """);
        }
        return "PluginMain.bungeecordConnect(" + arg(0, buildInfo) + "," + arg(1, buildInfo) + ");";
    }
}
