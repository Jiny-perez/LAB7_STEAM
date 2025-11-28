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

public class EliminarJuego {

    public static JPanel createPanel() {
        final JTextField txtCodigo = new JTextField();
        
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(30, 30, 30, 30)); // Márgenes amplios
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(15, 15, 15, 15);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        gdc.gridx = 0; 
        gdc.gridy = 0;
        gdc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("BAJA DE JUEGO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        p.add(lblTitulo, gdc);

        gdc.gridwidth = 1; 

        gdc.gridx = 0; 
        gdc.gridy = 1;
        gdc.weightx = 0.0;
        JLabel lblCodigo = new JLabel("Código del juego a eliminar:");
        lblCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        p.add(lblCodigo, gdc);

        gdc.gridx = 1; 
        gdc.weightx = 1.0;
        txtCodigo.setPreferredSize(new Dimension(200, 30));
        p.add(txtCodigo, gdc);

        gdc.gridx = 0; 
        gdc.gridy = 2;
        gdc.gridwidth = 2;
        gdc.weightx = 0.0;
        
        JButton btnDelete = new JButton("Eliminar Juego");
        btnDelete.setPreferredSize(new Dimension(220, 44)); // Botón grande
        p.add(btnDelete, gdc);

        btnDelete.addActionListener(ae -> {
            String codigo = txtCodigo.getText();
            
            if(codigo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(p, "Por favor escribe el código del juego.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int confirm = JOptionPane.showConfirmDialog(p, 
                        "¿Seguro que deseas eliminar el juego con código: " + codigo + "?\nEsta acción no se puede deshacer.", 
                        "Confirmar Eliminación", 
                        JOptionPane.YES_NO_OPTION);
                
                if(confirm == JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(p, "Juego eliminado exitosamente (Simulado).");
                }
            }
        });
        
        return p;
    }
}