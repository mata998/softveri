/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableModeli;

import db.DBBroker;
import domen.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import kontroler.Controller;

/**
 *
 * @author matej
 */
public class UseriTableModel extends AbstractTableModel {

    List<User> users;
    String[] kolone = {"id", "username", "password", "name", "birthday"};

    public UseriTableModel() {
        fillTable();
    }

    public void fillTable() {
        users = Controller.getInstance().getAllUsers();
        fireTableDataChanged();
    }

    public User getSelectedUser(int row) {
        return users.get(row);
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        User user = users.get(rowIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        switch (columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getUsername();
            case 2:
                return user.getPassword();
            case 3:
                return user.getName();
            case 4:
                return sdf.format(user.getBirthday());

            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            User user = users.get(rowIndex);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

            switch (columnIndex) {
                case 0:
                    user.setId(Integer.parseInt((String) aValue));
                    break;
                case 1:
                    user.setUsername((String) aValue);
                    break;
                case 2:
                    user.setPassword((String) aValue);
                    break;
                case 3:
                    user.setName((String) aValue);
                    break;
                case 4:
                    user.setBirthday(sdf.parse((String) aValue));
                    break;
                default:
                    System.out.println("default sta?");
            }
        } catch (ParseException ex) {
            Logger.getLogger(UseriTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
