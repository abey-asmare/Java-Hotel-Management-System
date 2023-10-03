package Gui.Popups;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Gui.HeroPage2;
import Sql.Sql;
public class Login extends JFrame {
    JLabel username, password, flashMessage;
    JTextField username_tf, password_tf;
    JButton login;
    JPanel pane;
    String session = null;
    public Login(){
          setSize(500, 400);
          setTitle("Login");
          setLayout(new GridLayout(3,1));
          addElements();
          setVisible(true);
    }

    private void addElements(){
        flashMessage = new JLabel("");
        username = new JLabel("         username");
        password = new JLabel("         password");
        username_tf = new JTextField(10);
        password_tf = new JPasswordField();
        login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean auth1_0 = Sql.authenticate(username_tf.getText().toString(), password_tf.getText().toString());
                if (auth1_0){
                    session = username_tf.getText().toString();
                    flashMessage.setForeground(Color.decode("#FFFFFF"));
                    username_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#FFFFFF"), 5,true));
                    password_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#FFFFFF"), 5,true));
                    flashMessage.setText("");
                    dispose();
                    HeroPage2 heroPage2 = new HeroPage2(session);
                }else{
                    flashMessage.setText("Invalid Credentials");
                    flashMessage.setForeground(Color.decode("#ED7B7B"));
                    username_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
                    password_tf.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
                }
            }
        });
        addWindowListener(new WindowAdapter() {
       @Override
      public void windowClosing(WindowEvent e) {
           System.exit(0);
             }
            });

        pane = new JPanel();
        pane.add(flashMessage);
        add(pane);

        pane = new JPanel(new GridLayout(3,2));
        pane.add(username);
        pane.add(username_tf);
        pane.add(password);
        pane.add(password_tf);
        add(pane);
         pane = new JPanel();
         pane.add(login);
         add(pane);
    }

}
