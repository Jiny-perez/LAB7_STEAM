package steam.Frames;

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

public class ModificarJuego {
    
    private static void handleImageSelection(JLabel previewLabel, String[] rutaImagenHolder) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            rutaImagenHolder[0] = selectedFile.getAbsolutePath(); 

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

    public static JPanel createPanel(Steam steam) {
        final JTextField txtCodigo = new JTextField(12);
        final JTextField txtTitulo = new JTextField(30);
        final JComboBox<String> cbSO = new JComboBox<>(new String[]{"Windows", "Mac", "Linux"});
        final JTextField txtEdad = new JTextField(10);
        final JTextField txtPrecio = new JTextField(10);
        final JLabel imagePreview = new JLabel("Cargar Imagen", SwingConstants.CENTER);

        final int[] contDescargasHolder = new int[]{0};
        final String[] rutaImagenHolder = new String[]{null};
        final boolean[] juegoCargado = new boolean[]{false};
        
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(12, 12, 12, 12);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        gdc.gridx = 0; gdc.gridy = 0; gdc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("MODIFICAR JUEGO EXISTENTE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        p.add(lblTitulo, gdc);

        gdc.gridwidth = 1;
        gdc.gridx = 0; gdc.gridy = 1;
        p.add(new JLabel("Código del juego:"), gdc);
        gdc.gridx = 1;
        p.add(txtCodigo, gdc);
        
        gdc.gridx = 0; gdc.gridy = 2; gdc.gridwidth = 2;
        JButton btnLoad = new JButton("Buscar y Cargar Datos");
        btnLoad.addActionListener(ae -> {
            String codigoStr = txtCodigo.getText().trim();
            if (codigoStr.isEmpty()) {
                JOptionPane.showMessageDialog(p, "Ingresa el código del juego.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int codigo;
            try {
                codigo = Integer.parseInt(codigoStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(p, "El código debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            File archivo = new File("steam/games.stm");
            if (!archivo.exists()) {
                JOptionPane.showMessageDialog(p, "No existe el archivo de juegos (steam/games.stm).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean found = false;
            try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(archivo, "r")) {
                while (raf.getFilePointer() < raf.length()) {
                    int code = raf.readInt();
                    String title = raf.readUTF();
                    char so = raf.readChar();
                    int edad = raf.readInt();
                    double precio = raf.readDouble();
                    int cont = raf.readInt();
                    String rutaImg = raf.readUTF();

                    if (code == codigo) {
                        txtTitulo.setText(title);
                        txtEdad.setText(String.valueOf(edad));
                        txtPrecio.setText(String.valueOf(precio));
                        contDescargasHolder[0] = cont;
                        rutaImagenHolder[0] = rutaImg;

                        switch (Character.toUpperCase(so)) {
                            case 'W' -> cbSO.setSelectedItem("Windows");
                            case 'M' -> cbSO.setSelectedItem("Mac");
                            case 'L' -> cbSO.setSelectedItem("Linux");
                            default -> cbSO.setSelectedIndex(0);
                        }

                        imagePreview.setText("Sin imagen");
                        imagePreview.setIcon(null);
                        if (rutaImg != null && !rutaImg.isBlank()) {
                            File imgFile = new File(rutaImg);
                            if (imgFile.exists()) {
                                ImageIcon originalIcon = new ImageIcon(rutaImg);
                                Image scaledImage = originalIcon.getImage().getScaledInstance(
                                        imagePreview.getPreferredSize().width,
                                        imagePreview.getPreferredSize().height,
                                        Image.SCALE_SMOOTH
                                );
                                imagePreview.setIcon(new ImageIcon(scaledImage));
                                imagePreview.setText("");
                            }
                        }

                        juegoCargado[0] = true;
                        found = true;
                        break;
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(p, "Error al leer el archivo de juegos:\n" + ex.getMessage(),
                        "Error IO", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!found) {
                juegoCargado[0] = false;
                JOptionPane.showMessageDialog(p, "No se encontró un juego con código " + codigo + ".", "No encontrado", JOptionPane.WARNING_MESSAGE);
            }
        });
        p.add(btnLoad, gdc);
        gdc.gridwidth = 1; 

        gdc.gridx = 0; gdc.gridy = 3; p.add(new JLabel("Título:"), gdc);
        gdc.gridx = 1; p.add(txtTitulo, gdc);

        gdc.gridx = 0; gdc.gridy = 4; p.add(new JLabel("Sistema Operativo:"), gdc);
        gdc.gridx = 1; p.add(cbSO, gdc);
        
        gdc.gridx = 0; gdc.gridy = 5; p.add(new JLabel("Edad mínima:"), gdc);
        gdc.gridx = 1; p.add(txtEdad, gdc);
        
        gdc.gridx = 0; gdc.gridy = 6; p.add(new JLabel("Precio ($):"), gdc);
        gdc.gridx = 1; p.add(txtPrecio, gdc);

        gdc.gridx = 0; gdc.gridy = 7; p.add(new JLabel("Imagen (preview):"), gdc);
        gdc.gridx = 1;
        imagePreview.setPreferredSize(new Dimension(320, 200));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        p.add(imagePreview, gdc);

        gdc.gridx = 1; gdc.gridy = 8;
        JButton btnChooseImage = new JButton("Cambiar Portada");
        btnChooseImage.addActionListener(ae -> handleImageSelection(imagePreview, rutaImagenHolder)); 
        p.add(btnChooseImage, gdc);

        gdc.gridx = 0; gdc.gridy = 9; gdc.gridwidth = 2;
        JButton btnSave = new JButton("Guardar Cambios");
        btnSave.setPreferredSize(new Dimension(240, 44));
        btnSave.addActionListener(ae -> {
            if (!juegoCargado[0]) {
                JOptionPane.showMessageDialog(p, "Primero carga un juego válido por código.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String codigoStr = txtCodigo.getText().trim();
            String titulo = txtTitulo.getText().trim();
            String edadStr = txtEdad.getText().trim();
            String precioStr = txtPrecio.getText().trim();

            if (codigoStr.isEmpty() || titulo.isEmpty() || edadStr.isEmpty() || precioStr.isEmpty()) {
                JOptionPane.showMessageDialog(p, "Completa todos los campos antes de guardar.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int codigo;
            int edadMin;
            double precio;

            try {
                codigo = Integer.parseInt(codigoStr);
                edadMin = Integer.parseInt(edadStr);
                precio = Double.parseDouble(precioStr);
                if (precio < 0 || edadMin < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(p, "Edad y precio deben ser números válidos (no negativos).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String soStr = (String) cbSO.getSelectedItem();
            char soChar = 'W';
            if ("Mac".equals(soStr)) soChar = 'M';
            else if ("Linux".equals(soStr)) soChar = 'L';

            String rutaImgFinal = rutaImagenHolder[0] != null ? rutaImagenHolder[0] : "";

            try {
                boolean ok = steam.modificarGame(
                        codigo,
                        titulo,
                        soChar,
                        edadMin,
                        precio,
                        contDescargasHolder[0],  
                        rutaImgFinal
                );

                if (ok) {
                    JOptionPane.showMessageDialog(p, "Juego modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(p, "No se pudo modificar el juego (no encontrado).", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(p, "Error al modificar el juego:\n" + ex.getMessage(), "Error IO", JOptionPane.ERROR_MESSAGE);
            }
        });
        p.add(btnSave, gdc);
        
        return p;
    }
}
