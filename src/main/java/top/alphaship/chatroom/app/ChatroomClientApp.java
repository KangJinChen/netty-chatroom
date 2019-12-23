package top.alphaship.chatroom.app;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import top.alphaship.chatroom.handler.ClientMsgHandler;
import top.alphaship.chatroom.handler.TransferMsgHandler;

/**
 * 聊天室客户端启动类
 */
public class ChatroomClientApp {

    public void run() throws InterruptedException {
        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TransferMsgHandler(), new ClientMsgHandler());
                        }
                    }).option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8888).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ChatroomClientApp().run();
    }
}
