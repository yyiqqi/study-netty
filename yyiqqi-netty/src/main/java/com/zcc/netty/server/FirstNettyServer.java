package com.zcc.netty.server;

import com.zcc.netty.handler.ServerHandler;
import com.zcc.netty.msgpack.MsgPackDecode;
import com.zcc.netty.msgpack.MsgPackEncode;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 第一个netty服务端实例
 * @author zcc
 *
 * @date 2017年11月9日
 */
public class FirstNettyServer {
	
	//端口
	private final static int SERVER_PORT = 1001;
	
	//启动服务端
	public void start() throws InterruptedException{
		
		//看到bootstrap大家都知道，这是一个引导类，如tomcat的bootstrap，在此处ServerBootstrap是的netty服务端配置与引导类
		ServerBootstrap bootstrap = new ServerBootstrap();
		
		//IO事件处理线程工作组，如建立连接事件accept，读事件read
		//bossGroup用来接收到来的连接，workerGroup用来处理已经被接收的连接
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		//设置事件处理线程组
		bootstrap.group(bossGroup, workerGroup);
		
		//指定server对应的channel类型，netty会通过channel工厂，利用反射来创建对应的channel，也可以配置指定的channel工厂
		//NioServerSocketChannel实现了java nio的ServerSocketChannel
		bootstrap.channel(NioServerSocketChannel.class);
		
		//服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，
		//服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
		bootstrap.option(ChannelOption.SO_BACKLOG, 100);
		
		//当读写等事件到达后，调用该handler进行业务逻辑处理
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
//				ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
//				ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.buffer(16).writeBytes("$".getBytes())));
//				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new MsgPackDecode());
				ch.pipeline().addLast(new MsgPackEncode());
				ch.pipeline().addLast(new ServerHandler());
			}
		});
		
		//绑定端口,启动服务
		ChannelFuture channelFuture = bootstrap.bind(SERVER_PORT).sync();
		
		System.out.println("server start ......");
		
		//server socket关闭时关闭channel
		channelFuture.channel().closeFuture().sync();
		System.out.println("server end ......");
	}
	

}
