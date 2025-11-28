/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steam.Frames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;

/**
 *
 * @author esteb
 */
public class GenerarReporte {
    public static JPanel createPanel() {
        final JTextField txtCodigo = new JTextField();
        
        // --- PANEL DE INTERFAZ ---
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(15, 15, 15, 15);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        gdc.gridx = 0; 
        gdc.gridy = 0;
        gdc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("GENERAR REPORTE DE CLIENTE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        p.add(lblTitulo, gdc);

        gdc.gridwidth = 1; 

        gdc.gridx = 0; gdc.gridy = 1; gdc.weightx = 0.0;
        JLabel lblCod = new JLabel("Código del cliente:");
        lblCod.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        p.add(lblCod, gdc);

        gdc.gridx = 1; gdc.weightx = 1.0;
        txtCodigo.setPreferredSize(new Dimension(200, 30));
        p.add(txtCodigo, gdc);

        gdc.gridx = 0; gdc.gridy = 2; gdc.gridwidth = 2; gdc.weightx = 0.0;
        
        JButton btnGen = new JButton("Generar Reporte");
        btnGen.setPreferredSize(new Dimension(220, 44));
        p.add(btnGen, gdc);

        btnGen.addActionListener(e -> {
            String codigo = txtCodigo.getText();
            
            if(codigo.trim().isEmpty()){
                JOptionPane.showMessageDialog(p, "Por favor ingresa el código del cliente.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                // Aquí iría la lógica para buscar el cliente en el archivo binario y generar el reporte
                JOptionPane.showMessageDialog(p, 
                    "Reporte generado exitosamente para el cliente: " + codigo + " (Simulado)", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        return p;
    }
}
