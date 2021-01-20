/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matej
 */
public class DBBroker {

    private static DBBroker instance;
    private Connection connection;

    private DBBroker() {

    }

    public static DBBroker getInstance() {
        if (instance == null) {
            instance = new DBBroker();
        }

        return instance;
    }

    public void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/test2";
                String username = "root";
                String password = "";

                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);
                System.out.println("db connected");

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ///////////  user  /////////////////
    public List<User> getAllUsers() {

        try {
            String q = "select * from user";

            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(q);

            List<User> lista = new LinkedList<>();

            while (rs.next()) {
                User novi = new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );

                lista.add(novi);
            }

            rs.close();
            st.close();

            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

   
}
