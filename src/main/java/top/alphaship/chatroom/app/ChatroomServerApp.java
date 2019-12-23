package top.alphaship.chatroom.app;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import top.alphaship.chatroom.handler.MessageEncodeHandler;
import top.alphaship.chatroom.handler.ServerMsgHandler;
import top.alphaship.chatroom.handler.TransferMsgHandler;

/**
 * 聊天室服务端启动类
 */
public class ChatroomServerApp {

    private int port;

    public ChatroomServerApp(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossLoopGroup, workerLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new MessageEncodeHandler(), new TransferMsgHandler(), new ServerMsgHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 1024 * 10)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossLoopGroup.shutdownGracefully();
            workerLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ChatroomServerApp(8888).run();
    }
}
