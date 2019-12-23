package top.alphaship.chatroom.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import top.alphaship.chatroom.dto.MessageDTO;
import top.alphaship.chatroom.util.MessageUtils;

import java.nio.charset.StandardCharsets;

/**
 * 把消息实体转为字符串的处理器
 */
public class MessageEncodeHandler extends MessageToByteEncoder<MessageDTO> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageDTO messageDTO, ByteBuf byteBuf) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        String content = MessageUtils.encodeMsg(messageDTO);
        buffer.writeBytes(content.getBytes(StandardCharsets.UTF_8));

        ctx.writeAndFlush(buffer);
    }
}
