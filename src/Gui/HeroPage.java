package Gui;
import java.awt.*;
import javax.swing.*;

public class HeroPage extends JFrame  {
    JPanel content, menuPanel;
    JComboBox query;
    JTextField value;
    public HeroPage(){
        setTitle("Haile resort Hawassa");
        setSize(1250,650);
        menuPanel = new Menu();
        content = new Staff();

        Container container = getContentPane();
        setLayout(new BorderLayout());

        container.add(menuPanel, BorderLayout.WEST);
        container.add(content, BorderLayout.CENTER);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }// contsructor
//
    private void addComponent(Container container, Component ... components){
        for (Component component : components){
            container.add(component);
        }
    }





}
