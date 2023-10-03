package Gui;
import Gui.Popups.Login;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HeroPage2 extends JFrame  implements MouseListener {
    public static final String ROOT_USER = "abdu";
    Container container;
    JPanel content, menuPanel;
    JButton guests, rooms,staff,logout;
    JLabel brand;
    String session ;

    public HeroPage2(String session){
        this.session = session;
        setTitle("Haile resort Hawassa");
        setSize(1250,650);
        content = new Guest();


        menuPanel = new JPanel(new GridLayout(10,1));
        menuPanel.setLayout(new GridLayout(10,1));
        menuPanel.setBackground(Color.decode("#A64452"));
        menuPanel.setPreferredSize(new Dimension(350, 650));

        ImageIcon heroImage = new ImageIcon("./assets/image/HaileResort.png");
        Image image = heroImage.getImage().getScaledInstance(350,200 , Image.SCALE_SMOOTH);
        heroImage = new ImageIcon(image);
        brand = new JLabel(heroImage);
        brand.setPreferredSize(new Dimension(getX(), getY()));

        guests = new JButton("Guests");
        rooms = new JButton("Rooms");
        staff = new JButton("Staff");
        logout = new JButton("logout");

         // add to the menuPanel
        makeTransparentComponent("#F1F1F1", new Font("Times New Roman", Font.PLAIN, 24), guests, rooms, staff, logout);
        addComponent(menuPanel, brand,new JLabel(""), guests, rooms, staff, logout);
          if (!session.equals(ROOT_USER)){
            menuPanel.remove(staff);
        }
        container = getContentPane();
        setLayout(new BorderLayout());

        container.add(menuPanel, BorderLayout.WEST);
        container.add(content, BorderLayout.CENTER);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }// constructor
//
    private void addComponent(Container container, Component ... components){
        for (Component component : components)
            container.add(component);
    }// addcomponent

        private void makeTransparentComponent(String fg, Font font, JButton ...  buttons){
            for (JButton btn : buttons) {
                btn.setOpaque(false);
                btn.setContentAreaFilled(false);
                btn.setFont(font);
                btn.setBorderPainted(false);
                btn.setForeground(Color.decode(fg));
                btn.setFocusPainted(false);
                btn.addMouseListener(this);
            }
            }//make transparent

    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getSource() == guests){
            content.setVisible(false);
            content = new Guest();
            content.setVisible(true);
            System.out.println("guest");
            System.out.println(e.getSource());
        }else if(e.getSource() == rooms){
            content.setVisible(false);
            content = new Room();
            content.setVisible(true);
            System.out.println("rooms");
            System.out.println(e.getSource());
        }else if(e.getSource() == staff){
            content.setVisible(false);
            content = new Staff();
            content.setVisible(true);
            System.out.println("staff");
            System.out.println(e.getSource());
        }else if(e.getSource() == logout){
            System.out.println("logout");
            dispose();
            Login login = new Login();
            System.out.println(e.getSource());
        }
        container.add(content, BorderLayout.CENTER);
    };
    public void mousePressed(MouseEvent e){};
    public void mouseReleased(MouseEvent e){};
    public void mouseEntered(MouseEvent e){};
    public void mouseExited(MouseEvent e){};

}
