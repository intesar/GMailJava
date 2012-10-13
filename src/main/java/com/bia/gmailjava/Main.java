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
        String[] bcc = {"somename@yahoo.com"}; // only if you want emails to bcc otherwise pass null
        EmailService emailService = new EmailService("somename@gmail.com", "somepassword", bcc, Executors.newFixedThreadPool(1));
        
        emailService.sendEmail("mdshannan@gmail.com", "test subject from GMailJava ", "test body");
        
        System.out.println("done!");
        
        emailService.shutdown();
    }
}
