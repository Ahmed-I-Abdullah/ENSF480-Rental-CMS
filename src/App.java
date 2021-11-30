package src;


import src.controller.ControllerManager;
import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        ControllerManager.connectDatabase();
        ControllerManager.runSQLScript("./src/controller/tables.sql");
    }
}