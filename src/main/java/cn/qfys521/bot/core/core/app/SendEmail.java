package cn.qfys521.bot.core.core.app;

import cn.qfys521.bot.core.BotApplication;
import cn.qfys521.bot.core.config.CoreConfigApplication;
import cn.qfys521.bot.plugin.core.CoreInteractors;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.Data;
import lombok.Setter;

public class SendEmail {
    /**
     * @param subject 标题
     * @param content 正文
     */

    private String from;
    private String to;
    private String subject;
    private String content;
    private String host;
    private String password;

    public static void sendEmail(String subject, String content) {
        //return;
        CoreConfigApplication configApplication = new CoreConfigApplication(new mailConfig() , "mail.json");
        mailConfig config = (mailConfig) configApplication.getDataOrFail();
        var sendEmail = new EmailSender.EmailSenderBuilder()
                .subject(subject)
                .body(content)
                .senderName(config.getSenderName())
                .host(config.getHost())
                .password(config.getPassword())
                .to(config.getTo())
                .props(System.getProperties())
                .build();
        sendEmail.send();




    }


}
@Data
class mailConfig{
    private String host;
    private String senderName;
    private String password;
    private String to;
}