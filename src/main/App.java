package src.main;

import java.io.FileNotFoundException;
import java.util.Date;

import src.main.controller.AdminController;
import src.main.controller.ControllerManager;
import src.main.controller.UserController;
import src.main.model.user.Manager;
import src.main.model.user.UserType;

import src.main.view.Widget;
import src.main.view.Page;
import src.main.view.MainPage;
import src.main.view.BrowseListingsPage;

public class App {

  public static void main(String[] args) throws FileNotFoundException {
    ControllerManager.connectDatabase();
    ControllerManager.runSQLScript("./src/tables.sql");
    Widget w = null;
    Page p = new MainPage(w);
    String[] welcome = { "Hello " };
    String[] Listings = {
      "1234 SunHarbor Cresant",
      "47 Sommervale Drive",
      "14 North Drive SW Apt. 7",
      "88 Los Almos Blvd",
      "191 Leninskya Street Apt. 41",
    };
    p.draw(welcome);

    while (p.getSwitchEvent() == 0) {
      System.out.println("");
    }
    if (p.getSwitchEvent() == 1) {
      p = new BrowseListingsPage(w);
      p.draw(Listings);
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
}
