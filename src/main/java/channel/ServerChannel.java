package channel;

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
        try {
            System.out.println("ahahahahahahahah");
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("ahahahahahahahah");
    }

}
