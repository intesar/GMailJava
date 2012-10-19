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

import java.util.concurrent.Executors;

/**
 *
 * @author Intesar Mohammed
 */
public class Main {
    public static void main(String[] args) {
        
        String email = "<<user@gmail.com>>";
        String pass = "<<password>>";
        String subject = "<<subject>>";
        String body = "<<body>>";
        String to = "<<email@yahoo.com>>";
        String[] bcc = {"somename@yahoo.com"}; // only if you want emails to bcc otherwise use other constructor
        
        // Sample 1
        EmailService emailService1 = new EmailService(email, pass);
        emailService1.sendEmail(to, subject, body);
        
        // Sample 2
        EmailService emailService2 = new EmailService(email, pass, Executors.newFixedThreadPool(1));
        emailService2.sendEmail(to, subject, body);
        
        // Sample 3
        EmailService emailService3 = new EmailService(email, pass, bcc);
        emailService3.sendEmail(to, subject, body);
        
        // Sample 4
        
        EmailService emailService4 = new EmailService(email, pass, bcc, Executors.newFixedThreadPool(1));
        emailService4.sendEmail(to, subject, body);
        
    }
}
