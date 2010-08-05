package org.ssh.app.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * AOP advice that sends confirmation email after order has been submitted.
 * We use {@literal AfterReTurn} here because we expect any failure in email sending should not bother the normal
 * order transaction.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SimpleTextEmailSender implements EmailSender {

    private static final Log log = LogFactory.getLog(SimpleTextEmailSender.class);
    private String mailFrom;
    private String mailTo;
    private String subject;
    private MailSender mailSender;
    private String mailText;

    public SimpleTextEmailSender() {
    }

    public SimpleTextEmailSender(String mailFrom, String mailTo, String subject, String mailText) {
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.subject = subject;
        this.mailText = mailText;
    }

    @Required
    @Override
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Required
    @Override
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    /**
     * Not required, as can be initialized during runtime.
     * @param mailTo
     */
    @Override
    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    /**
     * Not required, as can be initialized during runtime.
     * @param mailTo
     */
    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Not required, as can be initialized during runtime.
     * @param mailText
     */
    @Override
    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    @Override
    public void send() throws Throwable {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailTo);
        mailMessage.setFrom(this.mailFrom);
        mailMessage.setSubject(this.subject);
        mailMessage.setText(mailText);
        try {
            log.info("sending email to " + mailTo + " ...");
            this.mailSender.send(mailMessage);
            log.info("successful sent email to " + mailTo);
        } catch (MailException ex) {
            log.warn("An exception occured when trying to send email", ex);
            // ignore it and let it go
        }
    }
}
