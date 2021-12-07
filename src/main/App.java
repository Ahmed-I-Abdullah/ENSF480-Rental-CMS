package src.main;

import java.io.FileNotFoundException;
import src.main.controller.AdminController;
import src.main.controller.ControllerManager;
import src.main.controller.UserController;
import src.main.controller.ViewController;
import src.main.view.*;
import javax.swing.*;

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
	
	int c=0;
    while (true) {
		System.out.println("run #:"+ c);
		c++;
      if (p.getSwitchEvent() == 0 && !firstDisplay) {
        p = new MainPage(w, v);
        p.draw();
      }
      while (p.getSwitchEvent() == 0) {}
      if (p.getSwitchEvent() == 1) {
        p = new BrowseListingsPage(w, v);
        p.draw();
      }
      while (p.getSwitchEvent() == 1) {}

      if (p.getSwitchEvent() == 2) {
        p = new CreateListingsPage(w, v);
        p.draw();
      }

      while (p.getSwitchEvent() == 2) {}

      if (p.getSwitchEvent() == 3) {
        p = new ManagerControlPage(w, v);
        p.draw();
      }
      while (p.getSwitchEvent() == 3) {}

      if (p.getSwitchEvent() == 4) {
        p = new ManagerReportPage(w, v);
        p.draw();
      }

      while (p.getSwitchEvent() == 4) {}

      if (p.getSwitchEvent() == 5) {
        p = new NotificationsPage(w, v);
        p.draw();
      }

      while (p.getSwitchEvent() == 5) {}

      if (p.getSwitchEvent() == 6) {
        p = new ListingsPage(w, v);
        p.draw();
      }

      while (p.getSwitchEvent() == 6) {}

      firstDisplay = false;
    }
  }
}
