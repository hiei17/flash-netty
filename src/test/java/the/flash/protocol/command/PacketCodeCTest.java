package the.flash.protocol.command;

import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;
import the.flash.serialize.Serializer;
import the.flash.serialize.impl.JSONSerializer;

public class PacketCodeCTest {
    @Test
    public void encode() {
        //自己写的对象 
        LoginRequestPacket loginRequestPacket = createObject();
        
        //对象编码 再解码 看看对不对
        Serializer serializer = new JSONSerializer();
        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }

    private LoginRequestPacket createObject() {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId(123);
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");
        return loginRequestPacket;
    }
}
