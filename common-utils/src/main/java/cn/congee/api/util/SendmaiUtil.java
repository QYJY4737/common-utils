package cn.congee.api.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;

/**
 * 发送邮件工具类
 *
 * @Author: yang
 * @Date: 2020-12-03 11:42
 */
@Slf4j
@Component
public class SendmaiUtil {

    @Value("${spring.mail.username}")
    private String SRPING_MAIL_USERNAME;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 纯文本邮件
     *
     * @param valueMap
     */
    @Async
    public void sendTextMail(HashMap<String, Object> valueMap){
        //建立邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //发送人的邮箱
        message.setFrom(SRPING_MAIL_USERNAME);
        //标题
        message.setSubject(valueMap.get("title").toString());
        //发给谁 对方邮箱
        message.setTo((String[])valueMap.get("to"));
        //内容
        message.setText(valueMap.get("content").toString());
        try{
            javaMailSender.send(message);
        }catch (MailException e){
            log.error("纯文本邮件发送失败->message:{}", e.getMessage());
            throw new RuntimeException("邮件发送失败");
        }
    }

    /**
     * javaMail发送邮件带附件
     *
     * @param valueMap
     * 如果使用freemarker模板
     * Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
     * configuration.setClassForTemplateLoading(this.getClass(), "/templates");
     * String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("mail.ftl"), params);
     */
    public void sendSimpleMail(HashMap<String, Object> valueMap){
        MimeMessage mimeMessage = null;
        try{
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            //设置发件人邮箱
            helper.setFrom(SRPING_MAIL_USERNAME);

            //设置收件人邮箱
            helper.setTo((String[])valueMap.get("to"));

            //设置邮件标题
            helper.setSubject(valueMap.get("title").toString());

            //添加正文(使用thymeleaf模板)
            //Context是导这个包import org.thymeleaf.context.Context;
            Context context = new Context();

            //定义模板数据
            context.setVariables(valueMap);

            //获取thymeleaf的html模板
            String content = templateEngine.process(StringUtils.isEmpty(valueMap.get("template").toString()) ? "mail" : valueMap.get("template").toString(), context);
            helper.setText(content, true);

            //发送邮件
            javaMailSender.send(mimeMessage);
        }catch (MessagingException me){
            me.printStackTrace();
            log.error("发送邮件出错: " + me.getMessage());
        }
    }
}
