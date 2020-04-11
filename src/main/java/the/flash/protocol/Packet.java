package the.flash.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 这个协议的数据包 有很多类型 这个格式可以传很多不同类型的数据  
 * 但是
 * 版本都是一样的
 * 类型不同,但是类型字段都要有
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private static Byte version = 1;


    @JSONField(serialize = false)
    public abstract Byte getCommand();

    public static Byte getVersion() {
        return version;
    }
}
