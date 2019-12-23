package top.alphaship.chatroom.util;

import top.alphaship.chatroom.dto.MessageDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author ChenKangJin
 * @Date 2019/12/22
 */
public class MessageUtils {

    public static String encodeMsg(MessageDTO messageDTO) {
        return messageDTO.getUserName()
                + "~" + formatDateTime(messageDTO.getSentTime())
                + "~" + messageDTO.getMsg();
    }

    public static String formatDateTime(Date time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    public static Date parseDateTime(String time) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd Hh:mm:ss").parse(time);
        } catch (ParseException e) {
            return null;
        }
    }

    public static void printMsg(MessageDTO messageDTO) {
        System.out.println("<<= " + formatDateTime(messageDTO.getSentTime()) + "【" + messageDTO.getUserName() + "】: " + messageDTO.getMsg());
    }
}
