/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steam.Frames;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author esteb
 */
public class RegistrarPlayer extends JFrame {
    
    private JLabel imagePreview;
    private File archivoImagenSeleccionado;
    private JDateChooser dateChooser;
    private JPasswordField txtPass; 
    private char defaultEchoChar; 

    public RegistrarPlayer() {
        setTitle("Registrar Player");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(12, 12, 12, 12);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        gdc.gridx = 0; gdc.gridy = 0;
        p.add(new JLabel("Username:"), gdc);
        gdc.gridx = 1;
        p.add(new JTextField(30), gdc);

        gdc.gridx = 0; gdc.gridy = 1;
        p.add(new JLabel("Password:"), gdc);
        
        gdc.gridx = 1;
        JPanel passPanel = new JPanel(new BorderLayout(5, 0)); 
        
        txtPass = new JPasswordField(25);
        defaultEchoChar = txtPass.getEchoChar();
        
        JToggleButton btnShowPass = new JToggleButton("👁"); 
        btnShowPass.setPreferredSize(new Dimension(50, 30));
        btnShowPass.setFocusPainted(false);
        
        btnShowPass.addActionListener(e -> {
            if (btnShowPass.isSelected()) {
                txtPass.setEchoChar((char) 0); 
            } else {
                txtPass.setEchoChar(defaultEchoChar); 
            }
        });

        passPanel.add(txtPass, BorderLayout.CENTER);
        passPanel.add(btnShowPass, BorderLayout.EAST);
        
        p.add(passPanel, gdc);

        gdc.gridx = 0; gdc.gridy = 2;
        p.add(new JLabel("Nombre:"), gdc);
        gdc.gridx = 1;
        p.add(new JTextField(30), gdc);

        gdc.gridx = 0; gdc.gridy = 3;
        p.add(new JLabel("Fecha de nacimiento:"), gdc);
        
        gdc.gridx = 1;
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(150, 30));
        p.add(dateChooser, gdc);

        gdc.gridx = 0; gdc.gridy = 4;
        p.add(new JLabel("Imagen (preview):"), gdc);
        
        gdc.gridx = 1;
        imagePreview = new JLabel("Sin imagen", SwingConstants.CENTER);
        imagePreview.setPreferredSize(new Dimension(260, 160));
        imagePreview.setSize(260, 160); 
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        p.add(imagePreview, gdc);

        gdc.gridx = 1; gdc.gridy = 5;
        JButton btnChooseImage = new JButton("Seleccionar imagen");
        p.add(btnChooseImage, gdc);

        gdc.gridx = 0; gdc.gridy = 6;
        p.add(new JLabel("Tipo usuario:"), gdc);
        gdc.gridx = 1;
        p.add(new JComboBox<>(new String[]{"normal", "admin"}), gdc);

        gdc.gridx = 0; gdc.gridy = 7;
        gdc.gridwidth = 2;
        JButton btnRegister = new JButton("Registrar");
        btnRegister.setPreferredSize(new Dimension(220, 44));
        p.add(btnRegister, gdc);

        add(p, BorderLayout.CENTER);

        btnChooseImage.addActionListener(ae -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "png", "jpeg");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                archivoImagenSeleccionado = fileChooser.getSelectedFile();
                
                ImageIcon originalIcon = new ImageIcon(archivoImagenSeleccionado.getAbsolutePath());
                Image scaledImage = originalIcon.getImage().getScaledInstance(
                        imagePreview.getPreferredSize().width, 
                        imagePreview.getPreferredSize().height, 
                        Image.SCALE_SMOOTH
                );
                
                imagePreview.setIcon(new ImageIcon(scaledImage));
                imagePreview.setText(""); 
            }
        });

        btnRegister.addActionListener(ae -> {
            if (dateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Por favor selecciona una fecha.");
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String fechaString = sdf.format(dateChooser.getDate());
                
                String pass = new String(txtPass.getPassword());
                
                JOptionPane.showMessageDialog(this, "Contraseña: " + pass + "\nFecha: " + fechaString);
            }
        });
    }
    
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> new RegistrarPlayer().setVisible(true));
    }
}