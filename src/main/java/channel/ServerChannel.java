package channel;

import Http.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by ilya on 3/11/17.
 */
public class ServerChannel extends ChannelInboundHandlerAdapter {

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
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("ahahahahahahahah");
    }

}
