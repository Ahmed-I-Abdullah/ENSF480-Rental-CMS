package src.main.model.property;
import src.main.model.user.RegisteredRenter; // when connected package add this
import src.main.model.user.Landlord; 
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
//import javax.activation.*;

public class Email {
    private RegisteredRenter renter;
    private Property interestedIn;
    private String messageBody;

    public Email(RegisteredRenter renter, Property interestedIn) {
        this.renter = renter;
        this.interestedIn = interestedIn;
    }

    public Email(RegisteredRenter renter, Property interestedIn, String message) {
        this.renter = renter;
        this.interestedIn = interestedIn;
        this.messageBody = message;
    }

    public boolean sendMessage(){
        /* Reference: https://www.tutorialspoint.com/java/java_sending_email.htm */
        String to = renter.getEmail(); // Recipient's email ID
        String from = getPropertyOwner().getEmail();  // Sender's email ID
        String host = "localhost"; // Assuming you are sending email from localhost
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host); // Setup mail server        
        Session session = Session.getDefaultInstance(properties); // Get the default Session object.

        try {
            MimeMessage message = new MimeMessage(session); // Create a default MimeMessage object.
            message.setFrom(new InternetAddress(from)); // Set From: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // Set To: header field of the header.
            String subject = "Renter " + renter.getName() + "is interested in your property #: " + interestedIn.getHouseID();
            message.setSubject(subject); // Set Subject: header field
            //check if message is null?
            message.setText(messageBody); // Now set the actual message
            Transport.send(message); // Send message
            System.out.println("Sent message successfully....");
            return true;
        } catch (MessagingException mex) {
            //mex.printStackTrace();
            System.out.println("Sorry....message failed to send");
            return false;
        }
    }

    private Landlord getPropertyOwner(){
        return interestedIn.getPostedBy();
    }

    public RegisteredRenter getRenter() {
        return this.renter;
    }

    public void setRenter(RegisteredRenter renter) {
        this.renter = renter;
    }

    public Property getInterestedIn() {
        return this.interestedIn;
    }

    public void setInterestedIn(Property interestedIn) {
        this.interestedIn = interestedIn;
    }

    public String getMessage() {
        return this.messageBody;
    }

    public void setMessage(String message) {
        this.messageBody = message;
    }

}
