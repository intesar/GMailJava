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
import org.junit.*;

/**
 *
 * @author intesar
 */
public class EmailServiceImplTest {

    public EmailServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        
    }

    @After
    public void tearDown() {
    }
       final String EMAIL = "mdshannan@gmail.com";

    /**
     * Test of sendEmail method, of class EmailService.
     */
    @Test
    public void testSendEmail_3args_1() {
        
        System.out.println("sendEmail");
        
        EmailService instance = new EmailService(EMAIL, null, null, Executors.newFixedThreadPool(1));
        
        String toAddress = "mdshannan@gmail.com";
        String subject = "testing yahoo send";
        String body = "testing body";

        check(instance, toAddress, subject, body, true); // all valid
        check(instance, toAddress, subject, "", true); // optional body missing
        check(instance, toAddress, subject, null, true); // optional body missing
        check(instance, toAddress, subject, " ", true); // optional body missing

        check(instance, toAddress, "", body, false); // subject missing
        check(instance, toAddress, null, body, false); // subject missing
        check(instance, toAddress, "  ", body, false); // subject missing

        String toAddress0 = null;
        check(instance, toAddress0, subject, body, false); // subject missing
        String toAddress1 = "mdshannan@gmail";
        check(instance, toAddress1, subject, body, false); // subject missing
        String toAddress2 = "mdshannan";
        check(instance, toAddress2, subject, body, false); // subject missing
        String toAddress3 = "";
        check(instance, toAddress3, subject, body, false); // subject missing
    }

    /**
     * Test of sendEmail method, of class EmailService.
     */
    @Test
    public void testSendEmail_3args_2() {
        System.out.println("sendEmail");
        
        EmailService instance = new EmailService(EMAIL, null, null, Executors.newFixedThreadPool(1));
        
        String[] toAddress = {"mdshannan@gmail.com"};
        String subject = "testing yahoo send";
        String body = "testing body";
        String EMPTY = "";
        String SPACE = " ";
        String NULL = null;

        check(instance, toAddress, subject, body, true); // all valid
        check(instance, toAddress, subject, EMPTY, true); // optional body missing
        check(instance, toAddress, subject, NULL, true); // optional body missing
        check(instance, toAddress, subject, SPACE, true); // optional body missing

        check(instance, toAddress, EMPTY, body, false); // subject missing
        check(instance, toAddress, NULL, body, false); // subject missing
        check(instance, toAddress, SPACE, body, false); // subject missing

        String[] toAddress0 = null;
        check(instance, toAddress0, subject, body, false); // TO missing
        String[] toAddress1 = {"mdshannan@gmail"};
        check(instance, toAddress1, subject, body, false); // no .com 
        String[] toAddress2 = {"mdshannan"};
        check(instance, toAddress2, subject, body, false); // invalid email 
        String[] toAddress3 = {""};
        check(instance, toAddress3, subject, body, false); // email missing
    }
    
    
    /**
     * Test of sendEmail method, of class EmailService.
     */
    @Test
    public void testSendEmail_2() {
        
        System.out.println("sendEmail");
        String []bcc = {"mdshannan@gmail.com"};
        EmailService instance = new EmailService(EMAIL, null, bcc);
        
        String toAddress = "mdshannan@gmail.com";
        String subject = "testing yahoo send";
        String body = "testing body";

        check(instance, toAddress, subject, body, true); // all valid
        check(instance, toAddress, subject, "", true); // optional body missing
        check(instance, toAddress, subject, null, true); // optional body missing
        check(instance, toAddress, subject, " ", true); // optional body missing

        check(instance, toAddress, "", body, false); // subject missing
        check(instance, toAddress, null, body, false); // subject missing
        check(instance, toAddress, "  ", body, false); // subject missing

        String toAddress0 = null;
        check(instance, toAddress0, subject, body, false); // subject missing
        String toAddress1 = "mdshannan@gmail";
        check(instance, toAddress1, subject, body, false); // subject missing
        String toAddress2 = "mdshannan";
        check(instance, toAddress2, subject, body, false); // subject missing
        String toAddress3 = "";
        check(instance, toAddress3, subject, body, false); // subject missing
    }

    /**
     * Test of sendEmail method, of class EmailService.
     */
    @Test
    public void testSendEmail_3() {
        System.out.println("sendEmail");
        String []bcc = {"mdshannan@gmail.com"};
        EmailService instance = new EmailService(EMAIL, null, bcc);
        
        String[] toAddress = {"mdshannan@gmail.com"};
        String subject = "testing yahoo send";
        String body = "testing body";
        String EMPTY = "";
        String SPACE = " ";
        String NULL = null;

        check(instance, toAddress, subject, body, true); // all valid
        check(instance, toAddress, subject, EMPTY, true); // optional body missing
        check(instance, toAddress, subject, NULL, true); // optional body missing
        check(instance, toAddress, subject, SPACE, true); // optional body missing

        check(instance, toAddress, EMPTY, body, false); // subject missing
        check(instance, toAddress, NULL, body, false); // subject missing
        check(instance, toAddress, SPACE, body, false); // subject missing

        String[] toAddress0 = null;
        check(instance, toAddress0, subject, body, false); // TO missing
        String[] toAddress1 = {"mdshannan@gmail"};
        check(instance, toAddress1, subject, body, false); // no .com 
        String[] toAddress2 = {"mdshannan"};
        check(instance, toAddress2, subject, body, false); // invalid email 
        String[] toAddress3 = {""};
        check(instance, toAddress3, subject, body, false); // email missing
    }
    
    /**
     * Test of sendEmail method, of class EmailService.
     */
    @Test
    public void testSendEmail_4() {
        
        System.out.println("sendEmail");
        EmailService instance = new EmailService(EMAIL, null, Executors.newFixedThreadPool(1));
        
        String toAddress = "mdshannan@gmail.com";
        String subject = "testing yahoo send";
        String body = "testing body";

        check(instance, toAddress, subject, body, true); // all valid
        check(instance, toAddress, subject, "", true); // optional body missing
        check(instance, toAddress, subject, null, true); // optional body missing
        check(instance, toAddress, subject, " ", true); // optional body missing

        check(instance, toAddress, "", body, false); // subject missing
        check(instance, toAddress, null, body, false); // subject missing
        check(instance, toAddress, "  ", body, false); // subject missing

        String toAddress0 = null;
        check(instance, toAddress0, subject, body, false); // subject missing
        String toAddress1 = "mdshannan@gmail";
        check(instance, toAddress1, subject, body, false); // subject missing
        String toAddress2 = "mdshannan";
        check(instance, toAddress2, subject, body, false); // subject missing
        String toAddress3 = "";
        check(instance, toAddress3, subject, body, false); // subject missing
    }

    /**
     * Test of sendEmail method, of class EmailService.
     */
    @Test
    public void testSendEmail_5() {
        System.out.println("sendEmail");
        
        EmailService instance = new EmailService(EMAIL, null, Executors.newFixedThreadPool(1));
        
        String[] toAddress = {"mdshannan@gmail.com"};
        String subject = "testing yahoo send";
        String body = "testing body";
        String EMPTY = "";
        String SPACE = " ";
        String NULL = null;

        check(instance, toAddress, subject, body, true); // all valid
        check(instance, toAddress, subject, EMPTY, true); // optional body missing
        check(instance, toAddress, subject, NULL, true); // optional body missing
        check(instance, toAddress, subject, SPACE, true); // optional body missing

        check(instance, toAddress, EMPTY, body, false); // subject missing
        check(instance, toAddress, NULL, body, false); // subject missing
        check(instance, toAddress, SPACE, body, false); // subject missing

        String[] toAddress0 = null;
        check(instance, toAddress0, subject, body, false); // TO missing
        String[] toAddress1 = {"mdshannan@gmail"};
        check(instance, toAddress1, subject, body, false); // no .com 
        String[] toAddress2 = {"mdshannan"};
        check(instance, toAddress2, subject, body, false); // invalid email 
        String[] toAddress3 = {""};
        check(instance, toAddress3, subject, body, false); // email missing
    }
    
    
    /**
     * Test of sendEmail method, of class EmailService.
     */
    @Test
    public void testSendEmail_6() {
        
        System.out.println("sendEmail");
        EmailService instance = new EmailService(EMAIL, null);
        
        String toAddress = "mdshannan@gmail.com";
        String subject = "testing yahoo send";
        String body = "testing body";

        check(instance, toAddress, subject, body, true); // all valid
        check(instance, toAddress, subject, "", true); // optional body missing
        check(instance, toAddress, subject, null, true); // optional body missing
        check(instance, toAddress, subject, " ", true); // optional body missing

        check(instance, toAddress, "", body, false); // subject missing
        check(instance, toAddress, null, body, false); // subject missing
        check(instance, toAddress, "  ", body, false); // subject missing

        String toAddress0 = null;
        check(instance, toAddress0, subject, body, false); // subject missing
        String toAddress1 = "mdshannan@gmail";
        check(instance, toAddress1, subject, body, false); // subject missing
        String toAddress2 = "mdshannan";
        check(instance, toAddress2, subject, body, false); // subject missing
        String toAddress3 = "";
        check(instance, toAddress3, subject, body, false); // subject missing
    }

    /**
     * Test of sendEmail method, of class EmailService.
     */
    @Test
    public void testSendEmail_7() {
        System.out.println("sendEmail");
        
        EmailService instance = new EmailService(EMAIL, null);
        
        String[] toAddress = {"mdshannan@gmail.com"};
        String subject = "testing yahoo send";
        String body = "testing body";
        String EMPTY = "";
        String SPACE = " ";
        String NULL = null;

        check(instance, toAddress, subject, body, true); // all valid
        check(instance, toAddress, subject, EMPTY, true); // optional body missing
        check(instance, toAddress, subject, NULL, true); // optional body missing
        check(instance, toAddress, subject, SPACE, true); // optional body missing

        check(instance, toAddress, EMPTY, body, false); // subject missing
        check(instance, toAddress, NULL, body, false); // subject missing
        check(instance, toAddress, SPACE, body, false); // subject missing

        String[] toAddress0 = null;
        check(instance, toAddress0, subject, body, false); // TO missing
        String[] toAddress1 = {"mdshannan@gmail"};
        check(instance, toAddress1, subject, body, false); // no .com 
        String[] toAddress2 = {"mdshannan"};
        check(instance, toAddress2, subject, body, false); // invalid email 
        String[] toAddress3 = {""};
        check(instance, toAddress3, subject, body, false); // email missing
    }

    private void check(EmailService instance, String to, String subject, String body, boolean expResult) {
        try {
            instance.sendEmail(to, subject, body);
            if (!expResult) {
                Assert.fail();
            }
        } catch (RuntimeException re) {
        }
    }

    private void check(EmailService instance, String[] to, String subject, String body, boolean expResult) {
        try {
            instance.sendEmail(to, subject, body);
            if (!expResult) {
                Assert.fail();
            }
        } catch (RuntimeException re) {
        }
    }
}