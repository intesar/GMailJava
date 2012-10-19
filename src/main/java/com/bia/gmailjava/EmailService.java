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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.EmailValidator;
// uncomment log4j if you already have
//import org.apache.log4j.Logger;

public class EmailService {

    final static String EMAIL_CONTENT_TYPE = "text/html";
    final static String SMTP_HOST_NAME = "smtp.gmail.com";
    final static String SMTP_PORT = "465";
    final static String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    final static String TRUE = "true";
    final static String FALSE = "false";
    final static int ONE = 1;
    private Session session;
    private InternetAddress[] bcc;
    //private static Logger logger = Logger.getLogger(EmailService.class);
    private ExecutorService executor;
    private String username;
    private String password;
    private String[] bccs;

    /**
     * <p> Use this constructor if you want to set bcc and executor </p>
     * @param username cannot be null, should be valid gmail or google-domain username
     * @param password cannot be null, 
     * @param bccs cannot be null
     * @param executor cannot be null
     */
    public EmailService(String username, String password, String[] bccs, ExecutorService executor) {
        this.username = username;
        this.password = password;
        this.bccs = bccs;
        this.executor = executor;
    }
    
    /**
     * <p> Use this constructor if you want to set executor </p>
     * <p> Instantiate with username, password and executor </p>
     * @param username cannot be null
     * @param password cannot be null
     * @param executor cannot be null
     */
    public EmailService(String username, String password, ExecutorService executor) {
        this.username = username;
        this.password = password;
        this.executor = executor;
    }
    
    /**
     * <p> Minimalist constructor, sets executor to default </p>
     * @param username cannot be null
     * @param password cannot be null
     */
    public EmailService(String username, String password) {
        this.username = username;
        this.password = password;
        this.executor = Executors.newFixedThreadPool(ONE);
    }
    
    /**
     * <p> Use this constructor to just set bcc, sets executor to default </p>
     * @param username cannot be null
     * @param password cannot be null
     */
    public EmailService(String username, String password, String[] bccs) {
        this.username = username;
        this.password = password;
        this.bccs = bccs;
    }

    /**
     * Sends async email to one recipient Returns true if email send
     * successfully, otherwise returns false for all errors including invalid
     * input
     *
     * @param toAddress
     * @param subject
     * @param body
     * @return true email send, false invalid input
     */
    public void sendEmail(String toAddress, String subject, String body) {
        String[] toAddresses = {toAddress};
        validate(subject, toAddresses);
        // Aysnc send email
        Runnable emailServiceAsync = new EmailServiceAsync(toAddresses, subject, body);
        executor.execute(emailServiceAsync);

    }

    /**
     * Sends async email to multiple recipients Returns true if email send
     * successfully, otherwise returns false for all errors including invalid
     * input
     *
     * @param toAddresses
     * @param subject
     * @param body
     * @return
     */
    public void sendEmail(String[] toAddresses, String subject, String body) {
        validate(subject, toAddresses);
        // Aysnc send email
        Runnable emailServiceAsync = new EmailServiceAsync(toAddresses, subject, body);
        executor.execute(emailServiceAsync);
    }

    /**
     * call this method from ServletContextListener.contextDestroyed() This will
     * release all work thread's when your app is undeployed
     */
    public void shutdown() {
        // releasing executor
        executor.shutdown();
        // logger.trace("EmailService executor released!");
        System.out.println("EmailService exector released!");

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
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", TRUE);
        props.put("mail.debug", FALSE);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", FALSE);

        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
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
        if (bccs != null && bccs.length > 0) {
            bcc = new InternetAddress[bccs.length];
            int index = 0;
            for (String email : bccs) {
                bcc[index++] = new InternetAddress(email);
            }

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
        InternetAddress addressFrom = new InternetAddress(username);
        msg.setFrom(addressFrom);
        msg.setRecipients(Message.RecipientType.TO, addressTo);

        // set bcc
        InternetAddress[] bcc_ = getBCC();
        if (bcc_ != null && bcc_.length > 0) {
            msg.setRecipients(Message.RecipientType.BCC, bcc_);
        }


        // Setting the Subject and Content Type
        msg.setSubject(subject);
        //String message = comment;
        msg.setContent(message, EMAIL_CONTENT_TYPE);

        Transport.send(msg);
    }

    /**
     * validates subject and to email addresses
     *
     * @param subject
     * @param emails
     */
    private void validate(String subject, String... emails) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean valid = true;
        if (!isValidSubject(subject)) {
            stringBuilder.append("Invalid subject, ");
            valid = false;
        }

        if (!isValidEmail(emails)) {
            stringBuilder.append("Invalid email address");
            valid = false;
        }

        if (!valid) {
            throw new RuntimeException(stringBuilder.toString());
        }
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
     * checks for blank and null
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
     *
     * Send aysnc emails using command pattern
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
}
