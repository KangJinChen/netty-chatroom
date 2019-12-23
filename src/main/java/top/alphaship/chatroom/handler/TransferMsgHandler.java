package top.alphaship.chatroom.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import top.alphaship.chatroom.dto.MessageDTO;
import top.alphaship.chatroom.util.MessageUtils;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 将byte消息转换成MessageDTO
 */
public class TransferMsgHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        String totalMsg = byteBuf.readCharSequence(byteBuf.readableBytes(), Charset.forName("UTF-8")).toString();
        String[] content = totalMsg.split("~");
        list.add(new MessageDTO(content[0], MessageUtils.parseDateTime(content[1]), content[2]));
    }
}
