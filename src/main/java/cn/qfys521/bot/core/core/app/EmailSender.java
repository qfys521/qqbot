package cn.qfys521.bot.core.core.app;

import cn.qfys521.bot.core.BotApplication;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder()
public class EmailSender {
    private String host;
    private String senderName;
    private String password;
    private String to;
    private String subject;
    private String body;
    Properties props;

    public void send(){
        props.setProperty("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderName, password); //发件人邮件用户名、授权码
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(senderName));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject(subject);

            // 设置消息体
            message.setText(body);

            // 发送消息
            Transport.send(message);
            BotApplication.getLogger().info("Sent message successfully!");



        } catch (MessagingException mex) {
            BotApplication.getLogger().error(mex.getMessage());
        }


    }
}