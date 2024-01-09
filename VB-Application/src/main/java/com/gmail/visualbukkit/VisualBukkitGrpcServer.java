package com.gmail.visualbukkit;

import com.gmail.visualbukkit.blocks.definitions.ExprSerializedItemStack;
import com.gmail.visualbukkit.project.CopyPasteManager;
import com.gmail.visualbukkit.rpc.VisualBukkitGrpc;
import com.gmail.visualbukkit.rpc.VisualBukkitRPC;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;

public class VisualBukkitGrpcServer extends VisualBukkitGrpc.VisualBukkitImplBase {

    @Override
    public void importItemStack(VisualBukkitRPC.ImportItemStackRequest request, StreamObserver<VisualBukkitRPC.Response> responseObserver) {
        VisualBukkitApp.getLogger().info("Received ImportItemStackRequest: " + request);
        responseObserver.onNext(importItemStack(request));
        responseObserver.onCompleted();
    }

    private VisualBukkitRPC.Response importItemStack(VisualBukkitRPC.ImportItemStackRequest request) {
        Platform.runLater(() -> {
            CopyPasteManager.copyExpression(new ExprSerializedItemStack(request.getYaml()));
            VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.imported_item"));
        });
        return VisualBukkitRPC.Response.newBuilder().build();
    }
}
