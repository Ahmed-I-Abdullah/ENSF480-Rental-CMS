package main.controller;

import java.util.Date;

public class AdminController extends UserController {
    private boolean isAdmin;

    public AdminController(User u) {
        super(u);
    }

    public void generateReport(Date startDate, Date endDate) {
        
    }
}
