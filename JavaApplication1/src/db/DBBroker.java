/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import Enum.Pol;
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
                String url = "jdbc:mysql://localhost:3306/test1";
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
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getDate("birthday"));

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

    public void addUser(User user) throws SQLException {

        String q = "insert into user(id, username, password, name, birthday) values(?,?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(q);
        ps.setInt(1, user.getId());
        ps.setString(2, user.getUsername());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getName());
        ps.setDate(5, new Date(user.getBirthday().getTime()));

        ps.executeUpdate();
        ps.close();
    }

    public void updateUser(User user) throws SQLException {

        String q = "update user"
                + " set"
                + " username=?,"
                + " password=?,"
                + " name=?,"
                + " birthday=?"
                + " where id=?";

        PreparedStatement ps = connection.prepareStatement(q);

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getName());
        ps.setDate(4, new Date(user.getBirthday().getTime()));
        ps.setInt(5, user.getId());

        ps.executeUpdate();
        ps.close();
    }

    public void deleteUser(User user) throws SQLException {
        String q = "delete from user where id=?";

        PreparedStatement ps = connection.prepareStatement(q);
        ps.setInt(1, user.getId());

        ps.executeUpdate();
        ps.close();
    }

    //////////  roleuser  //////////////////
    public List<String> getUserRoles(User user) {

        try {
            String q = "select r.name as name"
                    + " from roleuser ru"
                    + " join role r on ru.roleId = r.id"
                    + " where ru.userId = " + user.getId();

            System.out.println(q);

            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(q);

            List<String> lista = new LinkedList<>();

            while (rs.next()) {
                lista.add(rs.getString("name"));
            }

            rs.close();
            s.close();

            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    ////////// enum /////////
    public Pol getOneEnum() {

        try {
            String q = "select pol from test where id = 1";
            Statement st;

            st = connection.createStatement();
            ResultSet rs = st.executeQuery(q);
            Pol p = null;
          
            if (rs.next()) {
                p = Pol.valueOf(rs.getString("pol"));
            }
            rs.close();
            st.close();

            return p;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
