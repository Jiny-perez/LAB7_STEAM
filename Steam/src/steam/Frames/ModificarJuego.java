/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steam.Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author esteb
 */

public class ModificarJuego {
    
    private static void handleImageSelection(JLabel previewLabel) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ImageIcon originalIcon = new ImageIcon(selectedFile.getAbsolutePath());
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                    previewLabel.getPreferredSize().width, 
                    previewLabel.getPreferredSize().height, 
                    Image.SCALE_SMOOTH
            );
            previewLabel.setIcon(new ImageIcon(scaledImage));
            previewLabel.setText(""); 
        }
    }

    public static JPanel createPanel() {
        // Variables locales para el panel
        final JTextField txtCodigo = new JTextField(12);
        final JLabel imagePreview = new JLabel("Cargar Imagen", SwingConstants.CENTER);
        
        // --- PANEL DE INTERFAZ ---
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(12, 12, 12, 12);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        // TÍTULO GENERAL
        gdc.gridx = 0; gdc.gridy = 0; gdc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("MODIFICAR JUEGO EXISTENTE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        p.add(lblTitulo, gdc);

        // --- BÚSQUEDA ---
        gdc.gridwidth = 1;
        gdc.gridx = 0; gdc.gridy = 1; p.add(new JLabel("Código del juego:"), gdc);
        gdc.gridx = 1; p.add(txtCodigo, gdc);
        
        gdc.gridx = 0; gdc.gridy = 2; gdc.gridwidth = 2;
        JButton btnLoad = new JButton("Buscar y Cargar Datos");
        btnLoad.addActionListener(ae -> JOptionPane.showMessageDialog(p, "Simulando carga de datos para: " + txtCodigo.getText()));
        p.add(btnLoad, gdc);
        gdc.gridwidth = 1; 

        // --- CAMPOS ---
        gdc.gridx = 0; gdc.gridy = 3; p.add(new JLabel("Título:"), gdc);
        gdc.gridx = 1; p.add(new JTextField(30), gdc);

        gdc.gridx = 0; gdc.gridy = 4; p.add(new JLabel("Sistema Operativo:"), gdc);
        gdc.gridx = 1; p.add(new JComboBox<>(new String[]{"Windows", "Mac", "Linux"}), gdc);
        
        gdc.gridx = 0; gdc.gridy = 5; p.add(new JLabel("Edad mínima:"), gdc);
        gdc.gridx = 1; p.add(new JTextField(10), gdc);
        
        gdc.gridx = 0; gdc.gridy = 6; p.add(new JLabel("Precio ($):"), gdc);
        gdc.gridx = 1; p.add(new JTextField(10), gdc);

        // IMAGEN PREVIEW
        gdc.gridx = 0; gdc.gridy = 7; p.add(new JLabel("Imagen (preview):"), gdc);
        gdc.gridx = 1;
        imagePreview.setPreferredSize(new Dimension(320, 200));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        p.add(imagePreview, gdc);

        // BOTÓN CAMBIAR IMAGEN
        gdc.gridx = 1; gdc.gridy = 8;
        JButton btnChooseImage = new JButton("Cambiar Portada");
        btnChooseImage.addActionListener(ae -> handleImageSelection(imagePreview)); 
        p.add(btnChooseImage, gdc);

        // BOTÓN GUARDAR
        gdc.gridx = 0; gdc.gridy = 9; gdc.gridwidth = 2;
        JButton btnSave = new JButton("Guardar Cambios");
        btnSave.setPreferredSize(new Dimension(240, 44));
        btnSave.addActionListener(ae -> JOptionPane.showMessageDialog(p, "Cambios listos para guardar."));
        p.add(btnSave, gdc);
        
        return p;
    }
}