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
public class RegistrarJuego {

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
        final JLabel imagePreview = new JLabel("Sin portada", SwingConstants.CENTER);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(12, 12, 12, 12);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        gdc.gridx = 0;
        gdc.gridy = 0;
        gdc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("REGISTRAR NUEVO JUEGO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        p.add(lblTitulo, gdc);
        gdc.gridwidth = 1;

        gdc.gridx = 0;
        gdc.gridy = 1;
        p.add(new JLabel("Título del Juego:"), gdc);
        gdc.gridx = 1;
        p.add(new JTextField(30), gdc);

        gdc.gridx = 0;
        gdc.gridy = 2;
        p.add(new JLabel("Sistema Operativo:"), gdc);
        gdc.gridx = 1;
        p.add(new JComboBox<>(new String[]{"Windows", "Mac", "Linux"}), gdc);

        gdc.gridx = 0;
        gdc.gridy = 3;
        p.add(new JLabel("Edad mínima:"), gdc);
        gdc.gridx = 1;
        p.add(new JTextField(10), gdc);

        gdc.gridx = 0;
        gdc.gridy = 4;
        p.add(new JLabel("Precio ($):"), gdc);
        gdc.gridx = 1;
        p.add(new JTextField(10), gdc);

        gdc.gridx = 0;
        gdc.gridy = 5;
        p.add(new JLabel("Portada del Juego:"), gdc);
        gdc.gridx = 1;
        imagePreview.setPreferredSize(new Dimension(320, 200));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        p.add(imagePreview, gdc);

        gdc.gridx = 1;
        gdc.gridy = 6;
        JButton btnChooseImage = new JButton("Subir Portada");
        btnChooseImage.addActionListener(ae -> handleImageSelection(imagePreview));
        p.add(btnChooseImage, gdc);

        gdc.gridx = 0;
        gdc.gridy = 7;
        gdc.gridwidth = 2;
        JButton btnRegister = new JButton("Publicar Juego");
        btnRegister.setPreferredSize(new Dimension(240, 44));
        btnRegister.addActionListener(ae -> JOptionPane.showMessageDialog(p, "Juego listo para publicar."));
        p.add(btnRegister, gdc);

        return p;
    }
}
