package src.main;

import src.main.controller.ControllerManager;
import src.main.controller.AdminController;
import src.main.controller.UserController;
import src.main.model.user.Manager;
import java.io.FileNotFoundException;
import java.util.Date;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        ControllerManager.connectDatabase();
        ControllerManager.runSQLScript("./src/tables.sql");
        Manager m = new Manager("Ahmed");

        try {
            AdminController am = new AdminController(m);
            long t = System.currentTimeMillis() - (3*24*60*60*1000);
            long t2 = System.currentTimeMillis() + (3*24*60*60*1000);
            am.generateReport(new Date(t), new Date(t2));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        UserController um = new UserController(m);

        
    }
}