package src.main.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ControllerManager {

  private static final String DB_DRIVER = "postgresql";
  private static final String DB_HOST =
    "ec2-50-17-255-244.compute-1.amazonaws.com";
  private static final String DB_NAME = "ddkcocueff92q2";
  private static final String DB_PASSWORD =
    "d276c5e635c3485311bc43378ac99a9cb5e2f1778d46f31a53bc5c31869870ea";
  private static final String DB_USER = "ayefdajlveiume";
  private static final String DB_PORT = "5432";
  private static Connection databaseConnection;

  /**
   * Establishes connection with remote database and
   * stores Connection instance
   */
  public static void connectDatabase() {
    try {
      String url = String.format(
        "jdbc:%s://%s:%s/%s",
        DB_DRIVER,
        DB_HOST,
        DB_PORT,
        DB_NAME
      );
      databaseConnection =
        DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
        System.out.println("Connection to database successful");
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  /**
   * Gets the database connection instance
   * @return A Connection object to the remote database
   */
  public static Connection getConnection() {
    return databaseConnection;
  }

  /**
   * Executes a given sql file on the remote database
   * @param filePath String representing the path of the sql file
   */
  public static void runSQLScript(String filePath)
    throws FileNotFoundException {
    File inputFile = new File(filePath);
    Scanner fileScanner = new Scanner(inputFile);
    fileScanner.useDelimiter("(;(\r)?\n'\")|(--\n)");

    try {
      Statement statement = databaseConnection.createStatement();
      while (fileScanner.hasNext()) {
        String line = fileScanner.next();
        if (line.trim().length() > 0) {
          statement.execute(line);
        }
      }
      statement.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
    fileScanner.close();
  }

  /**
   * Executes a given sql query on the remote database
   * @param filePath String representing query
   * @throws SQLException
   */
  public static void executeQuery(String query) throws SQLException {
    try {
      Statement statement = databaseConnection.createStatement();
      statement.execute(query);
      statement.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }
}
