package the.flash.util;

import io.netty.channel.Channel;
import the.flash.attribute.Attributes;
import the.flash.session.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    /**
     * 1.存map
     * 2.channel里面存下session
     * @param session 就是用户信息
     * @param channel 用户是这个channel
     */
    public static void bindSession(Session session, Channel channel) {
        
        userIdChannelMap.put(session.getUserId(), channel);
        
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        
        if (hasLogin(channel)) {
            
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);//mark 作者没回答 ???
        }
    }

    /**
     * 通过channel里面是不是有存用户信息
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }
}
