package com.pj109.xkorey.share.util;

import androidx.lifecycle.MediatorLiveData;
import com.pj109.xkorey.share.data.NettyAllInOne;
import com.pj109.xkorey.share.domain.MediatorUseCase;
import com.pj109.xkorey.share.result.Result;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import timber.log.Timber;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public enum Client {

    INSTANCE;

    private final String type="APP";
    private EventLoopGroup group = new NioEventLoopGroup();
    private ProtoBufClientHandler socketHandler=null;
    private ChannelFuture f;
    private MediatorUseCase useCase;


//    public Client initSendPool(Integer threadNum) {
//        this.threadNum = threadNum;
//        threadPool = Executors.newFixedThreadPool(threadNum);
//        reqs = new ArrayBlockingQueue<>(threadNum * 2);
//        return INSTANCE;
//    }

    public ProtoBufClientHandler getSocketHandler() {
        return socketHandler;
    }

    public ProtoBufClientHandler startUp(String host, int port,MediatorUseCase useCase) {
        socketHandler = new ProtoBufClientHandler();
        Bootstrap b = new Bootstrap();
        this.useCase=useCase;
        b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ProtobufVarint32FrameDecoder());
                        pipeline.addLast(new ProtobufDecoder(NettyAllInOne.AllInone.getDefaultInstance()));
                        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                        pipeline.addLast(new ProtobufEncoder());
                        pipeline.addLast(socketHandler);
                    }

                });
        // 发起异步连接操作
        try {
            f = b.connect(host, port).sync();
        } catch (Exception e) {
            Timber.e(e);
        }
        return socketHandler;
    }

    public void close(){
        if(null!=f){
            try {
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(NettyAllInOne.AllInone data){
        if(socketHandler!=null && socketHandler.checkIsAlive()){
            socketHandler.activeChannel.writeAndFlush(data);
        }
    }

    public class ProtoBufClientHandler extends SimpleChannelInboundHandler<NettyAllInOne.AllInone> {

        ChannelHandlerContext activeChannel;

        public AtomicBoolean run = new AtomicBoolean(true);
        public AtomicBoolean connection = new AtomicBoolean(false);


        ArrayBlockingQueue<NettyAllInOne.AllInone> nettyServeResponse = new ArrayBlockingQueue<>(1);

        private AtomicBoolean havSendData = new AtomicBoolean(false);

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            activeChannel = ctx;
        }


        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.close();
            run.set(false);
            socketHandler.connection.set(false);
            try {
                nettyServeResponse.put(NettyAllInOne.AllInone.newBuilder().setType("ERROR").build());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, NettyAllInOne.AllInone msg) throws Exception {
            if (null != msg) {
                useCase.observe().postValue(new Result.Success(msg));
            }
            havSendData.set(false);
        }

        boolean checkIsAlive() {
            if (null == activeChannel) {
                return false;
            }
            if (!activeChannel.channel().isActive()) {
                return false;
            }
            return true;
        }
    }


    public boolean isRunning(){
        if(socketHandler!=null){
            return socketHandler.checkIsAlive();
        }
        return true;
    }

}
