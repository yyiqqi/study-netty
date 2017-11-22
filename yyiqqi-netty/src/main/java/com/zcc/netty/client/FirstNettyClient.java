package com.zcc.netty.client;

import java.net.InetSocketAddress;

import com.zcc.netty.handler.ClientHandler;
import com.zcc.netty.msgpack.MsgPackDecode;
import com.zcc.netty.msgpack.MsgPackEncode;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 第一个netty客户端实例
 * @author zcc
 *
 * @date 2017年11月9日
 */
public class FirstNettyClient {
	
	//端口
	private final static int SERVER_PORT = 1001;
	
	private final static String SERVER_HOST = "127.0.0.1";
	
	//启动客户端
	public void start() throws InterruptedException{
		
		//看到bootstrap大家都知道，这是一个引导类，如tomcat的bootstrap，在此处Bootstrap是的netty客户端配置与引导类
		Bootstrap bootstrap = new Bootstrap();
		
		//IO事件处理线程工作组
		EventLoopGroup group = new NioEventLoopGroup();
		
		//设置事件处理线程组
		bootstrap.group(group);
		
		//设置连接的服务器与端口
		bootstrap.remoteAddress(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
		
		bootstrap.channel(NioSocketChannel.class);
		
		//客户端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，
		//客户端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		
		//当读写等事件到达后，调用该handler进行业务逻辑处理
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
//				ch.pipeline().addLast(new LineBasedFrameDecoder(65535));
//				ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.buffer(16).writeBytes("$".getBytes())));
//				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new MsgPackDecode());
				ch.pipeline().addLast(new MsgPackEncode());
				ch.pipeline().addLast(new ClientHandler());
			}
		});
		
		//绑定端口,启动客户
		ChannelFuture channelFuture = bootstrap.connect().sync();
		
		System.out.println("client start ......");
		
		//server socket关闭时关闭channel
		channelFuture.channel().closeFuture().sync();
		System.out.println("client end ......");
	}
	
	public static void main(String[] args) throws InterruptedException {
		FirstNettyClient client = new FirstNettyClient();
		client.start();
	}
}
