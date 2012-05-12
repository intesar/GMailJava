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
 * @author intesar
 */
public interface EmailService {

    String SMTP_HOST_NAME = "smtp.gmail.com";
    String SMTP_PORT = "465";
    String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    
    //String EMAIL_FROM_ADDRESS = "team@zytoon.me";
    
    // TODO Start
    
    // username or email -- team@zytoon.me
    String SEND_FROM_USERNAME = "";
    // password 
    String SEND_FROM_PASSWORD = "";
    
    
    String EMAIL_SIGNATURE = "<br/>"
            + "Thanks, <br/>"
            + "Zytoon.me Team";

    String EMAIL_CONTENT_TYPE = "text/html";
    
    
    // Todo End
    
    /**
     * 
     * @param toAddress
     * @param subject
     * @param body
     * @return true success, false failure
     */
    boolean sendEmail(String toAddress, String subject, String body);
    
    /**
     * 
     * @param toAddresses
     * @param subject
     * @param body 
     * @return true success, false failure
     */
    boolean sendEmail(String[] toAddresses, String subject, String body);
    
}
