/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steam.Frames;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author esteb
 */
public class ModificarPlayer {

    public static JPanel createPanel() {
        final JDateChooser dateChooser = new JDateChooser(new Date());
        final JPasswordField txtPass = new JPasswordField(25);
        final JTextField txtCodigo = new JTextField(12);
        final JLabel imagePreview = new JLabel("Cargar Imagen", SwingConstants.CENTER);
        final char defaultEchoChar = txtPass.getEchoChar();

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(12, 12, 12, 12);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        gdc.gridx = 0;
        gdc.gridy = 0;
        gdc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("MODIFICAR PLAYER EXISTENTE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        p.add(lblTitulo, gdc);

        gdc.gridwidth = 1;
        gdc.gridx = 0;
        gdc.gridy = 1;
        p.add(new JLabel("Código del player:"), gdc);
        gdc.gridx = 1;
        p.add(txtCodigo, gdc);

        gdc.gridx = 0;
        gdc.gridy = 2;
        gdc.gridwidth = 2;
        JButton btnLoad = new JButton("Buscar y Cargar Datos");
        btnLoad.addActionListener(ae -> JOptionPane.showMessageDialog(p, "Simulando carga de datos para: " + txtCodigo.getText()));
        p.add(btnLoad, gdc);
        gdc.gridwidth = 1;

        // --- CAMPOS ---
        gdc.gridx = 0;
        gdc.gridy = 3;
        p.add(new JLabel("Username:"), gdc);
        gdc.gridx = 1;
        p.add(new JTextField(30), gdc);

        // PASSWORD + OJO
        gdc.gridx = 0;
        gdc.gridy = 4;
        p.add(new JLabel("Password:"), gdc);
        gdc.gridx = 1;
        JPanel passPanel = new JPanel(new BorderLayout(5, 0));
        JToggleButton btnShowPass = new JToggleButton("👁");
        btnShowPass.setPreferredSize(new Dimension(50, 30));
        btnShowPass.addActionListener(e -> txtPass.setEchoChar(btnShowPass.isSelected() ? (char) 0 : defaultEchoChar));
        passPanel.add(txtPass, BorderLayout.CENTER);
        passPanel.add(btnShowPass, BorderLayout.EAST);
        p.add(passPanel, gdc);

        // NOMBRE
        gdc.gridx = 0;
        gdc.gridy = 5;
        p.add(new JLabel("Nombre:"), gdc);
        gdc.gridx = 1;
        p.add(new JTextField(30), gdc);

        // FECHA (JCALENDAR)
        gdc.gridx = 0;
        gdc.gridy = 6;
        p.add(new JLabel("Fecha de nacimiento:"), gdc);
        gdc.gridx = 1;
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(150, 30));
        p.add(dateChooser, gdc);

        // IMAGEN PREVIEW & BOTÓN
        gdc.gridx = 0;
        gdc.gridy = 7;
        p.add(new JLabel("Imagen (preview):"), gdc);
        gdc.gridx = 1;
        imagePreview.setPreferredSize(new Dimension(260, 160));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        p.add(imagePreview, gdc);

        gdc.gridx = 1;
        gdc.gridy = 8;
        JButton btnChooseImage = new JButton("Cambiar imagen");
        btnChooseImage.addActionListener(ae -> handleImageSelection(imagePreview));
        p.add(btnChooseImage, gdc);

        gdc.gridx = 0;
        gdc.gridy = 10;
        gdc.gridwidth = 2;
        JButton btnSave = new JButton("Guardar Cambios");
        btnSave.setPreferredSize(new Dimension(220, 44));
        btnSave.addActionListener(ae -> JOptionPane.showMessageDialog(p, "Cambios listos para guardar."));
        p.add(btnSave, gdc);

        return p;
    }

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

}
