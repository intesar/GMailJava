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

import static org.junit.Assert.assertEquals;
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
    public void testSendEmail_3args_1() {
        System.out.println("sendEmail");
        
        String toAddress = "mdshannan@gmail.com";
        String subject = "testing yahoo send";
        String body = "testing body";
        
        check(toAddress, subject, body, true); // all valid
        check(toAddress, subject, "", true); // optional body missing
        check(toAddress, subject, null, true); // optional body missing
        check(toAddress, subject, " ", true); // optional body missing
        
        check(toAddress, "", body, false); // subject missing
        check(toAddress, null, body, false); // subject missing
        check(toAddress, "  ", body, false); // subject missing
        
        String toAddress0 = null;
        check(toAddress0, subject, body, false); // subject missing
        String toAddress1 = "mdshannan@gmail";
        check(toAddress1, subject, body, false); // subject missing
        String toAddress2 = "mdshannan";
        check(toAddress2, subject, body, false); // subject missing
        String toAddress3 = "";
        check(toAddress3, subject, body, false); // subject missing
    }

    /**
     * Test of sendEmail method, of class EmailService.
     */
    @Test
    public void testSendEmail_3args_2() {
        System.out.println("sendEmail");
        String[] toAddress = {"mdshannan@gmail.com"};
        String subject = "testing yahoo send";
        String body = "testing body";
        
        check(toAddress, subject, body, true); // all valid
        check(toAddress, subject, "", true); // optional body missing
        check(toAddress, subject, null, true); // optional body missing
        check(toAddress, subject, " ", true); // optional body missing
        
        check(toAddress, "", body, false); // subject missing
        check(toAddress, null, body, false); // subject missing
        check(toAddress, "  ", body, false); // subject missing
        
        String[] toAddress0 = null;
        check(toAddress0, subject, body, false); // subject missing
        String[] toAddress1 = {"mdshannan@gmail"};
        check(toAddress1, subject, body, false); // subject missing
        String[] toAddress2 = {"mdshannan"};
        check(toAddress2, subject, body, false); // subject missing
        String[] toAddress3 = {""};
        check(toAddress3, subject, body, false); // subject missing
    }
    
    private void check(String to, String subject, String body, boolean expResult) {
        boolean result = instance.sendEmail(to, subject, body);
        assertEquals(expResult, result);
    }
    
    private void check(String[] to, String subject, String body, boolean expResult) {
        boolean result = instance.sendEmail(to, subject, body);
        assertEquals(expResult, result);
    }
}