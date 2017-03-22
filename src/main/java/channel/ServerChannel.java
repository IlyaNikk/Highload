package channel;

import Http.Request;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import server.Server;

/**
 * Created by ilya on 3/11/17.
 */
public class ServerChannel extends ChannelInboundHandlerAdapter {

    private String serverDirectory;

    public ServerChannel(String dir){
    serverDirectory = dir;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        Request request = new Request((String)msg, serverDirectory);
        try {
            System.out.println("try in channelRead");
        } finally {
            System.out.println("in finally part");
            ReferenceCountUtil.release(msg);
        }
        final ChannelFuture channelfuture = ctx.writeAndFlush(Unpooled.copiedBuffer(request.getResponse().getBytes()));
        channelfuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println(cause);
    }

}
