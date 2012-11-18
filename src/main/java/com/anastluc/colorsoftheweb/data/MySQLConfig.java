package com.anastluc.colorsoftheweb.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author lucas
 */
public class MySQLConfig {

    public static String serverName;// = "localhost:3306";
    public static String database;// = "core";
    public static String username;// = "root";
    public static String password;// = "localhost";//abcd";
    
    private Connection connection;

    public MySQLConfig(){
        setConnectionFromPropertyFile();
    }
    
    public void setConnectionFromPropertyFile() {
        Properties pr = new Properties();
        InputStream propsFile;
        String propertyPath = getClass().getResource("/properties.properties").getPath();
        try {            
                propsFile = new FileInputStream(propertyPath);
                pr.load(propsFile);
                propsFile.close();
            } catch (IOException ioe) {                
                System.err.println("I/O Exception while loading properties: " + ioe.getMessage());
            }
        if (!pr.getProperty("SERVER_NAME").isEmpty()) {
            serverName = pr.getProperty("SERVER_NAME");
        }

        if (!pr.getProperty("DATABASE").isEmpty()) {
            database = pr.getProperty("DATABASE");
        }

        if (!pr.getProperty("USERNAME").isEmpty()) {
            username = pr.getProperty("USERNAME");
        }

        if (pr.getProperty("PASSWORD") != null) {
            password = pr.getProperty("PASSWORD");
        }
    }

    private void setConnection(String serverName, String database, String username, String password) {
        this.connection = null;
        try {
            String driver = "com.mysql.jdbc.Driver"; 
            Class.forName(driver);
            String url = "jdbc:mysql://" + serverName + "/" + database + "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";

            this.connection = DriverManager.getConnection(url, username, password);
            
        } catch (ClassNotFoundException e) {
            System.err.println("Connection cannot be found");
        } catch (SQLException e) {
            System.err.println("Cannot connect to database");
            System.err.println("Connecting as db:" + database + " user:" + username + 
                    " server:" + serverName);
            System.err.println(e.getMessage());

        }
    }
    
    public Connection getConnection() {
        try {
            if ((this.connection == null) || (!this.connection.isValid(1))) {
                setConnection(MySQLConfig.serverName, MySQLConfig.database, MySQLConfig.username, MySQLConfig.password);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return this.connection;
    }
    
    public static String showParameters() {

        String s = "server: " + serverName + "\ndatabase: " + database +
                "\nusername: " + username + "\npassword: " + password;

        return s;

    }
    
} 