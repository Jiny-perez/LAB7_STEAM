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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import steam.Steam;

/**
 *
 * @author esteb
 */

public class RegistrarPlayer { 

   public static JPanel createPanel(Steam steam) {
        final JPasswordField txtPass = new JPasswordField(25);
        final JDateChooser dateChooser = new JDateChooser();
        final JLabel imagePreview = new JLabel("Sin imagen", SwingConstants.CENTER);
        final char defaultEchoChar = txtPass.getEchoChar();

        final String[] selectedImagePath = new String[]{""};

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(12, 12, 12, 12);
        gdc.fill = GridBagConstraints.HORIZONTAL;
        gdc.gridx = 0;
        gdc.gridy = 0;
        gdc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("REGISTRAR NUEVO PLAYER", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        p.add(lblTitulo, gdc);

        gdc.gridwidth = 1;
        gdc.gridx = 0;
        gdc.gridy = 1;
        p.add(new JLabel("Username:"), gdc);
        gdc.gridx = 1;
        final JTextField txtUsername = new JTextField(30);
        p.add(txtUsername, gdc);

        gdc.gridx = 0;
        gdc.gridy = 2;
        p.add(new JLabel("Password:"), gdc);
        gdc.gridx = 1;
        JPanel passPanel = new JPanel(new BorderLayout(5, 0));
        JToggleButton btnShowPass = new JToggleButton("👁");
        btnShowPass.setPreferredSize(new Dimension(50, 30));
        btnShowPass.addActionListener(e -> txtPass.setEchoChar(btnShowPass.isSelected() ? (char) 0 : defaultEchoChar));
        passPanel.add(txtPass, BorderLayout.CENTER);
        passPanel.add(btnShowPass, BorderLayout.EAST);
        p.add(passPanel, gdc);

        gdc.gridx = 0;
        gdc.gridy = 3;
        p.add(new JLabel("Nombre:"), gdc);
        gdc.gridx = 1;
        final JTextField txtNombre = new JTextField(30);
        p.add(txtNombre, gdc);

        gdc.gridx = 0;
        gdc.gridy = 4;
        p.add(new JLabel("Fecha de nacimiento:"), gdc);
        gdc.gridx = 1;
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(150, 30));
        p.add(dateChooser, gdc);

        gdc.gridx = 0;
        gdc.gridy = 5;
        p.add(new JLabel("Imagen (preview):"), gdc);
        gdc.gridx = 1;
        imagePreview.setPreferredSize(new Dimension(260, 160));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        p.add(imagePreview, gdc);

        gdc.gridx = 1;
        gdc.gridy = 6;
        JButton btnChooseImage = new JButton("Seleccionar imagen");
        btnChooseImage.addActionListener(ae -> handleImageSelection(imagePreview, selectedImagePath));
        p.add(btnChooseImage, gdc);

        gdc.gridx = 0;
        gdc.gridy = 7;
        p.add(new JLabel("Tipo usuario:"), gdc);
        gdc.gridx = 1;
        final JComboBox<String> cbTipo = new JComboBox<>(new String[]{"normal", "admin"});
        p.add(cbTipo, gdc);

        gdc.gridx = 0;
        gdc.gridy = 8;
        gdc.gridwidth = 2;
        JButton btnRegister = new JButton("Registrar");
        btnRegister.setPreferredSize(new Dimension(220, 44));
        btnRegister.addActionListener(ae -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPass.getPassword());
            String nombre = txtNombre.getText().trim();
            Date fecha = dateChooser.getDate();
            String tipo = (String) cbTipo.getSelectedItem();
            String rutaSeleccionada = selectedImagePath[0] == null ? "" : selectedImagePath[0];

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(p, "Ingresa un username.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(p, "Ingresa una contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(p, "Ingresa el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            long nacimientoMillis = System.currentTimeMillis();
            if (fecha != null) {
                nacimientoMillis = fecha.getTime();
            } else {
                int opt = JOptionPane.showConfirmDialog(p, "No ha ingresado fecha de nacimiento. ¿Usar fecha de hoy?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (opt != JOptionPane.YES_OPTION) return;
                nacimientoMillis = System.currentTimeMillis();
            }

            String rutaParaGuardar = rutaSeleccionada;
            if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty()) {
                try {
                    File src = new File(rutaSeleccionada);
                    if (src.exists()) {
                        File dirImages = new File("steam/images");
                        if (!dirImages.exists()) dirImages.mkdirs();
                        String ext = "";
                        String name = src.getName();
                        int dot = name.lastIndexOf('.');
                        if (dot >= 0) ext = name.substring(dot);
                        String destName = "player_" + System.currentTimeMillis() + ext;
                        File dest = new File(dirImages, destName);
                        Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        rutaParaGuardar = dest.getPath();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(p, "Error al copiar la imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    rutaParaGuardar = "";
                }
            }

            try {
                if (steam == null) {
                    JOptionPane.showMessageDialog(p, "Instancia de Steam no proporcionada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                steam.addPlayer(username, password, nombre, nacimientoMillis, rutaParaGuardar, tipo);
                JOptionPane.showMessageDialog(p, "Player registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // limpiar formulario
                txtUsername.setText("");
                txtPass.setText("");
                txtNombre.setText("");
                dateChooser.setDate(null);
                selectedImagePath[0] = "";
                imagePreview.setIcon(null);
                imagePreview.setText("Sin imagen");
                cbTipo.setSelectedIndex(0);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(p, "Error al guardar el player: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        p.add(btnRegister, gdc);

        return p;
    }

    private static void handleImageSelection(JLabel previewLabel, String[] selectedImagePath) {
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
            if (selectedImagePath != null && selectedImagePath.length > 0) {
                selectedImagePath[0] = selectedFile.getAbsolutePath();
            }
        }
    }
}