package src.main.view;

import java.awt.*;
import java.awt.Graphics;
import javax.swing.*;
import src.main.controller.UnAuthorizedException;
import src.main.controller.UserNotFoundException;
import src.main.controller.ViewController;
import src.main.model.user.UserType;

public class MainPage extends Page {

  String welcomeMessage = "Hello";
  static String userName = "";
  static String loginErrorMessage = "";
  static String registerErrors = "";

  static String emailPlaceHolder = "Email";
  static String passwordPlaceHolder = "Password";
  static String namePlaceHolder = "Name";

  public MainPage(Widget w, ViewController c) {
    super(c);
    widget = w;
    resetSwitchEvent();
  }

  private boolean checkRegisterErrors(
    String inputEmail,
    String inputName,
    String inputPassword
  ) {
    registerErrors = "";
    if (emailPlaceHolder.indexOf(inputEmail) != -1) {
      registerErrors = "Enter an email.";
      return false;
    }

    if (passwordPlaceHolder.indexOf(inputPassword) != -1) {
      registerErrors = "Please enter a password.";
      return false;
    }

    if (namePlaceHolder.indexOf(inputName) != -1) {
      registerErrors = "Please enter a name.";
      return false;
    }

    if (inputPassword.length() < 6) {
      registerErrors = "Password must be more than 5 characters";
      return false;
    }

    return true;
  }

  public void draw() {
    final JLabel prompt = new JLabel("Enter Email and Password");
    prompt.setBounds(600, 70, 200, 30);

    final JTextField userN = new JTextField();
    final JLabel un = new JLabel("Email");
    un.setBounds(600, 100, 80, 30);
    userN.setBounds(670, 100, 100, 30);

    final JPasswordField pass = new JPasswordField();
    final JLabel pw = new JLabel("Password");
    pw.setBounds(600, 150, 80, 30);
    pass.setBounds(670, 150, 100, 30);

    final JButton button = new JButton("Sign in");
    button.setBounds(670, 200, 70, 25);

    final JButton Sinbutton = new JButton("Register");
    Sinbutton.setBounds(670, 225, 100, 25);

    final JButton browse = new JButton("Browse");
    browse.setBounds(100, 120, 100, 50);

    final JButton manage = new JButton("Manage");
    manage.setBounds(100, 220, 100, 50);

    final JButton post = new JButton("Post Property");
    post.setBounds(100, 170, 100, 50);

    final JButton signout = new JButton("Sign out");
    signout.setBounds(670, 200, 100, 25);

    final JButton notifications = new JButton("Notifications");
    notifications.setBounds(100, 170, 100, 50);

    notifications.addActionListener(
      e -> {
        f.setVisible(false);
        f.getContentPane().removeAll();
        switchEvent = 5;
      }
    );

    browse.addActionListener(
      e -> {
        f.setVisible(false);
        f.getContentPane().removeAll();
        switchEvent = 1;
      }
    );

    post.addActionListener(
      e -> {
        f.setVisible(false);
        f.getContentPane().removeAll();
        switchEvent = 2;
      }
    );

    manage.addActionListener(
      e -> {
        f.setVisible(false);
        f.getContentPane().removeAll();
        switchEvent = 3;
      }
    );

    Sinbutton.addActionListener(
      e -> {
        JFrame pop = new JFrame("Register");
        pop.setSize(290, 250);
        pop.setLocationRelativeTo(null);
        pop.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JButton register = new JButton("Register");

        final JLabel errors = new JLabel(registerErrors);
        errors.setForeground(Color.red);

        final JTextField email = new JTextField(emailPlaceHolder);

        final JTextField name = new JTextField(namePlaceHolder);

        final JTextField password = new JTextField(passwordPlaceHolder);

        String[] select = { "Renter", "Landlord" };

        JLabel typeLabel = new JLabel("Are you a:");
        final JComboBox<String> type = new JComboBox<String>(select);
        type.setSelectedIndex(0);

        register.addActionListener(
          p -> {
            if (
              checkRegisterErrors(
                email.getText(),
                name.getText(),
                password.getText()
              ) &&
              signup(
                email.getText(),
                name.getText(),
                password.getText(),
                type.getSelectedIndex()
              )
            ) {
              welcomeMessage += " " + userName;
              f.setVisible(false);
              f.getContentPane().remove(this);
              f.getContentPane().remove(pass);
              f.getContentPane().remove(pw);
              f.getContentPane().remove(userN);
              f.getContentPane().remove(un);
              f.getContentPane().remove(button);
              f.getContentPane().remove(prompt);
              f.getContentPane().remove(Sinbutton);
              f.getContentPane().add(signout);
              f.getContentPane().add(this);
              f.setVisible(true);
              pop.setVisible(false);
              if (
                controller
                  .getUserController()
                  .getAuthenticatedUser()
                  .getUserType() ==
                UserType.RENTER
              ) {
                f.add(notifications);
              }
              if (
                controller
                  .getUserController()
                  .getAuthenticatedUser()
                  .getUserType() ==
                UserType.MANAGER
              ) {
                f.add(post);
                f.add(manage);
              }
              if (
                controller
                  .getUserController()
                  .getAuthenticatedUser()
                  .getUserType() ==
                UserType.LANDLORD
              ) {
                f.add(post);
              }
            } else {
              errors.setText(registerErrors);
            }
          }
        );
        c.weighty = 1.0;
        c.weightx = 0.5;
        c.ipadx = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        pop.add(errors, c);

        c.gridx = 0;
        c.gridy = 1;
        pop.add(email, c);

        c.gridx = 0;
        c.gridy = 2;
        pop.add(password, c);

        c.gridx = 1;
        c.gridy = 1;
        pop.add(name, c);

        c.gridx = 0;
        c.gridy = 3;
        pop.add(typeLabel, c);

        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 4;
        pop.add(type, c);

        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 5;
        pop.add(register, c);

        pop.setVisible(true);
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
          f.getContentPane().add(signout);
          f.getContentPane().add(this);
          f.setVisible(true);
          f.getContentPane().remove(Sinbutton);
          if (
            controller
              .getUserController()
              .getAuthenticatedUser()
              .getUserType() ==
            UserType.RENTER
          ) {
            f.add(notifications);
          }
          if (
            controller
              .getUserController()
              .getAuthenticatedUser()
              .getUserType() ==
            UserType.MANAGER
          ) {
            f.add(post);
            f.add(manage);
          }
          if (
            controller
              .getUserController()
              .getAuthenticatedUser()
              .getUserType() ==
            UserType.LANDLORD
          ) {
            f.add(post);
          }
        } else {
          prompt.setText(loginErrorMessage);
          pass.setText("");
          userN.setText("");
        }
      }
    );
    signout.addActionListener(
      e -> {
        welcomeMessage = "Hello ";
        controller.getUserController().setAuthenticatedUser(null);
        f.setVisible(false);
        f.getContentPane().remove(this);
        f.getContentPane().add(pass);
        f.getContentPane().add(pw);
        f.getContentPane().add(userN);
        userN.setText("");
        pass.setText("");
        f.getContentPane().add(un);
        f.getContentPane().add(button);
        f.getContentPane().add(prompt);
        f.getContentPane().add(Sinbutton);
        f.getContentPane().remove(signout);
        f.getContentPane().remove(manage);
        f.getContentPane().remove(post);
        f.getContentPane().remove(notifications);
        f.getContentPane().add(this);
        f.setVisible(true);
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

    if (controller.getUserController().getAuthenticatedUser() == null) {
      f.add(userN);
      f.add(un);
      f.add(pass);
      f.add(pw);
      f.add(button);
      f.add(prompt);
      f.add(Sinbutton);
    } else {
      if (
        controller.getUserController().getAuthenticatedUser().getUserType() ==
        UserType.RENTER
      ) {
        f.add(notifications);
      }
      if (
        controller.getUserController().getAuthenticatedUser().getUserType() ==
        UserType.MANAGER ||
        controller.getUserController().getAuthenticatedUser().getUserType() ==
        UserType.LANDLORD
      ) {
        f.add(post);
        if (
          controller.getUserController().getAuthenticatedUser().getUserType() ==
          UserType.MANAGER
        ) {
          f.add(manage);
        }
      }
    }
    f.add(browse);
    f.getContentPane().add(this);
    f.setVisible(true);
  }

  public void paintComponent(Graphics g) {
    widget = new Text(230, 40, "RENT A PLACE");
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
    } catch (UserNotFoundException u) {
      loginErrorMessage = "User Not Found";
      return false;
    } catch (UnAuthorizedException ua) {
      loginErrorMessage = "Wrong Password";
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      loginErrorMessage = "Server error, try again.";
      return false;
    }

    userName = controller.getUserController().getAuthenticatedUser().getName();
    return true;
  }

  public static boolean signup(
    String email,
    String name,
    String password,
    int usertypeIndex
  ) {
    UserType uType = UserType.RENTER;
    if (usertypeIndex == 1) {
      uType = UserType.LANDLORD;
    }
    try {
      controller.getUserController().signUp(email, name, password, uType);
    } catch (Exception ex) {
      ex.printStackTrace();
      registerErrors = "Email already used.";
      return false;
    }

    userName = controller.getUserController().getAuthenticatedUser().getName();
    return true;
  }
}
