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
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import steam.Steam;

/**
 *
 * @author esteb
 */
public class RegistrarJuego {

   private static void handleImageSelection(JLabel previewLabel, String[] selectedPathHolder) {
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
            if (selectedPathHolder != null && selectedPathHolder.length > 0) {
                selectedPathHolder[0] = selectedFile.getAbsolutePath();
            }
        }
    }

    public static JPanel createPanel(Steam steam) {
        final JLabel imagePreview = new JLabel("Sin portada", SwingConstants.CENTER);
        final String[] selectedImagePath = new String[]{ "" };

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
        final JTextField txtTitulo = new JTextField(30);
        p.add(txtTitulo, gdc);

        gdc.gridx = 0;
        gdc.gridy = 2;
        p.add(new JLabel("Sistema Operativo:"), gdc);
        gdc.gridx = 1;
        final JComboBox<String> cbOs = new JComboBox<>(new String[]{"Windows", "Mac", "Linux"});
        p.add(cbOs, gdc);

        gdc.gridx = 0;
        gdc.gridy = 3;
        p.add(new JLabel("Edad mínima:"), gdc);
        gdc.gridx = 1;
        final JTextField txtEdad = new JTextField(10);
        p.add(txtEdad, gdc);

        gdc.gridx = 0;
        gdc.gridy = 4;
        p.add(new JLabel("Precio ($):"), gdc);
        gdc.gridx = 1;
        final JTextField txtPrecio = new JTextField(10);
        p.add(txtPrecio, gdc);

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
        btnChooseImage.addActionListener(ae -> handleImageSelection(imagePreview, selectedImagePath));
        p.add(btnChooseImage, gdc);

        gdc.gridx = 0;
        gdc.gridy = 7;
        gdc.gridwidth = 2;
        JButton btnRegister = new JButton("Publicar Juego");
        btnRegister.setPreferredSize(new Dimension(240, 44));
        btnRegister.addActionListener(ae -> {
            String titulo = txtTitulo.getText().trim();
            String edadText = txtEdad.getText().trim();
            String precioText = txtPrecio.getText().trim();
            String rutaImg = selectedImagePath[0] == null ? "" : selectedImagePath[0];
            String osSelected = (String) cbOs.getSelectedItem();

            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(p, "Ingresa el título del juego.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int edadMin;
            try {
                edadMin = Integer.parseInt(edadText);
                if (edadMin < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(p, "Edad mínima inválida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double precio;
            try {
                precio = Double.parseDouble(precioText);
                if (precio < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(p, "Precio inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            char soChar = 'W';
            if ("Windows".equalsIgnoreCase(osSelected)) soChar = 'W';
            else if ("Mac".equalsIgnoreCase(osSelected)) soChar = 'M';
            else if ("Linux".equalsIgnoreCase(osSelected)) soChar = 'L';

            try {
                if (steam == null) {
                    JOptionPane.showMessageDialog(p, "Instancia de Steam no proporcionada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                steam.addGame(titulo, soChar, edadMin, precio, rutaImg);
                JOptionPane.showMessageDialog(p, "Juego publicado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                txtTitulo.setText("");
                txtEdad.setText("");
                txtPrecio.setText("");
                selectedImagePath[0] = "";
                imagePreview.setIcon(null);
                imagePreview.setText("Sin portada");
                cbOs.setSelectedIndex(0);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(p, "Error al publicar el juego: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        p.add(btnRegister, gdc);

        return p;
    }
}
