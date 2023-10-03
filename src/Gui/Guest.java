package Gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.event.*;
import javax.swing.*;
import Sql.*;
import Gui.Popups.GuestInfo;
public class Guest extends JPanel  {
    JLabel queryLabel;
  public  JTable table;
    JScrollPane scrollPane;
    JComboBox query;
    JTextField value;

    JButton btn ;
     public Guest(){
        setLayout(new BorderLayout());
        setBackground(Color.black);
        queryLabel = new JLabel("Search By: ");
        String[] filter_by = {"fullname", "roomNumber", "address"};
        query = new JComboBox(filter_by);
        value = new JTextField("",20);
        value.setPreferredSize(new Dimension(100,30));
        value.setBorder(BorderFactory.createLineBorder(Color.decode("#FFFFFF"), 4,true));
        value.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                  try {
                    table.setModel(Sql.filterGuests(query.getSelectedItem().toString(), value.getText()));
                    value.setBorder(BorderFactory.createLineBorder(Color.decode("#ACFADF"), 5,true));
                }catch (SQLException ex){
                    System.out.println(ex);
                }catch (NumberFormatException ex){
                    value.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 5,true));
                  }

            }// end of insertUpdate

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    table.setModel(Sql.filterGuests(query.getSelectedItem().toString(), value.getText()));
                    value.setBorder(BorderFactory.createLineBorder(Color.decode("#ACFADF"), 4,true));

                }catch (SQLException ex){
                    System.out.println(ex);
                }catch (NumberFormatException ex){
                      value.setBorder(BorderFactory.createLineBorder(Color.decode("#ED7B7B"), 4, true));
                  }
            }// end of removeupdate

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        // table content
        try{
            table = new JTable(Sql.getAllGuests());
            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1 && !e.getValueIsAdjusting())
                    {
                        GuestInfo guestInfo = new GuestInfo(selectedRow);
                    }

                }
            });
        }catch (SQLException e){
            System.out.println(e);
        }finally{
            scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }


        // add to the content panel
        JPanel subcontent = new JPanel();
        subcontent.add(queryLabel);
        subcontent.add(query);
        subcontent.add(value);
        add(subcontent, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }// contsructor
    private void addComponent(Container container, Component ... components){
        for (Component component : components){
            container.add(component);
        }
    }

}
