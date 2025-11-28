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
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import steam.Steam;
import steam.Steam;

/**
 *
 * @author esteb
 */
public class ModificarPlayer {
 public static JPanel createPanel(Steam steam) {
        final JDateChooser dateChooser = new JDateChooser(new Date());
        final JPasswordField txtPass = new JPasswordField(25);
        final JTextField txtCodigo = new JTextField(12);
        final JLabel imagePreview = new JLabel("Cargar Imagen", SwingConstants.CENTER);
        final char defaultEchoChar = txtPass.getEchoChar();

        final JTextField txtUsername = new JTextField(30);
        final JTextField txtNombre = new JTextField(30);
        final JComboBox<String> tipoCombo = new JComboBox<>(new String[] { "regular", "premium" });

        final long[] loadedNacimiento = new long[1];
        final int[] loadedContador = new int[1];
        final String[] loadedImagePath = new String[1];

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
        btnLoad.addActionListener(ae -> {
            String codeText = txtCodigo.getText().trim();
            if (codeText.isEmpty()) {
                JOptionPane.showMessageDialog(p, "Ingresa un código.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int code;
            try {
                code = Integer.parseInt(codeText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(p, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                RandomAccessFile raf = steam.getRplayers();
                if (raf == null) {
                    JOptionPane.showMessageDialog(p, "Archivo de players no inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean found = false;
                raf.seek(0);
                while (raf.getFilePointer() < raf.length()) {
                    int readCode = raf.readInt();
                    long posAfterCode = raf.getFilePointer();
                    String username = raf.readUTF();
                    String password = raf.readUTF();
                    String name = raf.readUTF();
                    long nacimiento = raf.readLong();
                    int contador = -1;
                    String imagen = "";
                    String tipo = "";
                    try {
                        contador = raf.readInt();
                        imagen = raf.readUTF();
                        tipo = raf.readUTF();
                    } catch (IOException ex) {
                    }
                    if (readCode == code) {
                        txtUsername.setText(username);
                        txtPass.setText(password);
                        txtNombre.setText(name);
                        dateChooser.setDate(new Date(nacimiento));
                        loadedNacimiento[0] = nacimiento;
                        loadedContador[0] = contador;
                        loadedImagePath[0] = imagen;
                        tipoCombo.setSelectedItem(tipo == null || tipo.isEmpty() ? "regular" : tipo);
                        
                        if (imagen != null && !imagen.isEmpty()) {
                            File f = new File(imagen);
                            if (f.exists()) {
                                ImageIcon originalIcon = new ImageIcon(imagen);
                                Image scaled = originalIcon.getImage().getScaledInstance(
                                        imagePreview.getPreferredSize().width,
                                        imagePreview.getPreferredSize().height,
                                        Image.SCALE_SMOOTH
                                );
                                imagePreview.setIcon(new ImageIcon(scaled));
                                imagePreview.setText("");
                            } else {
                                imagePreview.setIcon(null);
                                imagePreview.setText("Imagen no encontrada");
                            }
                        } else {
                            imagePreview.setIcon(null);
                            imagePreview.setText("Sin imagen");
                        }
                        found = true;
                        break;
                    } else {
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(p, "Player con código " + code + " no encontrado.", "No encontrado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(p, "Datos cargados. Puedes editarlos y luego guardar.", "Listo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(p, "Error leyendo players: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        p.add(btnLoad, gdc);
        gdc.gridwidth = 1;

        gdc.gridx = 0;
        gdc.gridy = 3;
        p.add(new JLabel("Username:"), gdc);
        gdc.gridx = 1;
        p.add(txtUsername, gdc);

        gdc.gridx = 0;
        gdc.gridy = 4;
        p.add(new JLabel("Password:"), gdc);
        gdc.gridx = 1;
        JPanel passPanel = new JPanel(new BorderLayout(5, 0));
        JToggleButton btnShowPass = new JToggleButton();
        btnShowPass.setPreferredSize(new Dimension(50, 30));
        btnShowPass.addActionListener(e -> txtPass.setEchoChar(btnShowPass.isSelected() ? (char) 0 : defaultEchoChar));
        passPanel.add(txtPass, BorderLayout.CENTER);
        passPanel.add(btnShowPass, BorderLayout.EAST);
        p.add(passPanel, gdc);

        gdc.gridx = 0;
        gdc.gridy = 5;
        p.add(new JLabel("Nombre:"), gdc);
        gdc.gridx = 1;
        p.add(txtNombre, gdc);

        gdc.gridx = 0;
        gdc.gridy = 6;
        p.add(new JLabel("Fecha de nacimiento:"), gdc);
        gdc.gridx = 1;
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(150, 30));
        p.add(dateChooser, gdc);

        gdc.gridx = 0;
        gdc.gridy = 7;
        p.add(new JLabel("Tipo de usuario:"), gdc);
        gdc.gridx = 1;
        p.add(tipoCombo, gdc);

        gdc.gridx = 0;
        gdc.gridy = 8;
        p.add(new JLabel("Imagen:"), gdc);
        gdc.gridx = 1;
        imagePreview.setPreferredSize(new Dimension(260, 160));
        imagePreview.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY));
        p.add(imagePreview, gdc);

        gdc.gridx = 1;
        gdc.gridy = 9;
        JButton btnChooseImage = new JButton("Cambiar imagen");
        btnChooseImage.addActionListener(ae -> {
            handleImageSelection(imagePreview, loadedImagePath);
        });
        p.add(btnChooseImage, gdc);

        gdc.gridx = 0;
        gdc.gridy = 10;
        gdc.gridwidth = 2;
        JButton btnSave = new JButton("Guardar Cambios");
        btnSave.setPreferredSize(new Dimension(220, 44));
        btnSave.addActionListener(ae -> {
            String codeText = txtCodigo.getText().trim();
            if (codeText.isEmpty()) {
                JOptionPane.showMessageDialog(p, "Ingresa un código antes de guardar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int code;
            try {
                code = Integer.parseInt(codeText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(p, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newUsername = txtUsername.getText().trim();
            String newPassword = new String(txtPass.getPassword());
            String newName = txtNombre.getText().trim();
            Date d = dateChooser.getDate();
            long newNacimiento = d == null ? loadedNacimiento[0] : d.getTime();
            int newContador = loadedContador[0]; // mantener contador leido (usuario puede no editar)
            String newImagePath = loadedImagePath[0] == null ? "" : loadedImagePath[0];
            String newTipo = (String) tipoCombo.getSelectedItem();

            try {
                boolean ok = steam.modificarPlayer(code, newUsername, newPassword, newName, newNacimiento, newContador, newImagePath, newTipo);
                if (ok) {
                    JOptionPane.showMessageDialog(p, "Jugador modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(p, "No se pudo modificar el jugador.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NoSuchElementException ex) {
                JOptionPane.showMessageDialog(p, ex.getMessage(), "No existe", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(p, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        p.add(btnSave, gdc);

        return p;
    }

    private static void handleImageSelection(JLabel previewLabel, String[] loadedImagePathHolder) {
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
            if (loadedImagePathHolder != null && loadedImagePathHolder.length > 0) {
                loadedImagePathHolder[0] = selectedFile.getAbsolutePath();
            }
        }
    }
}
