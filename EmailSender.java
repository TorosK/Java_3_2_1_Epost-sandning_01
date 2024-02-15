import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Provides functionality to send email messages using SMTP protocol.
 * This class leverages JavaMail API to configure and send emails based on user-defined settings
 * such as SMTP server details, authentication credentials, recipient, subject, and message body.
 */
public class EmailSender {

    /**
     * Entry point of the program. Parses command line arguments and sends an email.
     *
     * @param args Array of command line arguments.
     *             Expected arguments in order: SMTP host, SMTP port, username, password, recipient email, email subject, email message.
     */
    public static void main(String[] args) {
        // Check if the correct number of arguments are provided
        if (args.length < 7) {
            System.out.println("Usage: java EmailSender <host> <port> <username> <password> <to> <subject> <message>");
            System.exit(1); // Exit if arguments are insufficient
        }

        // Extract email configuration and content from command line arguments
        String host = args[0];
        String port = args[1];
        String username = args[2];
        String password = args[3];
        String to = args[4];
        String subject = args[5];
        String message = args[6];

        // Send the email with provided details
        sendEmail(host, port, username, password, to, subject, message);
    }

    /**
     * Configures email session and sends an email message.
     *
     * @param host     Hostname of the SMTP server.
     * @param port     Port number of the SMTP server.
     * @param username Username for SMTP authentication.
     * @param password Password for SMTP authentication.
     * @param to       Recipient's email address.
     * @param subject  Subject of the email.
     * @param message  Body of the email.
     */
    public static void sendEmail(String host, String port, final String username, final String password,
                                 String to, String subject, String message) {
        // Setup mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host); // SMTP host
        properties.put("mail.smtp.port", port); // SMTP port
        properties.put("mail.smtp.auth", "true"); // Enable authentication
        properties.put("mail.smtp.ssl.enable", "true"); // Enable SSL for secure email
        properties.put("mail.smtp.timeout", "5000"); // Set connection timeout
        properties.put("mail.smtp.connectiontimeout", "5000"); // Set I/O timeout

        // Create a session with authentication using the provided username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password); // Authenticate the session
            }
        });

        try {
            // Prepare the email message
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username)); // Sender's email
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // Recipient's email
            mimeMessage.setSubject(subject); // Subject of the email
            mimeMessage.setText(message); // Content of the email

            // Send the email
            Transport.send(mimeMessage);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace(); // Print stack trace in case of exception
        }
    }
}
