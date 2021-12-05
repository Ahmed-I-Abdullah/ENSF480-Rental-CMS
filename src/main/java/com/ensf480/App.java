package com.ensf480;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import com.ensf480.controller.AdminController;
import com.ensf480.controller.ControllerManager;
import com.ensf480.controller.ViewController;
import com.ensf480.controller.UserController;
import com.ensf480.model.user.Manager;
import com.ensf480.model.user.UserType;
import com.ensf480.model.property.ApplicationEmail;

import com.ensf480.view.Widget;
import com.ensf480.view.Page;
import com.ensf480.view.MainPage;
import com.ensf480.view.BrowseListingsPage;

public class App {

  public static void main(String[] args) {
    ControllerManager.connectDatabase();

    // try {
    //   ControllerManager.runSQLScript("./src/tables.sql");
    // } catch(Exception e) {
    //   e.printStackTrace();
    // }
    
    ViewController v = new ViewController();
    Widget w = null;
    Page p = new MainPage(w, v);
    p.draw();

    while (p.getSwitchEvent() == 0) {
      System.out.println("");
    }
    if (p.getSwitchEvent() == 1) {
      p = new BrowseListingsPage(w, v);
      p.draw();
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
