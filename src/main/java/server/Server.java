package server;

import channel.ServerChannel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by ilya on 3/10/17.
 */
public class Server {

    private String dir;
    private int Port;
    private int cpuNumber;

    public Server(String dir, int Port, int cpuNumber){
        this.dir = dir;
        this.Port = Port;
        this.cpuNumber = cpuNumber;
    }

    public void run() throws InterruptedException{
        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            final ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void initChannel(SocketChannel ch) throws Exception{
                            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                            ch.pipeline().addLast(new ServerChannel(dir));
                            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                        }
                    });
            System.out.println(Port);
            final ChannelFuture f = server.bind(Port).sync();
            f.channel().closeFuture().sync();
            System.out.println(Port);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
