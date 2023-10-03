package Gui.Popups;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Inet4Address;
import java.sql.*;
import Gui.Staff;
import Sql.Sql;
import java.sql.Date;
import Sql.Password;
import javax.swing.*;

public class Register extends JDialog {
    Staff staff = new Staff();

    JLabel heroLabel, flashMessage, username, password, confirmPassword;
    JPanel pane, innerPane;
    JTextField username_tf, password_tf, confirmPassword_tf;
    JButton register;
    String hashed_password;

    int selectedRow ;
    public Register(int selectedRow){
          this.selectedRow = selectedRow;
          setSize(500, 400);
          setTitle("new Account");
          setLayout(new GridLayout(4,1));
          addElements();
          setVisible(true);
    }

    private void addElements(){
        heroLabel = new JLabel("Hello " + staff.table.getValueAt(selectedRow, 0).toString() );
        flashMessage = new JLabel(" ");
        username = new JLabel("     username: ");
        username_tf = new JTextField(20);
        password = new JLabel("     password: ");
        password_tf = new JPasswordField(20);
        confirmPassword = new JLabel("    Confirm password: ");
        confirmPassword_tf = new JPasswordField(20);
        register = new JButton("Register");
        register = new JButton("register");
        register.setBackground(Color.decode("#ACFADF"));

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Sql.isAccountExist(username_tf.getText().toString())){
                    System.out.println("account exists");
                    username_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
                    password_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
                    confirmPassword_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
                    flashMessage.setText("Invalid Credentials");
                    flashMessage.setForeground(Color.decode("#ED7B7B"));
                    return;
                }
                if(!password_tf.getText().toString().equals(confirmPassword_tf.getText().toString())){
                    System.out.println("password didnt match");
                   username_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff"), 5,true));
                   password_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
                   confirmPassword_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
                   flashMessage.setForeground(Color.decode("#ED7B7B"));
                   return;
                }
                   username_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff"), 5,true));
                   password_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff"), 5,true));
                   confirmPassword_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff"), 5,true));
                try{
                    Sql.createAccount(
                                        username_tf.getText().toString(),
                                        password_tf.getText().toString(),
                                        Integer.parseInt(staff.table.getValueAt(selectedRow, 8).toString())
                                        );
                    JOptionPane.showMessageDialog(null, "Account created.");
                    dispose();

                }catch(SQLException ex){
                    setTitle("try again");
                    System.out.println(ex);
                }


            }
        });

        // add to the pane
         pane = new JPanel();
         pane.add(heroLabel);
         add(pane);
         pane = new JPanel();
         pane.add(flashMessage);
         add(pane);
         pane = new JPanel(new GridLayout(3,2,5,5));
         pane.add(username);
         pane.add(username_tf);
         pane.add(password);
         pane.add(password_tf);
         pane.add(confirmPassword);
         pane.add(confirmPassword_tf);
         add(pane);
         pane = new JPanel();
         pane.add(register);
         add(pane);




    }
}
