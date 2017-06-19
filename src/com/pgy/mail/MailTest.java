package com.pgy.mail;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MailTest {
    public static void main(String[] args) {
        MailBody body = new MailBody();
        body.setProtocal("smtp");
        body.setSendDate(new Date());
        body.setSubject("Test");
        body.setText("hello");

        //        MailUtil.sendMail(getEnterpriseQQMailBody(body));
        //        MailUtil.sendMail(getQQMailBody(body));
        //        MailUtil.sendMail(get163MailBody(body));
        //        MailUtil.sendMail(getGmailBody(body));

        //        sendMail();
        System.out.println(Boolean.toString(true));

    }

    private static void sendMail() {
        JavaMailSenderImpl jmsi = new JavaMailSenderImpl();

        String host = "smtp.qq.com";
        String port = "465";
        String userName = "345681764@qq.com";
        String password = "13598260325";
        String defaultEncoding = "UTF-8";

        jmsi.setUsername(userName);
        jmsi.setPassword(password);
        jmsi.setPort(Integer.parseInt(port));
        jmsi.setDefaultEncoding(defaultEncoding);

        Properties javaMailProperties = new Properties();

        // 设置超时时间
        javaMailProperties.setProperty("mail.connection.timeout", "2500");
        javaMailProperties.setProperty("mail.socket.timeout", "2500");

        javaMailProperties.setProperty("mail.smtp.timeout", "2500");
        // 设置邮件服务器主机
        javaMailProperties.setProperty("mail.smtp.host", host);
        // 设置邮件服务器端口号
        javaMailProperties.setProperty("mail.smtp.port", port);
        // 开启debug调试
        javaMailProperties.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        javaMailProperties.setProperty("mail.smtp.auth", "true");
        javaMailProperties
                .setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", "false");
        jmsi.setJavaMailProperties(javaMailProperties);

        try {
            MimeMessage mimeMessage = jmsi.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, defaultEncoding);
            helper.setTo("peng_gy@163.com");
            helper.setSubject("Test");
            helper.setFrom("345681764@qq.com");
            helper.setCc("pengganyu@idcos.com");
            //            helper.setReplyTo("pengganyu@idcos.com");

            helper.setPriority(1);
            helper.setText("hello");
            helper.setSentDate(new Date());

            helper.addAttachment("hello.txt", new File("/tmp/hello.txt"));

            // 引入模板
            Map<String, Object> vmMap = new HashMap<>();
            vmMap.put("userName", "zhangsan");
            vmMap.put("password", "zhangsan1234");

            String text = VelocityEngineUtils
                    .mergeTemplateIntoString(getEngine(), "src/resource/MailTemplate.vm",
                            defaultEncoding, vmMap);

            helper.setText(text, true);
            helper.addInline("identifier1234",
                    new File("/Users/admin/Pictures/beauty/jingxuan029.jpg"));

            jmsi.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static void sendMail1() {
    }

    private static VelocityEngine getEngine() {

        VelocityEngine velocityEngine = new VelocityEngine();

        Properties props = new Properties();

        props.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        props.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        props.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
        velocityEngine.init(props);

        return velocityEngine;
    }

    private static MailBody getEnterpriseQQMailBody(MailBody body) {
        String[] to = { "peng_gy@163.com" };
        body.setFrom("pengganyu@idcos.com");
        body.setTo(to);
        body.setPort(465);
        body.setHost("smtp.qq.com");
        body.setUsername("pengganyu@idcos.com");
        body.setPassword("Yilin1992");
        return body;
    }

    private static MailBody getQQMailBody(MailBody body) {

        String[] to = { "peng_gy@163.com" };
        body.setFrom("345681764@qq.com");
        body.setTo(to);
        body.setPort(465);
        body.setHost("smtp.qq.com");
        body.setUsername("345681764@qq.com");
        body.setPassword("13598260325");
        return body;
    }

    private static MailBody get163MailBody(MailBody body) {
        String[] to = { "345681764@qq.com" };
        body.setFrom("peng_gy@163.com");
        body.setTo(to);
        body.setPort(465);
        body.setHost("smtp.163.com");
        body.setUsername("peng_gy@163.com");
        body.setPassword("kedadiannao220");
        return body;
    }

    private static MailBody getGmailBody(MailBody body) {
        String[] to = { "peng_gy@163.com" };
        body.setFrom("kedadiannao220@gmail.com");
        body.setTo(to);
        body.setPort(465);
        body.setHost("smtp.gmail.com");
        body.setUsername("kedadiannao220@gmail.com");
        body.setPassword("Yilin1992");
        return body;
    }
}