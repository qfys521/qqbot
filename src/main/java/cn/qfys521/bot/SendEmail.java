package cn.qfys521.bot;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
    /**
     * @param subject 标题
     * @param content 正文
     */
    public static void sendEmail(String subject, String content) {
        if (false) {
            // 收件人电子邮箱
            String to = "qfys520@vip.qq.com";

            // 发件人电子邮箱
            String from = "notice@codethink.cn";

            // 指定发送邮件的主机为 localhost
            String host = "smtp.feishu.cn";

            // 获取系统属性
            Properties props = System.getProperties();

            // 设置邮件服务器
            props.setProperty("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
       /* props.setProperty("mail.user", "notice");
        props.setProperty("mail.password", "NaCHHMTcS9xnSDue");
        */
            // 获取默认session对象
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("notice@codethink.cn", "NaCHHMTcS9xnSDue"); //发件人邮件用户名、授权码
                }
            });

            try {
                // 创建默认的 MimeMessage 对象
                MimeMessage message = new MimeMessage(session);

                // Set From: 头部头字段
                message.setFrom(new InternetAddress(from));

                // Set To: 头部头字段
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                // Set Subject: 头部头字段
                message.setSubject(subject);

                // 设置消息体
                message.setText(content);

                // 发送消息
                Transport.send(message);
                System.out.println("Sent message successfully....");



            } catch (MessagingException mex) {
                BotApplication.getLogger().error(mex.getMessage());
            }
        }

    }
}
