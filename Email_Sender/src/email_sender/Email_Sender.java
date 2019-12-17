/////////////////////////////////// Jacob Shea ///////////////////////////////////
///////////////////////////////// Networks Final /////////////////////////////////
////////////////////////////////// Email Sender //////////////////////////////////

package email_sender;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

public class Email_Sender {
	public static void main(String[] args) {
            
                // Values used for the email
                // TODO: change final Strings below to desired values
                final String recvEmail = "receiver@gmail.com";     // Email to send  
		final String senderEmail = "username@gmail.com";   // Email used for sending
		final String password = "email_password";             // Password for the email used for authentication 
		final String subject = "Subject";  // Subject of the email 
                final String body = "Text body for email"; // The text in the body of the email 
              
                // Creates a properties object 
		Properties props = new Properties();
                
                // Sets the properties for the email session                 
		props.put("mail.smtp.host", "smtp.gmail.com");      //SMTP Host
		props.put("mail.smtp.socketFactory.port", "465");   //SSL Port
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
		props.put("mail.smtp.auth", "true");    //Enabling SMTP Authentication 
		
                // Signs into the email using the password
		Authenticator auth;
                auth = new Authenticator() {
                    
                //override the getPasswordAuthentication method
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, password);
                }
            };
		
                // Creates a mail session for the mail API 
		Session session = Session.getDefaultInstance(props, auth);
		System.out.println("Session created");
                
                // Sending the email to each recipient with the same subject and body
	        sendEmail(session, recvEmail, subject, body);
                
        }
        
        // The sendEmail function will send an email with its own unique specifications
        // Parameters: session, the string email address it is being sent to,
        // a subject, and body text for the email
        // Returns: N/A
        public static void sendEmail(Session session, String recvEmail, String subject, String body){
		try
	    {
              // Creates a message object for us
	      MimeMessage msg = new MimeMessage(session);
              
	      //Sets the message header semantics
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

              // Sets the attributes for the email message
	      msg.setSubject(subject, "UTF-8"); // Subject line
	      //msg.setText(body, "UTF-8");       // Body text
	      msg.setSentDate(new Date());      // Sets date & time the email is sent 
              msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recvEmail, false));   // Sets where the email is being sent

              // Create the message body for the email
              BodyPart messageBodyPart = new MimeBodyPart();
              
              // Creates a multipart message
              // Allows for text and image to coexist 
              Multipart multipart = new MimeMultipart();
              
              // Sets the text for the message and adds it
              messageBodyPart.setText(body);
              multipart.addBodyPart(messageBodyPart);
              
              // Attaches the image 
              messageBodyPart = new MimeBodyPart();
              DataSource source = new FileDataSource("breakfast_burrito.png");
              messageBodyPart.setDataHandler(new DataHandler(source));
              messageBodyPart.setFileName("breakfast_burrito.png");
              messageBodyPart.setHeader("Content-ID", "imgage_id");
              multipart.addBodyPart(messageBodyPart);

              //Displays the image 
              messageBodyPart = new MimeBodyPart();
              messageBodyPart.setContent("<h1> Attatched Image</h1>" +
        		     "<img src='cid:breakfast_burrito.png'>", "text/html");
              multipart.addBodyPart(messageBodyPart);
         
              //Set the multipart message to the email message
              msg.setContent(multipart);

              
	      // Sends the email
              Transport.send(msg);  
              
              // Lets us know that the email was successfully sent
	      System.out.println("EMail Sent Successfully!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    } 
	}
}

