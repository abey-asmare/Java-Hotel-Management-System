package Gui.Popups;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Inet4Address;
import java.sql.*;
import Gui.Room;
import Sql.Sql;
import java.sql.Date;


public class RegisterGuest  extends JDialog {
    JLabel label;
   public JPanel pane;
    JTextField firstName, lastName, age, country, city, tel, dateTo;
    JComboBox gender;
    JButton done;
    static final String[] choose_gender = {"M", "F"};
    int selectedRow ;
    public RegisterGuest(){
        this.selectedRow = selectedRow;
        setSize(500, 400);
        setTitle("new guest");
        addElements();
        setVisible(true);
    }
    private void addElements(){
          // components
        pane = new JPanel(new GridLayout(12,8));
        label = new JLabel("  first name");
        firstName = new JTextField(10);
        pane.add(label);
        pane.add(firstName);
        label = new JLabel("  last name");
        lastName = new JTextField(10);
        pane.add(label);
        pane.add(lastName);
        label = new JLabel("  age");
        age = new JTextField(10);
        pane.add(label);
        pane.add(age);
        label = new JLabel("  gender");
        gender = new JComboBox(choose_gender);
        pane.add(label);
        pane.add(gender);
        label = new JLabel("  country");
        country = new JTextField(10);
        pane.add(label);
        pane.add(country);
        label = new JLabel("  city");
        city = new JTextField(10);
        pane.add(label);
        pane.add(city);
        label = new JLabel("  tel");
        tel = new JTextField(10);
        pane.add(label);
        pane.add(tel);
        label = new JLabel("  to: ");
       dateTo = new JTextField(10);
       pane.add(label);
       pane.add(dateTo);

        add(pane, BorderLayout.CENTER);

        pane = new JPanel();
        done = new JButton("done");
            done.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Room room = new Room();
                try {
                    int status = Sql.guestRegistration(
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                            gender.getSelectedItem().toString(),
                            country.getText().toString(),
                            city.getText().toString(),
                            tel.getText().toString(),
                            Date.valueOf(dateTo.getText().toString()),
                            Integer.parseInt(age.getText().toString()),
                            Integer.parseInt(room.table.getValueAt(selectedRow, 4).toString())
                    );
                    if (status >= 0) dispose();
                    else setTitle("try again!! check the formatting of the date and age");
        }catch( NumberFormatException ex){
            System.out.println(ex);
            age.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
            dateTo.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
            room.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
            setTitle("check the formating of age ,date,and room");
        }catch (IllegalArgumentException ex){
                        dateTo.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
                    setTitle("check the formating ofthe dateTo");

                }
                }
        });
        done.setBackground(Color.decode("#ACFADF"));
        pane.add(done);
        add(pane, BorderLayout.SOUTH);
    }

}
