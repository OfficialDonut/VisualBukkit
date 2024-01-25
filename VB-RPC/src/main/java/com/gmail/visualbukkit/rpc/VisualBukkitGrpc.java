package com.gmail.visualbukkit.rpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.61.0)",
    comments = "Source: VisualBukkitRPC.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class VisualBukkitGrpc {

  private VisualBukkitGrpc() {}

  public static final java.lang.String SERVICE_NAME = "VisualBukkit";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.PingRequest,
      com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getPingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Ping",
      requestType = com.gmail.visualbukkit.rpc.VisualBukkitRPC.PingRequest.class,
      responseType = com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.PingRequest,
      com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getPingMethod() {
    io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.PingRequest, com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getPingMethod;
    if ((getPingMethod = VisualBukkitGrpc.getPingMethod) == null) {
      synchronized (VisualBukkitGrpc.class) {
        if ((getPingMethod = VisualBukkitGrpc.getPingMethod) == null) {
          VisualBukkitGrpc.getPingMethod = getPingMethod =
              io.grpc.MethodDescriptor.<com.gmail.visualbukkit.rpc.VisualBukkitRPC.PingRequest, com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Ping"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gmail.visualbukkit.rpc.VisualBukkitRPC.PingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response.getDefaultInstance()))
              .setSchemaDescriptor(new VisualBukkitMethodDescriptorSupplier("Ping"))
              .build();
        }
      }
    }
    return getPingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportItemStackRequest,
      com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getImportItemStackMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ImportItemStack",
      requestType = com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportItemStackRequest.class,
      responseType = com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportItemStackRequest,
      com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getImportItemStackMethod() {
    io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportItemStackRequest, com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getImportItemStackMethod;
    if ((getImportItemStackMethod = VisualBukkitGrpc.getImportItemStackMethod) == null) {
      synchronized (VisualBukkitGrpc.class) {
        if ((getImportItemStackMethod = VisualBukkitGrpc.getImportItemStackMethod) == null) {
          VisualBukkitGrpc.getImportItemStackMethod = getImportItemStackMethod =
              io.grpc.MethodDescriptor.<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportItemStackRequest, com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ImportItemStack"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportItemStackRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response.getDefaultInstance()))
              .setSchemaDescriptor(new VisualBukkitMethodDescriptorSupplier("ImportItemStack"))
              .build();
        }
      }
    }
    return getImportItemStackMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportLocationRequest,
      com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getImportLocationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ImportLocation",
      requestType = com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportLocationRequest.class,
      responseType = com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportLocationRequest,
      com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getImportLocationMethod() {
    io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportLocationRequest, com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getImportLocationMethod;
    if ((getImportLocationMethod = VisualBukkitGrpc.getImportLocationMethod) == null) {
      synchronized (VisualBukkitGrpc.class) {
        if ((getImportLocationMethod = VisualBukkitGrpc.getImportLocationMethod) == null) {
          VisualBukkitGrpc.getImportLocationMethod = getImportLocationMethod =
              io.grpc.MethodDescriptor.<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportLocationRequest, com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ImportLocation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportLocationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response.getDefaultInstance()))
              .setSchemaDescriptor(new VisualBukkitMethodDescriptorSupplier("ImportLocation"))
              .build();
        }
      }
    }
    return getImportLocationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportInventoryRequest,
      com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getImportInventoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ImportInventory",
      requestType = com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportInventoryRequest.class,
      responseType = com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportInventoryRequest,
      com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getImportInventoryMethod() {
    io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportInventoryRequest, com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getImportInventoryMethod;
    if ((getImportInventoryMethod = VisualBukkitGrpc.getImportInventoryMethod) == null) {
      synchronized (VisualBukkitGrpc.class) {
        if ((getImportInventoryMethod = VisualBukkitGrpc.getImportInventoryMethod) == null) {
          VisualBukkitGrpc.getImportInventoryMethod = getImportInventoryMethod =
              io.grpc.MethodDescriptor.<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportInventoryRequest, com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ImportInventory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportInventoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response.getDefaultInstance()))
              .setSchemaDescriptor(new VisualBukkitMethodDescriptorSupplier("ImportInventory"))
              .build();
        }
      }
    }
    return getImportInventoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ReportExceptionRequest,
      com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getReportExceptionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReportException",
      requestType = com.gmail.visualbukkit.rpc.VisualBukkitRPC.ReportExceptionRequest.class,
      responseType = com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ReportExceptionRequest,
      com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getReportExceptionMethod() {
    io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ReportExceptionRequest, com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> getReportExceptionMethod;
    if ((getReportExceptionMethod = VisualBukkitGrpc.getReportExceptionMethod) == null) {
      synchronized (VisualBukkitGrpc.class) {
        if ((getReportExceptionMethod = VisualBukkitGrpc.getReportExceptionMethod) == null) {
          VisualBukkitGrpc.getReportExceptionMethod = getReportExceptionMethod =
              io.grpc.MethodDescriptor.<com.gmail.visualbukkit.rpc.VisualBukkitRPC.ReportExceptionRequest, com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReportException"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gmail.visualbukkit.rpc.VisualBukkitRPC.ReportExceptionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response.getDefaultInstance()))
              .setSchemaDescriptor(new VisualBukkitMethodDescriptorSupplier("ReportException"))
              .build();
        }
      }
    }
    return getReportExceptionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.DeployPluginRequest,
      com.gmail.visualbukkit.rpc.VisualBukkitRPC.JarFile> getDeployPluginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeployPlugin",
      requestType = com.gmail.visualbukkit.rpc.VisualBukkitRPC.DeployPluginRequest.class,
      responseType = com.gmail.visualbukkit.rpc.VisualBukkitRPC.JarFile.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.DeployPluginRequest,
      com.gmail.visualbukkit.rpc.VisualBukkitRPC.JarFile> getDeployPluginMethod() {
    io.grpc.MethodDescriptor<com.gmail.visualbukkit.rpc.VisualBukkitRPC.DeployPluginRequest, com.gmail.visualbukkit.rpc.VisualBukkitRPC.JarFile> getDeployPluginMethod;
    if ((getDeployPluginMethod = VisualBukkitGrpc.getDeployPluginMethod) == null) {
      synchronized (VisualBukkitGrpc.class) {
        if ((getDeployPluginMethod = VisualBukkitGrpc.getDeployPluginMethod) == null) {
          VisualBukkitGrpc.getDeployPluginMethod = getDeployPluginMethod =
              io.grpc.MethodDescriptor.<com.gmail.visualbukkit.rpc.VisualBukkitRPC.DeployPluginRequest, com.gmail.visualbukkit.rpc.VisualBukkitRPC.JarFile>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeployPlugin"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gmail.visualbukkit.rpc.VisualBukkitRPC.DeployPluginRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gmail.visualbukkit.rpc.VisualBukkitRPC.JarFile.getDefaultInstance()))
              .setSchemaDescriptor(new VisualBukkitMethodDescriptorSupplier("DeployPlugin"))
              .build();
        }
      }
    }
    return getDeployPluginMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static VisualBukkitStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VisualBukkitStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VisualBukkitStub>() {
        @java.lang.Override
        public VisualBukkitStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VisualBukkitStub(channel, callOptions);
        }
      };
    return VisualBukkitStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VisualBukkitBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VisualBukkitBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VisualBukkitBlockingStub>() {
        @java.lang.Override
        public VisualBukkitBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VisualBukkitBlockingStub(channel, callOptions);
        }
      };
    return VisualBukkitBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static VisualBukkitFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VisualBukkitFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VisualBukkitFutureStub>() {
        @java.lang.Override
        public VisualBukkitFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VisualBukkitFutureStub(channel, callOptions);
        }
      };
    return VisualBukkitFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void ping(com.gmail.visualbukkit.rpc.VisualBukkitRPC.PingRequest request,
        io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPingMethod(), responseObserver);
    }

    /**
     */
    default void importItemStack(com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportItemStackRequest request,
        io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getImportItemStackMethod(), responseObserver);
    }

    /**
     */
    default void importLocation(com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportLocationRequest request,
        io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getImportLocationMethod(), responseObserver);
    }

    /**
     */
    default void importInventory(com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportInventoryRequest request,
        io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getImportInventoryMethod(), responseObserver);
    }

    /**
     */
    default void reportException(com.gmail.visualbukkit.rpc.VisualBukkitRPC.ReportExceptionRequest request,
        io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReportExceptionMethod(), responseObserver);
    }

    /**
     */
    default void deployPlugin(com.gmail.visualbukkit.rpc.VisualBukkitRPC.DeployPluginRequest request,
        io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.JarFile> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeployPluginMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service VisualBukkit.
   */
  public static abstract class VisualBukkitImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return VisualBukkitGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service VisualBukkit.
   */
  public static final class VisualBukkitStub
      extends io.grpc.stub.AbstractAsyncStub<VisualBukkitStub> {
    private VisualBukkitStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VisualBukkitStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VisualBukkitStub(channel, callOptions);
    }

    /**
     */
    public void ping(com.gmail.visualbukkit.rpc.VisualBukkitRPC.PingRequest request,
        io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void importItemStack(com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportItemStackRequest request,
        io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getImportItemStackMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void importLocation(com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportLocationRequest request,
        io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getImportLocationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void importInventory(com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportInventoryRequest request,
        io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getImportInventoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void reportException(com.gmail.visualbukkit.rpc.VisualBukkitRPC.ReportExceptionRequest request,
        io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReportExceptionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deployPlugin(com.gmail.visualbukkit.rpc.VisualBukkitRPC.DeployPluginRequest request,
        io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.JarFile> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getDeployPluginMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service VisualBukkit.
   */
  public static final class VisualBukkitBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<VisualBukkitBlockingStub> {
    private VisualBukkitBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VisualBukkitBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VisualBukkitBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response ping(com.gmail.visualbukkit.rpc.VisualBukkitRPC.PingRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPingMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response importItemStack(com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportItemStackRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getImportItemStackMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response importLocation(com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportLocationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getImportLocationMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response importInventory(com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportInventoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getImportInventoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response reportException(com.gmail.visualbukkit.rpc.VisualBukkitRPC.ReportExceptionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReportExceptionMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.gmail.visualbukkit.rpc.VisualBukkitRPC.JarFile> deployPlugin(
        com.gmail.visualbukkit.rpc.VisualBukkitRPC.DeployPluginRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getDeployPluginMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service VisualBukkit.
   */
  public static final class VisualBukkitFutureStub
      extends io.grpc.stub.AbstractFutureStub<VisualBukkitFutureStub> {
    private VisualBukkitFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VisualBukkitFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VisualBukkitFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> ping(
        com.gmail.visualbukkit.rpc.VisualBukkitRPC.PingRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> importItemStack(
        com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportItemStackRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getImportItemStackMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> importLocation(
        com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportLocationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getImportLocationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> importInventory(
        com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportInventoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getImportInventoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response> reportException(
        com.gmail.visualbukkit.rpc.VisualBukkitRPC.ReportExceptionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReportExceptionMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PING = 0;
  private static final int METHODID_IMPORT_ITEM_STACK = 1;
  private static final int METHODID_IMPORT_LOCATION = 2;
  private static final int METHODID_IMPORT_INVENTORY = 3;
  private static final int METHODID_REPORT_EXCEPTION = 4;
  private static final int METHODID_DEPLOY_PLUGIN = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PING:
          serviceImpl.ping((com.gmail.visualbukkit.rpc.VisualBukkitRPC.PingRequest) request,
              (io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>) responseObserver);
          break;
        case METHODID_IMPORT_ITEM_STACK:
          serviceImpl.importItemStack((com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportItemStackRequest) request,
              (io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>) responseObserver);
          break;
        case METHODID_IMPORT_LOCATION:
          serviceImpl.importLocation((com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportLocationRequest) request,
              (io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>) responseObserver);
          break;
        case METHODID_IMPORT_INVENTORY:
          serviceImpl.importInventory((com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportInventoryRequest) request,
              (io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>) responseObserver);
          break;
        case METHODID_REPORT_EXCEPTION:
          serviceImpl.reportException((com.gmail.visualbukkit.rpc.VisualBukkitRPC.ReportExceptionRequest) request,
              (io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>) responseObserver);
          break;
        case METHODID_DEPLOY_PLUGIN:
          serviceImpl.deployPlugin((com.gmail.visualbukkit.rpc.VisualBukkitRPC.DeployPluginRequest) request,
              (io.grpc.stub.StreamObserver<com.gmail.visualbukkit.rpc.VisualBukkitRPC.JarFile>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getPingMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.gmail.visualbukkit.rpc.VisualBukkitRPC.PingRequest,
              com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>(
                service, METHODID_PING)))
        .addMethod(
          getImportItemStackMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportItemStackRequest,
              com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>(
                service, METHODID_IMPORT_ITEM_STACK)))
        .addMethod(
          getImportLocationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportLocationRequest,
              com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>(
                service, METHODID_IMPORT_LOCATION)))
        .addMethod(
          getImportInventoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.gmail.visualbukkit.rpc.VisualBukkitRPC.ImportInventoryRequest,
              com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>(
                service, METHODID_IMPORT_INVENTORY)))
        .addMethod(
          getReportExceptionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.gmail.visualbukkit.rpc.VisualBukkitRPC.ReportExceptionRequest,
              com.gmail.visualbukkit.rpc.VisualBukkitRPC.Response>(
                service, METHODID_REPORT_EXCEPTION)))
        .addMethod(
          getDeployPluginMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.gmail.visualbukkit.rpc.VisualBukkitRPC.DeployPluginRequest,
              com.gmail.visualbukkit.rpc.VisualBukkitRPC.JarFile>(
                service, METHODID_DEPLOY_PLUGIN)))
        .build();
  }

  private static abstract class VisualBukkitBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    VisualBukkitBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.gmail.visualbukkit.rpc.VisualBukkitRPC.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("VisualBukkit");
    }
  }

  private static final class VisualBukkitFileDescriptorSupplier
      extends VisualBukkitBaseDescriptorSupplier {
    VisualBukkitFileDescriptorSupplier() {}
  }

  private static final class VisualBukkitMethodDescriptorSupplier
      extends VisualBukkitBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    VisualBukkitMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (VisualBukkitGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new VisualBukkitFileDescriptorSupplier())
              .addMethod(getPingMethod())
              .addMethod(getImportItemStackMethod())
              .addMethod(getImportLocationMethod())
              .addMethod(getImportInventoryMethod())
              .addMethod(getReportExceptionMethod())
              .addMethod(getDeployPluginMethod())
              .build();
        }
      }
    }
    return result;
  }
}
