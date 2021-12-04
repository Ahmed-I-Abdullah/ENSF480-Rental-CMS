package src.main.view;

import java.awt.Graphics;
import javax.swing.*;

import src.main.controller.ViewController;
import src.main.controller.UserNotFoundException;
import src.main.controller.UnAuthorizedException;

public class MainPage extends Page {
  String welcomeMessage = "Hello";
  static String userName = "";
  static String loginErrorMessage = "";

  public MainPage(Widget w, ViewController c) {
    super(c);
    widget = w;
    resetSwitchEvent();
  }

  public void draw() {
    JLabel prompt = new JLabel("Enter Username and Password");
    prompt.setBounds(600, 70, 200, 30);

    JTextField userN = new JTextField();
    JLabel un = new JLabel("Username");
    un.setBounds(600, 100, 80, 30);
    userN.setBounds(670, 100, 100, 30);

    JPasswordField pass = new JPasswordField();
    JLabel pw = new JLabel("Password");
    pw.setBounds(600, 150, 80, 30);
    pass.setBounds(670, 150, 100, 30);

    JButton button = new JButton("Sign in");
    button.setBounds(670, 200, 70, 25);

    JButton browse = new JButton("Browse");
    browse.setBounds(100, 120, 100, 50);

    browse.addActionListener(
      e -> {
        f.setVisible(false);
        f.getContentPane().removeAll();
        switchEvent = 1;
      }
    );

    button.addActionListener(
      e -> {
        String username = userN.getText();
        String password = new String(pass.getPassword());
        if (MainPage.login(username, password)) {
          welcomeMessage += " " + userName;
          f.setVisible(false);
          f.getContentPane().remove(this);
          f.getContentPane().remove(pass);
          f.getContentPane().remove(pw);
          f.getContentPane().remove(userN);
          f.getContentPane().remove(un);
          f.getContentPane().remove(button);
          f.getContentPane().remove(prompt);
          f.getContentPane().add(this);
          f.setVisible(true);
        } else {
          prompt.setText(loginErrorMessage);
          pass.setText("");
          userN.setText("");
        }
      }
    );
    pass.addActionListener(
      e -> {
        String username = userN.getText();
        String password = new String(pass.getPassword());
        if (MainPage.login(username, password)) {
          welcomeMessage += " " + userName;
          f.setVisible(false);
          f.getContentPane().remove(this);
          f.getContentPane().remove(pass);
          f.getContentPane().remove(pw);
          f.getContentPane().remove(userN);
          f.getContentPane().remove(un);
          f.getContentPane().remove(button);
          f.getContentPane().remove(prompt);
          f.getContentPane().add(this);
          f.setVisible(true);
        } else {
          prompt.setText(loginErrorMessage);
          pass.setText("");
          userN.setText("");
        }
      }
    );

    f.add(userN);
    f.add(un);
    f.add(pass);
    f.add(pw);
    f.add(button);
    f.add(prompt);
    f.add(browse);

    f.getContentPane().add(this);
    f.setVisible(true);
  }

  public void paintComponent(Graphics g) {
    //System.out.println("paint");
    widget = new Text(300, 40, "RENT A PLACE");
    g.setFont(titleFont);
    widget.draw(g);
    widget = new Border(widget, 0, 0, 785, 762, 10);
    widget.draw(g);
    g.setFont(mainText);
    widget = new Text(300, 100, welcomeMessage);
    widget.draw(g);
  }

  public static boolean login(String email, String password) {
	try {
		controller.getUserController().logIn(email, password);
	} catch(UserNotFoundException u) {
		loginErrorMessage = "User Not Found";
		return false;
	} catch(UnAuthorizedException ua) {
		loginErrorMessage = "Wrong Password";
		return false;
	} catch(Exception e) {
		e.printStackTrace();
		loginErrorMessage = "Server error, try again.";
		return false;
	}
	
	userName = controller.getUserController().getAuthenticatedUser().getName();
	return true;
  }
}
