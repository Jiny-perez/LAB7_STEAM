/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steam.Frames;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author esteb
 */
public class CatalogFrame extends JFrame {


    public CatalogFrame() {
        
        setTitle("Catálogo de Videojuegos");
        setSize(1100, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        top.setBorder(new EmptyBorder(12, 12, 12, 12));
        
        top.add(new JLabel("Filtrar por SO:")); 
        top.add(new JComboBox<>(new String[]{"Todos", "Windows", "Mac", "Linux"})); 
        
        top.add(Box.createHorizontalStrut(20));
        top.add(new JLabel("Buscar por Título:"));
        top.add(new JTextField(20));
        
        add(top, BorderLayout.NORTH);

        JPanel content = new JPanel(); 
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS)); 
        content.setBorder(new EmptyBorder(12, 12, 12, 12));
        

        JScrollPane sp = new JScrollPane(content);
        add(sp, BorderLayout.CENTER);
    }
    
    private JPanel createGamePanel(String titleText, String detailText, double price) {
        JPanel p = new JPanel(new BorderLayout()); 
        p.setBorder(BorderFactory.createLineBorder(Color.GRAY)); 
        p.setPreferredSize(new Dimension(1020, 140));

        JLabel img = new JLabel("[Imagen]", SwingConstants.CENTER); 
        img.setPreferredSize(new Dimension(180, 120)); 
        img.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        p.add(img, BorderLayout.WEST);
        
        JPanel info = new JPanel(); 
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS)); 
        info.setBorder(new EmptyBorder(12, 12, 12, 12));
        
        JLabel title = new JLabel(titleText); 
        title.setFont(title.getFont().deriveFont(16f)); 
        info.add(title);
        
        info.add(new JLabel(detailText)); 
        info.add(Box.createVerticalStrut(8));
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnPanel.add(new JButton("Descargar ($" + String.format("%.2f", price) + ")")); 

        p.add(info, BorderLayout.CENTER);
        p.add(btnPanel, BorderLayout.EAST);
        
        return p;
    }
}