package com.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;
import java.util.Properties;

import static java.util.Arrays.asList;

public class MailMonitoringService {

    private static Logger LOGGER = LoggerFactory.getLogger(MailMonitoringService.class);


    private Properties getServerProperties(String protocol, String host, String port) {
        Properties properties = new Properties();
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
        properties.put(String.format("mail.%s.socketFactory.class", protocol), "javax.net.ssl.SSLSocketFactory");
        properties.put(String.format("mail.%s.socketFactory.fallback", protocol), "false");
        properties.put(String.format("mail.%s.socketFactory.port", protocol), String.valueOf(port));

        return properties;
    }

    public void getNewEmails(String protocol, String host, String port, String userName, String password) {
        Properties properties = getServerProperties(protocol, host, port);
        Session session = Session.getDefaultInstance(properties);

        try {
            Store store = session.getStore(protocol);
            store.connect(userName, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] unread = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            LOGGER.info("Total number of messages: " + inbox.getMessageCount());
            int count = inbox.getMessageCount();
            LOGGER.info("Total number of unread messages: " + inbox.getUnreadMessageCount());

            asList(unread).stream()
                    .forEach(this::read);

            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for protocol: " + protocol);
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        }
    }


    private void read(Message message) {
        try {
            Address[] fromAddresses = message.getFrom();
            LOGGER.info("...................");
            LOGGER.info("From: " + fromAddresses[0].toString());
            LOGGER.info("To: " + parseAddresses(message.getRecipients(RecipientType.TO)));
            LOGGER.info("CC: " + parseAddresses(message.getRecipients(RecipientType.CC)));
            LOGGER.info("Subject: " + message.getSubject());
            LOGGER.info("Sent Date:" + message.getSentDate().toString());
            LOGGER.info("Sent Date:" + message.getSentDate().toString());
            LOGGER.info("Seen:" + message.getFlags().contains(Flags.Flag.SEEN));
//            message.setFlag(Flags.Flag.SEEN, true);
        } catch (MessagingException e) {
            LOGGER.error("Unable to read message ");
        }
    }

    private String parseAddresses(Address[] address) {

        String listOfAddress = "";
        if ((address == null) || (address.length == 0))
            return null;

        if (!(address[0] instanceof InternetAddress))
            return null;

        for (int i = 0; i < address.length; i++) {
            InternetAddress internetAddress = (InternetAddress) address[i];
            listOfAddress += internetAddress.getAddress() + ",";
        }
        return listOfAddress;
    }

    public static void main(String[] args) {

        MailMonitoringService bean = new MailMonitoringService();
        bean.getNewEmails(
                "imaps",
                "imap.gmail.com",
                "993",
                "xxx",
                "xxx");

    }
}