package top.alphaship.chatroom.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.apache.commons.lang3.StringUtils;
import top.alphaship.chatroom.constants.ChatroomConstants;
import top.alphaship.chatroom.dto.MessageDTO;
import top.alphaship.chatroom.util.MessageUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

/**
 * 客户端接受消息处理器
 */
public class ClientMsgHandler extends SimpleChannelInboundHandler<MessageDTO> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageDTO messageDTO) throws Exception {
            MessageUtils.printMsg(messageDTO);
            ReferenceCountUtil.release(messageDTO);
            System.out.println("=>> kali-client, please input msg: ");
            Scanner scanner = new Scanner(System.in);
            String reply = scanner.nextLine();
            MessageDTO message = new MessageDTO(ChatroomConstants.CLIENT, new Date(), reply);
            ByteBuf buffer = ctx.alloc().buffer();
            String content = MessageUtils.encodeMsg(message);
            buffer.writeBytes(content.getBytes(StandardCharsets.UTF_8));
            ctx.writeAndFlush(buffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
