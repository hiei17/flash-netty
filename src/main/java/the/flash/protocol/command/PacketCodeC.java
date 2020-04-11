package the.flash.protocol.command;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import the.flash.serialize.Serializer;
import the.flash.serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import static the.flash.protocol.command.Command.LOGIN_REQUEST;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }

    /**
     * 编码对象  对象--->ByteBuf
     * @param packet 对象
     * @return ByteBuf可netty发送
     */
    public ByteBuf encode(Packet packet) {
        
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        
        // 2. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);//魔术对协议
        byteBuf.writeByte(Packet.getVersion());//为以后升级协议做准备
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());//这个序列化类型
        byteBuf.writeByte(packet.getCommand());//这个数据类型
        byteBuf.writeInt(bytes.length);//要发这么长
        byteBuf.writeBytes(bytes);//数据本体

        return byteBuf;
    }


    public Packet decode(ByteBuf byteBuf) {
        
        // 跳过 magic number 一个int 4字节这里暂时不验了
        byteBuf.skipBytes(4);

        // 跳过版本号 现在都是1  不验了
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令(什么类型数据)
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();
        
        //消息体读到这个数组
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        //指令--类  序列化算法码---序列化工具类  这2个都是map之前放好的
        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            Packet packet = serializer.deserialize(requestType, bytes);
            return packet;
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
