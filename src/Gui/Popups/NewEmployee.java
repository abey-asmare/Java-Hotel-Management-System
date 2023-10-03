package Gui.Popups;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import Sql.Sql;


class NewEmployee  extends JDialog {
    JLabel label;
   public JPanel pane;
    JTextField firstName, lastName, age, address, email, phone1, phone2;
    JComboBox gender, job;
    JButton done;
    static final String[] choose_gender = {"M", "F"};
    static final String[] choose_job = {"General Manager", "Front Desk Receptionist", "Housekeeping", "Sales Manager",
                                  "Event Coordinator", "Executive Chef", "chef", "Waiter/waitress", "Parking Attendant/security officer"};
    NewEmployee(){
        setSize(500, 400);
        setTitle("new recruit");
        addElements();
        setVisible(true);
    }
    private void addElements(){
          // components
        pane = new JPanel(new GridLayout(12,6));
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
        label = new JLabel("  address");
        address = new JTextField(10);
        pane.add(label);
        pane.add(address);
        label = new JLabel("  gender");
        gender = new JComboBox(choose_gender);
        pane.add(label);
        pane.add(gender);

        label = new JLabel("  job");
        job = new JComboBox(choose_job);
        pane.add(label);
        pane.add(job);

        label = new JLabel("  email");
        email = new JTextField(10);
        pane.add(label);
        pane.add(email);
        label = new JLabel("  phone1");
        phone1 = new JTextField(10);
        pane.add(label);
        pane.add(phone1);
        label = new JLabel("  phone2");
        phone2= new JTextField(10);
        pane.add(label);
        pane.add(phone2);
        add(pane, BorderLayout.CENTER);

        pane = new JPanel();
        done = new JButton("done");
            done.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                try {
                    int status = Sql.recruit(
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                           Integer.parseInt(age.getText().toString()),
                            gender.getSelectedItem().toString(),
                            address.getText().toString(),
                            email.getText().toString(),
                            job.getSelectedIndex() + 1,
                            phone1.getText().toString(),
                            phone2.getText().toString()
                    );
                    if (status >= 0) dispose();
                    else setTitle("try again!!");
        }catch( NumberFormatException ex){
            System.out.println(ex);
            age.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
            email.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));

                    }
                }
        });
        done.setBackground(Color.decode("#ACFADF"));
        pane.add(done);
        add(pane, BorderLayout.SOUTH);
    }

}
