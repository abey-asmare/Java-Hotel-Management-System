package Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JPanel implements MouseListener {
    JPanel menuPanel;
    JLabel brand;
    JButton guests, rooms,staff, overview,register,login;

    public Menu(){
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
        overview = new JButton("Overview");
        register = new JButton("Register");
        login = new JButton("login");

         // add to the menuPanel
        makeTransparentComponent("#F1F1F1", new Font("Times New Roman", Font.PLAIN, 24), guests, rooms, staff, overview, register, login);
        addComponent(menuPanel, brand,new JLabel(""), guests, rooms, staff, overview, register, login);
    }// contstructor

        private void addComponent(Container container, Component ... components){
        for (Component component : components)
            container.add(component);
    }// addComponent


    public void mouseClicked(MouseEvent e){
        if(e.getSource() == guests){
        }else if(e.getSource() == rooms){
            System.out.println("rooms");
            System.out.println(e.getSource());
        }else if(e.getSource() == staff){
            System.out.println("staff");
            System.out.println(e.getSource());
        }else if(e.getSource() == overview){
            System.out.println("overview");
            System.out.println(e.getSource());
        }else if(e.getSource() == register){
            System.out.println("register");
            System.out.println(e.getSource());
        }else if(e.getSource() == login){
            System.out.println("login");
            System.out.println(e.getSource());
        }
    };
    public void mousePressed(MouseEvent e){};
    public void mouseReleased(MouseEvent e){};
    public void mouseEntered(MouseEvent e){};
    public void mouseExited(MouseEvent e){};
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
            }// transparentComponent
}// end of class Menu