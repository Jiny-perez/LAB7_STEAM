/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steam.Frames;

import java.awt.BorderLayout;
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
public class EliminarPlayer {

    public static JPanel createPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(15, 15, 15, 15);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        gdc.gridx = 0;
        gdc.gridy = 0;
        gdc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("BAJA DE USUARIO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        p.add(lblTitulo, gdc);

        gdc.gridwidth = 1;
        gdc.gridx = 0;
        gdc.gridy = 1;
        gdc.weightx = 0.0;
        p.add(new JLabel("Código del player:"), gdc);
        gdc.gridx = 1;
        gdc.weightx = 1.0;
        JTextField txtCodigo = new JTextField();
        txtCodigo.setPreferredSize(new Dimension(200, 30));
        p.add(txtCodigo, gdc);

        gdc.gridx = 0;
        gdc.gridy = 2;
        gdc.gridwidth = 2;
        gdc.weightx = 0.0;
        JButton btnDelete = new JButton("Eliminar Player");
        btnDelete.setPreferredSize(new Dimension(220, 44));
        btnDelete.addActionListener(ae -> {
            if (!txtCodigo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(p, "Eliminado usuario: " + txtCodigo.getText());
            }
        });
        p.add(btnDelete, gdc);

        return p;
    }
}
