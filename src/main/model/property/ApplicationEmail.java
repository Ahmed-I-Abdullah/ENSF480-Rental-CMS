package src.main.model.property;

import src.main.model.user.Landlord;
import src.main.model.property.Property;
import src.main.model.user.RegisteredRenter;
import com.sendgrid.*;
import java.io.IOException;

public class ApplicationEmail {

  private Property interestedIn;
  private String message;
  private String subject;

  public ApplicationEmail(Property interestedIn) {
    this.interestedIn = interestedIn;
  }

  public ApplicationEmail(Property interestedIn, String subject, String message) {
    this.interestedIn = interestedIn;
    this.message = message;
    this.subject = subject;
  }

  public boolean sendMessage() {
    Email from = new Email("ensf480rental@gmail.com");
    Email to = new Email(interestedIn.getPostedBy());
    Content content = new Content(
      "text/plain",
      message
    );
    Mail mail = new Mail(from, subject, to, content);

    SendGrid sendGrid = new SendGrid(
      "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"
    );
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sendGrid.api(request);
    } catch (IOException e) {
        e.printStackTrace();
      return false;
    }

    return true;
  }

  public Property getInterestedIn() {
    return this.interestedIn;
  }

  public void setInterestedIn(Property interestedIn) {
    this.interestedIn = interestedIn;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }
}