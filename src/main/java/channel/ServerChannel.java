package channel;

import Http.Request;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import server.Server;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class ServerChannel extends ChannelInboundHandlerAdapter {

    private String serverDirectory;

    public ServerChannel(String dir) {
        serverDirectory = dir;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        final Request request = new Request((String) msg, serverDirectory);
        try {
        } finally {
            ReferenceCountUtil.release(msg);
        }
        final ChannelFuture channelfuture = ctx.writeAndFlush(Unpooled.copiedBuffer(request.getResponse().getBytes()));
        if (!request.checkPicture()) {
            final RandomAccessFile image;
            try {
                image = new RandomAccessFile(request.getPath(), "r");
                long imageLength = 0;
                try{
                    imageLength = image.length();
                } catch (Exception e){}
                ctx.writeAndFlush(new DefaultFileRegion(image.getChannel(), 0, imageLength));
            } catch (FileNotFoundException e) {}
        }
        channelfuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    }

}
