package com.zcc.netty.handler;

import java.util.ArrayList;
import java.util.List;

import com.zcc.po.UserInfo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 客户端事件处理类
 * @author zcc
 *
 * @date 2017年11月9日
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client active .........");
//		byte[] reqData = ("love you" + System.getProperty("line.separator")).getBytes();
//		byte[] reqData = ("love you $").getBytes();
//		ByteBuf message = null;
//		for(int i=0; i<100; i++){
//			message = Unpooled.buffer(reqData.length);
//			message.writeBytes(reqData);
//			ctx.writeAndFlush(message);
//		}
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		for(int i=1; i<100; i++){
			UserInfo user = new UserInfo();
			user.setUserId(i);
			user.setUserName("zcc" + i);
			userInfos.add(user);
		}
		ctx.writeAndFlush(userInfos);
		System.out.println("client active end.........");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		System.out.println("channelRead......");
		/*ByteBuf buf = (ByteBuf)msg;
		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		String receiveData = new String(data, "UTF-8");*/
		String receiveData = (String)msg;
		System.out.println(receiveData);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//		System.out.println("client read compltete .......");
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("client exception .........");
		ctx.close();
	}

}
