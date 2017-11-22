package com.zcc.netty.msgpack;

import java.util.List;

import org.msgpack.MessagePack;

import com.zcc.po.UserInfo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * msgpack 消息解码器
 * @author zcc
 *
 * @date 2017年11月17日
 */
public class MsgPackDecode extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		int length = msg.readableBytes();
		byte[] array = new byte[length]; 
		msg.getBytes(msg.readerIndex(), array, 0, length);
		MessagePack msgPack = new MessagePack();
		out.add(msgPack.read(array));
	}

}
