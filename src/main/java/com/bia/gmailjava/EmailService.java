/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bia.gmailjava;

/**
 *
 * @author intesar mohammed
 * mdshannan@gmail.com
 */
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.validator.routines.EmailValidator;
// uncomment log4j if you already have
//import org.apache.log4j.Logger;

public class EmailService {

    // just set the username / password
    // example@gmail.com or example@zytoon.me
    private String USERNAME = "<example@gmail.com>";
    private String PASSWORD = "<password>";
    private String EMAIL_CONTENT_TYPE = "text/html";
    private String SMTP_HOST_NAME = "smtp.gmail.com";
    private String SMTP_PORT = "465";
    private String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    //private static Logger logger = Logger.getLogger(EmailService.class);
    private static final EmailService instance = new EmailService();
    private ScheduledThreadPoolExecutor executor;

    private EmailService() {
        executor = new ScheduledThreadPoolExecutor(2);
    }
    private Session session;
    // If require enable it
    //private InternetAddress[] bcc;

    public static EmailService getInstance() {
        return instance;
    }

    /**
     * 
     * @param toAddress
     * @param subject
     * @param body
     * @return true email send, false invalid input
     */
    public boolean sendEmail(String toAddress, String subject, String body) {
        if (!isValidEmail(toAddress) || !isValidSubject(subject)) {
            return false;
        }

        String[] to = {toAddress};
        // Aysnc send email
        Runnable emailServiceAsync = new EmailServiceAsync(to, subject, body);
        this.executor.schedule(emailServiceAsync, 1, TimeUnit.MILLISECONDS);

        return true;
    }

    /**
     * 
     * @param toAddresses
     * @param subject
     * @param body
     * @return true email send, false invalid input
     */
    public boolean sendEmail(String[] toAddresses, String subject, String body) {
        if (!isValidEmail(toAddresses) || !isValidSubject(subject)) {
            return false;
        }

        // Aysnc send email
        Runnable emailServiceAsync = new EmailServiceAsync(toAddresses, subject, body);
        this.executor.schedule(emailServiceAsync, 1, TimeUnit.MILLISECONDS);

        return true;

    }

    /**
     *
     * @param emails
     * @return
     */
    private boolean isValidEmail(String... emails) {
        if ( emails == null ) {
            return false;
        }
        for (String email : emails) {
            if (!EmailValidator.getInstance().isValid(email)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidSubject(String subject) {
        if (subject == null || subject.trim().length() == 0) {
            return false;
        }
        return true;
    }

    /*
     *
     * Aysnc send emails using command pattern
     *
     */
    private class EmailServiceAsync implements Runnable {

        String recipients[];
        String subject;
        String message;

        EmailServiceAsync(String recipients[], String subject,
                String message) {
            this.recipients = recipients;
            this.subject = subject;
            this.message = message;
        }

        public void run() {
            EmailService.this.sendSSMessage(recipients, subject, message);
        }
    }

    /**
     * session is created only once
     *
     * @return
     */
    private Session createSession() {

        if (session != null) {
            return session;
        }
        boolean debug = false;

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD); // todo add password before deploy
                    }
                });

        session.setDebug(debug);
        return session;
    }

    /**
     * bcc -- incase you need to be copied on emails
     *
     * @return
     * @throws AddressException
     */
//    private InternetAddress[] getBCC() throws AddressException {
//        if (bcc != null) {
//            return bcc;
//        }
//        bcc = new InternetAddress[1];
//        bcc[0] = new InternetAddress("example@yahoo.com");
//        return bcc;
//    }
    /**
     *
     * @param recipients
     * @param subject
     * @param message
     * @param from
     * @throws MessagingException
     */
    private void sendSSMessage(String recipients[], String subject,
            String message) {

        try {
            if (recipients == null || recipients.length == 0) {
                return;
            }

            InternetAddress[] addressTo = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                if (recipients[i] != null && recipients[i].length() > 0) {
                    addressTo[i] = new InternetAddress(recipients[i]);
                }
            }
            send(addressTo, subject, message);

        } catch (Exception ex) {
            //logger.warn(ex.getMessage(), ex);
            throw new RuntimeException("Error sending email, please check to and from emails are correct!");
        }
    }

    /**
     *
     * @param addressTo
     * @param subject
     * @param message
     * @throws AddressException
     * @throws MessagingException
     */
    private void send(InternetAddress[] addressTo, String subject, String message) throws AddressException, MessagingException {
        Message msg = new MimeMessage(createSession());
        InternetAddress addressFrom = new InternetAddress(USERNAME);
        msg.setFrom(addressFrom);
        msg.setRecipients(Message.RecipientType.TO, addressTo);

        // set bcc
        //InternetAddress[] bcc1 = getBCC();
        //msg.setRecipients(Message.RecipientType.BCC, bcc1);

        // Setting the Subject and Content Type
        msg.setSubject(subject);
        //String message = comment;
        msg.setContent(message, EMAIL_CONTENT_TYPE);

        Transport.send(msg);
    }
}
