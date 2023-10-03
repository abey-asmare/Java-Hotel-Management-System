package Gui.Popups;
import Gui.Guest;
import Sql.Sql;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GuestInfo extends JDialog {
    Guest g = new Guest();
    private int selectedRow;
    JPanel firstPane;
    JLabel name, address, phone, room, type, date, to, payment, paymentDate, paymentMethod;
    JTextField name_tf, address_tf, phone_tf, room_tf, type_tf, date_tf, to_tf, paymentDate_tf, paymentMethod_tf;
    JCheckBox payment_cb;
    JButton btn;
   public  GuestInfo(int selectedRow){
       this.selectedRow = selectedRow;
       addElements();
       setLayout(new GridLayout(6,1));
       setSize(500,400);
       setVisible(true);
    }
    public void addElements(){
              // panels
        firstPane = new JPanel(new GridLayout(3,2));
        // labels
        name = new JLabel("name");
        address = new JLabel("address");
        phone = new JLabel("phone");
        room = new JLabel("room");
        type = new JLabel("type");
        date = new JLabel("date");
        to = new JLabel("to");
        payment = new JLabel("payment");
        paymentDate = new JLabel("payment date");
        paymentMethod = new JLabel("payment Method");
        // textfields
            name_tf = new JTextField( 10);
            address_tf = new JTextField( 10);
            phone_tf = new JTextField( 10);
            room_tf = new JTextField(10);
            type_tf = new JTextField( 10);
            date_tf = new JTextField(10);
            to_tf = new JTextField(10);
            paymentDate_tf = new JTextField(10);
            paymentMethod_tf = new JTextField(10);
            payment_cb = new JCheckBox();
            // buttons
        btn = new JButton("Done");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                 if (payment_cb.isSelected() ) {
                        Sql.pay(
                                g.table.getValueAt(selectedRow, 0).toString().split(" ")[0],
                                g.table.getValueAt(selectedRow, 0).toString().split(" ")[1],
                                "cash", "pay for the room"
                        );
                        return;

                } else  {
                        paymentDate_tf.setText("transfer resetted.");
                        paymentMethod_tf.setText("transfer resetted.");
                        Sql.unpay(
                                g.table.getValueAt(selectedRow, 0).toString().split(" ")[0],
                                g.table.getValueAt(selectedRow, 0).toString().split(" ")[1]
                        );
                }
            }

        });
        btn.setBackground(Color.decode("#ACFADF"));

        try {
            name_tf.setText(g.table.getValueAt(selectedRow, 0).toString());
            address_tf.setText(g.table.getValueAt(selectedRow, 1).toString());
            phone_tf.setText(g.table.getValueAt(selectedRow, 2).toString());
            room_tf.setText(g.table.getValueAt(selectedRow, 3).toString());
            type_tf.setText(g.table.getValueAt(selectedRow, 4).toString());
            date_tf.setText(g.table.getValueAt(selectedRow, 5).toString().split("~")[0]);
            to_tf.setText(g.table.getValueAt(selectedRow, 5).toString().split("~")[1]);
            if (g.table.getValueAt(selectedRow, 8).toString() != null) {
                payment_cb.setSelected(true);
            }
            paymentDate_tf = new JTextField(g.table.getValueAt(selectedRow, 8).toString(), 15);
            paymentMethod_tf = new JTextField(g.table.getValueAt(selectedRow, 9).toString(), 15);

        }catch(NullPointerException e){
            paymentDate_tf.setText("didn't make the transfer");
            paymentMethod_tf.setText("didn't make the transfer");
        }

//        payment_cb.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == e.SELECTED ) {
//                        Sql.pay(
//                                g.table.getValueAt(selectedRow, 0).toString().split(" ")[0],
//                                g.table.getValueAt(selectedRow, 0).toString().split(" ")[1],
//                                "cash", "pay for the room"
//                        );
//                        return;
//
//                } else if (e.getStateChange() == e.DESELECTED) {
//                        paymentDate_tf.setText("transfer resetted.");
//                        paymentMethod_tf.setText("transfer resetted.");
//                        Sql.unpay(
//                                g.table.getValueAt(selectedRow, 0).toString().split(" ")[0],
//                                g.table.getValueAt(selectedRow, 0).toString().split(" ")[1]
//                        );
//                }
//            }
//        }
//        );

       // adding to the panel
        firstPane.add(name);
        firstPane.add(name_tf);
        firstPane.add(address);
        firstPane.add(address_tf);
        firstPane.add(phone);
        firstPane.add(phone_tf);
        add(firstPane);

        firstPane = new JPanel();
        firstPane.add(room);
        firstPane.add(room_tf);
        firstPane.add(type);
        firstPane.add(type_tf);
        add(firstPane);

        firstPane = new JPanel();
        firstPane.add(date);
        firstPane.add(date_tf);
        firstPane.add(to);
        firstPane.add(to_tf);
        add(firstPane);

        firstPane = new JPanel(new GridLayout(3,3));
        firstPane.add(payment);
        firstPane.add(payment_cb);
        firstPane.add(new JLabel(""));
        firstPane.add(paymentDate);
        firstPane.add(paymentDate_tf);
        firstPane.add(new JLabel(""));
        firstPane.add(paymentMethod);
        firstPane.add(paymentMethod_tf);
        add(firstPane);

        firstPane = new JPanel();
        firstPane.add(btn);
        add(firstPane);
    }
}
