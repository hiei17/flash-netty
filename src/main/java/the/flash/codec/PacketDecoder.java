package the.flash.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import the.flash.protocol.Packet;
import the.flash.protocol.PacketCodeC;

import java.util.List;

/**
 * 解码器
 * ByteBuf---> 我们要的对象
 */
public class PacketDecoder extends ByteToMessageDecoder {

    /**
     * 
     * @param ctx
     * @param in 收进来的数据
     * @param out 转好add到这
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) {
        Packet packet = PacketCodeC.INSTANCE.decode(in);
        out.add(packet);
    }
}
