package the.flash.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import the.flash.util.LoginUtil;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        
        if (!LoginUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
            return;
        } 
        
        ctx.pipeline().remove(this);//每个channel 维护自己独立的pipeline
        
        super.channelRead(ctx, msg);//这个handle继承的是Adapter 因此需要自己写往下传
        
    }

    /**
     * ctx.pipeline().remove(this) 后会回调这里 
     * 这里就打下日志
     * @param ctx
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
            return;
        } 
        System.out.println("无登录验证，强制关闭连接!");
        
    }
}
