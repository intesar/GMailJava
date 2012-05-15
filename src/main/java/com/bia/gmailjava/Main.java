/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bia.gmailjava;

/**
 *
 * @author intesar
 */
public class Main {
    public static void main(String[] args) {
        EmailService emailService = EmailService.getInstance();
        
        emailService.sendEmail("mdshannan@gmail.com", "test subject from GMailJava ", "test body");
        
        System.out.println("done!");
        
        emailService.shutdown();
    }
}
