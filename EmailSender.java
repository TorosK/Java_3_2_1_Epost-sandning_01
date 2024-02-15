import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * EmailSender class to send email messages.
 * This class demonstrates how to send an email with authentication and SSL/TLS encryption.
 */
public class EmailSender {

    /**
     * Sends an email using the provided parameters.
     *
     * @param host       SMTP server host.
     * @param port       SMTP server port.
     * @param username   Email account username for authentication.
     * @param password   Email account password for authentication.
     * @param to         Recipient's email address.
     * @param subject    Email subject.
     * @param message    Email message body.
     */
    public void sendEmail(String host, String port, final String username, final String password,
                          String to, String subject, String message) {
        // Set properties for the email session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true"); // Use 'mail.smtp.starttls.enable' for TLS
        properties.put("mail.smtp.timeout", "5000");
        properties.put("mail.smtp.connectiontimeout", "5000");

        // Create a session with authentication
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object
            Message mimeMessage = new MimeMessage(session);

            // Set From: header field
            mimeMessage.setFrom(new InternetAddress(username));

            // Set To: header field
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set Subject: header field
            mimeMessage.setSubject(subject);

            // Now set the actual message
            mimeMessage.setText(message);

            // Send message
            Transport.send(mimeMessage);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        EmailSender sender = new EmailSender();
        // Example usage
        sender.sendEmail("smtp.gmail.com", "465", "yourEmail@gmail.com", "yourPassword",
                         "recipient@example.com", "Test Subject", "Hello, this is a test email.");
    }
}
