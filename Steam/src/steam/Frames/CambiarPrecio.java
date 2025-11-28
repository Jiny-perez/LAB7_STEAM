/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steam.Frames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author esteb
 */

public class CambiarPrecio {

    public static JPanel createPanel() {
        final JTextField txtCodigo = new JTextField();
        final JTextField txtPrecio = new JTextField();
        
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(15, 15, 15, 15);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        gdc.gridx = 0; 
        gdc.gridy = 0;
        gdc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("ACTUALIZAR COSTO DE JUEGO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        p.add(lblTitulo, gdc);

        gdc.gridwidth = 1; 

        gdc.gridx = 0; gdc.gridy = 1; gdc.weightx = 0.0;
        JLabel lblCod = new JLabel("Código del juego:");
        lblCod.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        p.add(lblCod, gdc);

        gdc.gridx = 1; gdc.weightx = 1.0;
        txtCodigo.setPreferredSize(new Dimension(200, 30));
        p.add(txtCodigo, gdc);

        gdc.gridx = 0; gdc.gridy = 2; gdc.weightx = 0.0;
        JLabel lblPrecio = new JLabel("Nuevo Precio ($):");
        lblPrecio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        p.add(lblPrecio, gdc);

        gdc.gridx = 1; gdc.weightx = 1.0;
        txtPrecio.setPreferredSize(new Dimension(200, 30));
        p.add(txtPrecio, gdc);

        gdc.gridx = 0; gdc.gridy = 3; gdc.gridwidth = 2; gdc.weightx = 0.0;
        
        JButton btnChange = new JButton("Aplicar Precio");
        btnChange.setPreferredSize(new Dimension(220, 44));
        p.add(btnChange, gdc);

        btnChange.addActionListener(e -> {
            String codigo = txtCodigo.getText();
            String precio = txtPrecio.getText();
            
            if(codigo.isEmpty() || precio.isEmpty()){
                JOptionPane.showMessageDialog(p, "Por favor llena todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    double precioNum = Double.parseDouble(precio);
                    
                    JOptionPane.showMessageDialog(p, 
                        "Precio actualizado para el juego " + codigo + " a: $" + String.format("%.2f", precioNum) + " (Simulado)",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(p, "El precio debe ser un número válido (ej. 19.99).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        return p;
    }
}