package Gui.Popups;
import Gui.Staff;
import Sql.Sql;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import Gui.Popups.Register;
public class StaffInfo extends JDialog {
    Staff staff = new Staff();
    JLabel name, address, email, phone, hireDate, salary, profileImage;
    JButton createAccount, recruit, delete;
    JPanel firstpane;
    ImageIcon staffImage;

    private int selectedRow;

    public StaffInfo(int selectedRow) {
        this.selectedRow = selectedRow;
        setLayout(new BorderLayout(10, 10));
        setTitle("staff info");
        setSize(500, 400);
        addElements();
        setVisible(true);
    }

    public ImageIcon processImage(String path) throws NullPointerException {
        try {
            // Load the original image
            File imageFile = new File(path);
            BufferedImage originalImage = ImageIO.read(imageFile);

            BufferedImage circularImage = new BufferedImage(
                    originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB
            );
            Graphics2D g2d = circularImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Ellipse2D.Double clip = new Ellipse2D.Double(0, 0, originalImage.getWidth(), originalImage.getHeight());
            g2d.setClip(clip);
            g2d.drawImage(originalImage, 0, 0, circularImage.getWidth(), circularImage.getHeight(), null);
            g2d.dispose();

            ImageIcon icon = new ImageIcon(circularImage);
            Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            return icon;

        } catch (IOException e) {
            System.out.println(e);
            ImageIcon icon = new ImageIcon("./assets/image/default.jpg");
            Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            return icon;
        }
    }


    public void addElements() {
        //labels

        name = new JLabel("Name: " + staff.table.getValueAt(selectedRow, 0) + "(" + staff.table.getValueAt(selectedRow, 5) + ")");
        name.setFont(new Font("Times New Roman", Font.BOLD, 20));
        address = new JLabel("Address: " + staff.table.getValueAt(selectedRow, 1));
        email = new JLabel("Email: " + staff.table.getValueAt(selectedRow, 2));
        phone = new JLabel("Phone: " + staff.table.getValueAt(selectedRow, 3));
        hireDate = new JLabel("Been here since : " + staff.table.getValueAt(selectedRow, 4));
        salary = new JLabel("$" + staff.table.getValueAt(selectedRow, 6));
        salary.setFont(new Font("Times New Roman", Font.BOLD, 14));
        try {
            staffImage = processImage(staff.table.getValueAt(selectedRow, 7).toString());
        } catch (NullPointerException e) {
            staffImage = processImage("./assets/image/default.jpg");
        }

        createAccount = new JButton("createAccount");
        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Register register = new Register(selectedRow);
            }
        });
        createAccount.setBackground(Color.decode("#ACFADF"));
        recruit = new JButton("recruit");
        recruit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                NewEmployee employee = new NewEmployee();
            }
        });
        delete = new JButton("delete");
        delete.setBackground(Color.decode("#ED7B7B"));
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Sql.deleteRecord(
                            staff.table.getValueAt(selectedRow, 0).toString().split(" ")[0],
                            staff.table.getValueAt(selectedRow, 0).toString().split(" ")[1],
                            "staff"
                    );
                    JOptionPane.showMessageDialog(null, "Account deleted");
                    dispose();
                } catch (SQLException ex) {
                    System.out.println(ex);
                    setTitle("try again!!!");
                }
            }
        });

        // adding to the panel
        firstpane = new JPanel();
        profileImage = new JLabel(staffImage);
        profileImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser("./assets/image/");
                int fileDialog = fileChooser.showSaveDialog(null);
                if(fileDialog == JFileChooser.APPROVE_OPTION){
                    Path absolutePath = fileChooser.getSelectedFile().toPath();
                    Path currentPath = Paths.get(System.getProperty("user.dir"));
                    Path relativePath =  currentPath.relativize(absolutePath);
                    // change profile picture
                    Sql.changeProfilePicture(
                            staff.table.getValueAt(selectedRow, 0).toString().split(" ")[0],
                            staff.table.getValueAt(selectedRow, 0).toString().split(" ")[1],
                            relativePath.toString()
                    );
                    }
            }// end mouseClicked
            @Override
            public void mouseEntered(MouseEvent e){
                profileImage.setIcon(processImage("./assets/image/camera.jpg"));
            }
            @Override
            public void mouseExited(MouseEvent e){
                try{
                    profileImage.setIcon(processImage(staff.table.getValueAt(selectedRow, 7).toString()));
                }catch(NullPointerException ex){
                    profileImage.setIcon(processImage(staff.table.getValueAt(selectedRow, 7).toString()));
                }
            }
        });

        firstpane.add(profileImage);
        add(firstpane, BorderLayout.WEST);

        firstpane = new JPanel(new GridLayout(15,1));
        firstpane.add(name);
        firstpane.add(address);
        firstpane.add(email);
        firstpane.add(phone);
        firstpane.add(hireDate);
        firstpane.add(salary);
        add(firstpane, BorderLayout.CENTER);

        firstpane = new JPanel();
        firstpane.add(recruit);
        firstpane.add(createAccount);
        firstpane.add(delete);
        add(firstpane, BorderLayout.SOUTH);
    }


}
