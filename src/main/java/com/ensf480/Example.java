package com.ensf480;

import com.sendgrid.*;
import java.io.IOException;

public class Example {

  public static void sendEmail() {
    Email from = new Email("ensf480rental@gmail.com");
    String subject = "Test Email";
    Email to = new Email("hudaabbas1234@gmail.com");
    Content content = new Content(
      "text/plain",
      "This Email is sent from the application!"
    );
    Mail mail = new Mail(from, subject, to, content);

    SendGrid sg = new SendGrid(
      "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"
    );
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
