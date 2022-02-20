package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.project.BuildContext;

public class StatCloseDatabaseConnection extends Statement {
    public StatCloseDatabaseConnection() {
        super("stat-close-database-connection", "Close Database Connection", "SQL", "Closes this connection.\n\nIf the connection is already closed then invoking this method has no effect. After a connection is closed, any further attempt calls to readPacket or writePacket will throw a ClosedConnectionException. Any thread currently blocked in an I/O operation (readPacket or writePacket) will throw a ClosedConnectionException).\n\nThis method may be invoked at any time. If some other thread has already invoked it, however, then another invocation will block until the first invocation is complete, after which it will return without effect.");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForContainer(StatOpenDatabaseConnection.class);
            }

            @Override
            public String toJava() {
                return "connection.close();";
            }
        };
    }
}
