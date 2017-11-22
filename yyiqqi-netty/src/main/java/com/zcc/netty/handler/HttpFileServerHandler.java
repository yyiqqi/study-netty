package com.zcc.netty.handler;

import java.util.List;

import com.zcc.po.UserInfo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 服务端事件处理类
 * @author zcc
 *
 * @date 2017年11月9日
 */
public class HttpFileServerHandler extends ChannelInboundHandlerAdapter {
	
	private static int reqCounter = 0;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Server active .........");
	}


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		System.out.println("channelRead..........");
		/*ByteBuf buf = (ByteBuf)msg;
		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		String receiveData = new String(data, "UTF-8");
		System.out.println(receiveData);*/
		
//		String message = (String)msg;
//		System.out.println("client message------" + msg + "------reqCounter------" + reqCounter++);
//		String resp = "server message------" + reqCounter + "$";
//		ctx.writeAndFlush(Unpooled.copiedBuffer(resp.getBytes()));
		
		List<UserInfo> userInfos = (List<UserInfo>)msg;
		System.out.println(userInfos.size());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//		System.out.println("Server read compltete .......");
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("Server exception .........");
		ctx.close();
	}

}
