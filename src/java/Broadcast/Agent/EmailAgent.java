/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broadcast.Agent;

import Broadcast.util.GroupPostable;
import Broadcast.util.IndividualPostable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 *
 * @author zhijie
 */
public class EmailAgent implements GroupPostable, IndividualPostable {

    private final static String CONTACTSFILE = "../etc/emailContacts.csv;";//"src/Broadcast/etc/emailContacts.csv";
    private final static String USERNAME = "cmsystem007@gmail.com";
    private final static String PASSWORD = "system007";

    @Override
    public void post(Object messageObj) throws Exception {

        ArrayList<String> subscriberEmails = readfromFile();
        for (String receiptant : subscriberEmails) {            
            post(messageObj, receiptant);
        }
        System.out.println("Email broadcast completed!");

    }

    @Override
    public void post(Object messageObj, String recipent) throws Exception {

        Map <String,String[]> messageMap = (HashMap)messageObj;
        String message = messageMap.get("message")[0];
        String subject = messageMap.get("subject")[0];
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        Message content = new MimeMessage(session);
        content.setFrom(new InternetAddress("cmsystem007@gmail.com"));
        content.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipent));
        content.setSubject(subject);
        content.setText(message);

        Transport.send(content);

    }

    private ArrayList<String> readfromFile() throws Exception {
        BufferedReader br = null;
        try {
            ArrayList<String> recipents = new ArrayList();

            File f = new File(CONTACTSFILE);
            System.out.println(f.getAbsoluteFile());
            FileReader fr = new FileReader(f);
            br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String lineArr[] = line.split(",");
                String recipentsEmail = lineArr[1].trim();
                recipents.add(recipentsEmail);
            }
            br.close();
            return recipents;
        } catch (FileNotFoundException ex) {
            System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
            System.out.println("File not found");
            throw new Exception("SMS contact file missing");
        } catch (NumberFormatException nfex) {
            throw new Exception("Erronous input in SMS contact File");
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(SMSAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
