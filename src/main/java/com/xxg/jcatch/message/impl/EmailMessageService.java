package com.xxg.jcatch.message.impl;

import com.xxg.jcatch.mbg.bean.TApp;
import com.xxg.jcatch.mbg.bean.TException;
import com.xxg.jcatch.message.MessageService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

/**
 * Created by wucao on 2017/11/16.
 */
public class EmailMessageService implements MessageService {

    private String baseUrl;

    private JavaMailSender mailSender;

    private String mailFrom;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    @Override
    public void sendMessage(TApp app, List<TException> exceptionList, Date start, Date end) throws IOException, MessagingException {

        String[] subscribers = StringUtils.split(app.getSubscriber(), ",");

        ClassPathResource resource = new ClassPathResource("email_template/emailTemplate.vm");
        String html = IOUtils.toString(resource.getInputStream());

        StringWriter writer = new StringWriter();

        VelocityContext context = new VelocityContext();
        context.put("date", new DateTool());
        context.put("app", app);
        context.put("exceptionList", exceptionList);
        context.put("subscribers", subscribers);
        context.put("baseUrl", baseUrl);
        context.put("start", start);
        context.put("end", end);

        Velocity.evaluate(context, writer, "velocityemail", html);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(subscribers);
        helper.setText(writer.toString(), true);
        helper.setFrom(mailFrom);
        helper.setSubject("JCatch应用【" + app.getName() + "】发生异常");
        mailSender.send(message);
    }
}
