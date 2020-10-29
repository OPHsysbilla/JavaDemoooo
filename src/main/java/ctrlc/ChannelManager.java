package ctrlc;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import static ctrlc.InsertClient.*;

/**
 * @author yin.jianfeng
 * @date 2020/10/23
 */
public class ChannelManager {

    public static Channel[] initChannel(int num) {
        NioEventLoopGroup group = new NioEventLoopGroup(NETTY_THREAD_NUM);
        Bootstrap b = new Bootstrap();

        b.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("handler", new ChannelDuplexHandler() {

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ((ByteBuf) msg).release();
                    }

                    @Override
                    public void read(ChannelHandlerContext ctx) throws Exception {
                        super.read(ctx);
                    }

                    @Override
                    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                        super.disconnect(ctx, promise);
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        super.exceptionCaught(ctx, cause);
                    }

                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

                        ctx.writeAndFlush(msg, promise);
                    }
                });
            }
        });
        b.option(ChannelOption.TCP_NODELAY, true);
        b.remoteAddress(HOST, PORT);

        Channel[] channels = new Channel[num];
        for (int i = 0; i < num; i++) {
            try {
                ChannelFuture future = b.connect();
                future.sync();
                channels[i] = future.channel();
                //todo channel 异常断开重连
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return channels;
    }
}
