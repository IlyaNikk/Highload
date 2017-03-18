package channel;

import Http.Request;
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

    public ServerChannel(){

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
//        System.out.println(ctx);
//        System.out.println(msg);
        System.out.println("enter channelRead");
        Request request = new Request((String)msg);
        try {
            System.out.println("try in channelRead");
        } finally {
            System.out.println("in finally part");
            ReferenceCountUtil.release(msg);
        }
        final ChannelFuture channelfuture = ctx.writeAndFlush(request.getResponse());
        channelfuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("ahahahahahahahah");
    }

}
