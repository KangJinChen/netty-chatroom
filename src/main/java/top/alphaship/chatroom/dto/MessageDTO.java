package top.alphaship.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 客户端与服务端通信的对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private String userName;

    private Date sentTime;

    private String msg;

}
