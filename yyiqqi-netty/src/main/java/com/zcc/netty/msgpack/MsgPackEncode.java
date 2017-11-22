package com.zcc.netty.msgpack;

import org.msgpack.MessagePack;

import com.zcc.po.UserInfo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * msgpack 对象编码器
 * @author zcc
 *
 * @date 2017年11月17日
 */
public class MsgPackEncode extends MessageToByteEncoder<Object>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		MessagePack msgPack = new MessagePack();
		byte[] bytes = msgPack.write(msg);
		out.writeBytes(bytes);
	}

}
