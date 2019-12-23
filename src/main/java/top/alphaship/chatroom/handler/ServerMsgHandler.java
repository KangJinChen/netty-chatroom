package top.alphaship.chatroom.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.commons.lang3.StringUtils;
import top.alphaship.chatroom.constants.ChatroomConstants;
import top.alphaship.chatroom.dto.MessageDTO;
import top.alphaship.chatroom.util.MessageUtils;

import java.util.Date;
import java.util.Scanner;

/**
 * 服务端接受消息处理器
 */
public class ServerMsgHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("kali-client 进入聊天室");
        MessageDTO messageDTO = new MessageDTO(ChatroomConstants.SERVER, new Date(), "Hello, I am kali-server");
        ByteBuf buffer = ctx.alloc().buffer();
        String content = MessageUtils.encodeMsg(messageDTO);
        buffer.writeBytes(content.getBytes());
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageDTO messageDTO = (MessageDTO) msg;
        ReferenceCountUtil.release(msg);
        MessageUtils.printMsg(messageDTO);
        System.out.println("=>> kali-server, please input msg : ");
        Scanner scanner = new Scanner(System.in);
        String reply = scanner.nextLine();
        MessageDTO replyMsg = new MessageDTO(ChatroomConstants.SERVER, new Date(), reply);
        ctx.writeAndFlush(replyMsg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
