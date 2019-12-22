package top.alphaship.basics.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 打印系统时间的客户端启动类
 */
public class TimeClientApp {

    private int port;

    public TimeClientApp(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap sb = new Bootstrap();
            sb.group(workerLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = sb.connect("localhost", 8081).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new TimeClientApp(8082).run();
    }
}
