package src.main.model.property;

import com.sendgrid.Email;
import com.sendgrid.Content;
import com.sendgrid.Mail;
import com.sendgrid.SendGrid;
import com.sendgrid.Request;
import com.sendgrid.Method;
import java.io.IOException;

public class ApplicationEmail {

  private Property interestedIn;
  private String message;
  private String subject;

  //PROMISES: creates ApplicationEmail object
  //REQUIRES: the property the user is interested in that they want to send the email for
  public ApplicationEmail(Property interestedIn) {
    this.interestedIn = interestedIn;
  }

  //PROMISES: creates ApplicationEmail object
  //REQUIRES: the property the user is interested in, the email body and email subject
  public ApplicationEmail(Property interestedIn, String subject, String message) {
    this.interestedIn = interestedIn;
    this.message = message;
    this.subject = subject;
  }

  //PROMISES: sends a message to the owner of the property using twilio
  public boolean sendMessage() {
    Email from = new Email("ensf480rental@gmail.com");
    Email to = new Email(interestedIn.getPostedBy()); //gets landlords email, but hides it from the user
    Content content = new Content( 
      "text/plain",
      message
    ); //add ApplicationEmail.messsage to Mail content
    Mail mail = new Mail(from, subject, to, content);

    SendGrid sendGrid = new SendGrid(
      "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"
    );
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      sendGrid.api(request); //send Mail message request
    } catch (IOException e) {
        e.printStackTrace();
      return false;
    }

    return true;
  }

  //PROMISES: returns the property the user is wanting to send an email about
  public Property getInterestedIn() {
    return this.interestedIn;
  }

  //PROMISES: sets the property the user is wanting to send an email about
  //REQUIRES: the property the email is about
  public void setInterestedIn(Property interestedIn) {
    this.interestedIn = interestedIn;
  }

  //PROMISES: returns the email message
  public String getMessage() {
    return this.message;
  }

  //PROMISES: sets the email message
  //REQUIRES: the email message the user has typed out
  public void setMessage(String message) {
    this.message = message;
  }

  //PROMISES: returns the subject of the email
  public String getSubject() {
    return this.subject;
  }

  //PROMISES: sets the email subject line
  //REQUIRES: the email subject line
  public void setSubject(String subject) {
    this.subject = subject;
  }
}