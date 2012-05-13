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

import java.util.Date;
import static org.junit.Assert.fail;
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

    private EmailService instance = EmailService.getInstance();
    /**
     * Test of sendEmail method, of class EmailService.
     */
    @Test
    public void testSendEmail1() {
        System.out.println("sendEmail");
        String toAddress = "intesar@ymail.com";
        String subject = "Monitor App Test!";
        String body = "Test!";
        
        try {
            long st = (new Date()).getTime();
            instance.sendEmail(toAddress, subject, body);
            System.out.println("Total Time ms : " + ((new Date()).getTime() - (st)));
        } catch (RuntimeException ex) {
            fail("The test case is a prototype.");
        }
    }
    
    /**
     * Test of sendEmail method, of class EmailService.
     */
    @Test
    public void testSendEmail2() {
        System.out.println("sendEmail");
        String[] toAddress = {"intesar@ymail.com"};
        String subject = "Monitor App Test!";
        String body = "Test!";
        try {
            long st = (new Date()).getTime();
            instance.sendEmail(toAddress, subject, body);
            System.out.println("Total Time ms : " + ((new Date()).getTime() - (st)));
        } catch (RuntimeException ex) {
            fail("The test case is a prototype.");
        }
    }
}