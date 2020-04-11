package the.flash.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import the.flash.protocol.request.LoginRequestPacket;
import the.flash.protocol.response.LoginResponsePacket;

import java.util.Date;

/**
 * 继承这个SimpleXXX
 * 就只有类型符合 <这个类型> 才会进来
 * 也不用往下传了
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        
        System.out.println(new Date() + ": 收到客户端登录请求……");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date() + ": 登录成功!");
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }

        //往里面放的是 java对象
        // 登录响应
        ctx.channel().writeAndFlush(loginResponsePacket);
        //不用写往后传了 自动的
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
