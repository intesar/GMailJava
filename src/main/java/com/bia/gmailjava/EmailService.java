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
 * @author intesar mohammed mdshannan@gmail.com
 */
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.EmailValidator;
// uncomment log4j if you already have
//import org.apache.log4j.Logger;

enum Credentials {

    // just set the username / password
    USERNAME("<example@gmail.com>"), // TODO
    PASSWORD("<password>"), // TODO   
    BCC_EMAIL("<example@yahoo.com>"), // TODO

    EMAIL_CONTENT_TYPE("text/html"),
    SMTP_HOST_NAME("smtp.gmail.com"),
    SMTP_PORT("465"),
    SSL_FACTORY("javax.net.ssl.SSLSocketFactory");

    Credentials(String val) {
        this.val = val;
    }
    private String val;

    @Override
    public String toString() {
        return val;
    }
}

public class EmailService {

    //private static Logger logger = Logger.getLogger(EmailService.class);
    private static final EmailService instance = new EmailService();
    private ScheduledThreadPoolExecutor executor;

    private EmailService() {
        executor = new ScheduledThreadPoolExecutor(2);
    }
    private Session session;
    // If require enable it
    private InternetAddress[] bcc;

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
     * Aysnc send emails using command pattern
     *
     */
    private class EmailServiceAsync implements Runnable {

        private String recipients[];
        private String subject;
        private String message;

        EmailServiceAsync(String recipients[], String subject,
                String message) {
            this.recipients = recipients;
            this.subject = subject;
            this.message = message;
        }

        @Override
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

        Properties props = new Properties();
        props.put("mail.smtp.host", Credentials.SMTP_HOST_NAME.toString());
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.port", Credentials.SMTP_PORT.toString());
        props.put("mail.smtp.socketFactory.port", Credentials.SMTP_PORT.toString());
        props.put("mail.smtp.socketFactory.class", Credentials.SSL_FACTORY.toString());
        props.put("mail.smtp.socketFactory.fallback", "false");
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Credentials.USERNAME.toString(), Credentials.PASSWORD.toString());
                    }
                });

        session.setDebug(false);
        return session;
    }

    /**
     * bcc -- incase you need to be copied on emails
     *
     * @return
     * @throws AddressException
     */
    private InternetAddress[] getBCC() throws AddressException {
        if (bcc != null) {
            return bcc;
        }
        if (!Credentials.BCC_EMAIL.toString().equals("<example@yahoo.com>") && isValidEmail(Credentials.BCC_EMAIL.toString())) {
            bcc = new InternetAddress[1];
            bcc[0] = new InternetAddress(Credentials.BCC_EMAIL.toString());
        } else {
            bcc = new InternetAddress[0];
        }

        return bcc;
    }

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
        InternetAddress addressFrom = new InternetAddress(Credentials.USERNAME.toString());
        msg.setFrom(addressFrom);
        msg.setRecipients(Message.RecipientType.TO, addressTo);

        // set bcc
        InternetAddress[] bcc1 = getBCC();
        if (bcc1 != null && bcc1.length > 0) {
            msg.setRecipients(Message.RecipientType.BCC, bcc1);
        }

        // Setting the Subject and Content Type
        msg.setSubject(subject);
        //String message = comment;
        msg.setContent(message, Credentials.EMAIL_CONTENT_TYPE.toString());

        Transport.send(msg);
    }

     /**
     * validates email/emails
     *
     * @param emails
     * @return
     */
    private boolean isValidEmail(String... emails) {
        if (emails == null) {
            return false;
        }
        for (String email : emails) {
            if (!EmailValidator.getInstance().isValid(email)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param subject cannot be empty
     * @return
     */
    private boolean isValidSubject(String subject) {
        return !GenericValidator.isBlankOrNull(subject);
    }
    
    /**
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        try {
            shutdown();
        } finally {
            super.finalize();
        }

    }

    /**
     * call this method from ServletContextListener.contextDestroyed() This will
     * release all work thread's when your app is undeployed
     */
    public void shutdown() {
        // releasing executor
        this.executor.shutdown();
        // logger.trace("EmailService executor released!");
        System.out.println("EmailService exector released!");

    }
}
