package src.main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import src.main.controller.AdminController;
import src.main.controller.ControllerManager;
import src.main.controller.ViewController;
import src.main.controller.UserController;
import src.main.model.property.*;
import src.main.model.user.*;
import src.main.view.*;

public class App {

  public static void main(String[] args) throws FileNotFoundException {
    ControllerManager.connectDatabase();
    ControllerManager.runSQLScript("./src/tables.sql");
    ViewController v = new ViewController();
    UserController u = new UserController();
    v.setUserController(u);
    Widget w = null;
    Page p = new MainPage(w, v);
    p.draw();
    boolean firstDisplay = true;


    while(true) {
      if(p.getSwitchEvent() == 0 && !firstDisplay) {
        p = new MainPage(w, v);
        p.draw();
      }
      while (p.getSwitchEvent() == 0) {
        
      }
      if (p.getSwitchEvent() == 1) {
        p = new BrowseListingsPage(w, v);
        p.draw();
      }
      while (p.getSwitchEvent() == 1) {
        
      }
      
      if(p.getSwitchEvent()==3){
        p=new ManagerControlPage(w, v);
        p.draw();
      }
      while(p.getSwitchEvent()==3){}

      if (p.getSwitchEvent() == 2) {
        p = new CreateListingsPage(w, v);
        p.draw();
      }

      while(p.getSwitchEvent()==2){}

      firstDisplay = false;
    }

    // Manager m = new Manager("Ahmed");

    // try {
    //   AdminController am = new AdminController(m);
    //   long t = System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000);
    //   long t2 = System.currentTimeMillis() + (3 * 24 * 60 * 60 * 1000);
    //   am.generateReport(new Date(t), new Date(t2));
    // } catch (Exception e) {
    //   e.printStackTrace();
    //   System.exit(-1);
    // }

    // UserController um = new UserController(m);
    // try {
    //   um.logIn("test@domain.com", "12345678");
    // } catch (Exception e) {
    //   e.printStackTrace();
    //   System.exit(-1);
    // }
     
  }

  // public static void emailTest(){
  //   RegisteredRenter renter = new RegisteredRenter("huda","huda.abbas@ucalgary",null);
  //   Landlord landlord = new Landlord("huda","huda.abbas@ucalgary");
  //   Date date = java.util.Calendar.getInstance().getTime(); //get todays date
  //   ListingDetails property1 = new ListingDetails(ListingState.ACTIVE, 2, "Apartment", false, "NW");
  //   Address address1 = new Address("Calgary","AB","Canada","SunHarbor Cresant",154, "T34 5YR");
  //   Property property = new Property(1, address1, property1, landlord, date, "Stunning property. Waterfront view");
  //   Email email1 = new Email(renter, property, "Can we meet, I'm interested in taking a look!");
  //   email1.sendMessage();
  // }
}
