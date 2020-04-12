package the.flash.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import the.flash.protocol.request.MessageRequestPacket;
import the.flash.protocol.response.MessageResponsePacket;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {//只有 发消息的包 能进来, 并且不用我写往下传

    /**
     * 这里的逻辑只是 简单的 把客户端发来的消息 原样包成 消息响应包 发回去
     * @param ctx
     * @param messageRequestPacket
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");

        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
