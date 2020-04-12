package the.flash.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import the.flash.protocol.PacketCodeC;

public class Spliter extends LengthFieldBasedFrameDecoder {
    
    private static final int LENGTH_FIELD_OFFSET = 7;//index 7 开始是 长度
    private static final int LENGTH_FIELD_LENGTH = 4;//表示长度的有4字节

    public Spliter() {
        //最长 是int最大
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            ctx.channel().close();//mark 直接关闭了 
            return null;
        }

        return super.decode(ctx, in);
    }
}
