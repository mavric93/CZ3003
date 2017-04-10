/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broadcast.Agent;

import Broadcast.util.Broadcastable;
import Broadcast.util.GroupPostable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.ProcessBuilder.Redirect.from;
import static java.lang.ProcessBuilder.Redirect.to;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author zhijie
 */
public class EmailAgent implements Broadcastable, GroupPostable {

    private final static String CONTACTSFILE = "../etc/emailContacts.csv";//"src/Broadcast/etc/emailContacts.csv";
    private final static String USERNAME = "cmsystem007@gmail.com";
    private final static String PASSWORD = "system007";

    @Override
    public void broadcast(String message) throws Exception {
        ArrayList<String> subscriberEmails = readContacts();

        for (String recipient : subscriberEmails) {
            sendToGroup(message, recipient);
            System.out.println("Recipient:" + recipient);
        }
        System.out.println("Email broadcast completed!");

    }

    @Override
    public void sendToGroup(String messageContent, String recipentGroup) throws Exception {
        for (String recipent : readContacts(recipentGroup)) {
            send(messageContent, "This is a message from CMS system", recipent);
        }
    }

    public void send(String message, String subject, String recipent) throws Exception {
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
        content.setSubject("Email from ");
        content.setText(message);

        Transport.send(content);
    }

    public void sendWithAttachment(ArrayList<String> filePaths, String subject, String recipent) {
        // Assuming you are sending email through relay.jangosmtp.net
        String host = "relay.jangosmtp.net";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress("cmsystem007@gmail.com"));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipent));

            // Set Subject: header field
            message.setSubject("Email from CMS ");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("Update from CMS");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            for (String filepath : filePaths) {
                // Set text message part
                System.out.println(filepath);

                // Part two is attachment
                messageBodyPart = new MimeBodyPart();
                String filename = filepath;
                DataSource source = new FileDataSource(filename);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename);
                multipart.addBodyPart(messageBodyPart);
            }
            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Sent email successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<String> readContacts(String recipientGrp) throws Exception {
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

                System.out.println(line);
                if (recipientGrp == null) {
                    recipents.add(recipentsEmail);
                } else if (recipientGrp.equals(lineArr[2].trim())) {
                    recipents.add(recipentsEmail);
                }
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

    private ArrayList<String> readContacts() throws Exception {
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
