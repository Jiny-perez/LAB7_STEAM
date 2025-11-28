/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steam;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
public class SteamLogin extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;

    public SteamLogin() {
        setTitle("Steam - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(10, 10, 10, 10); 
        gdc.fill = GridBagConstraints.HORIZONTAL; 

        gdc.gridx = 0;
        gdc.gridy = 0;
        gdc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("Login", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panel.add(lblTitulo, gdc);

        gdc.gridwidth = 1;

        gdc.gridx = 0;
        gdc.gridy = 1;
        panel.add(new JLabel("Usuario:", SwingConstants.RIGHT), gdc);

        gdc.gridx = 1;
        txtUser = new JTextField();
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUser.setPreferredSize(new Dimension(250, 30));
        panel.add(txtUser, gdc);

        gdc.gridx = 0;
        gdc.gridy = 2;
        panel.add(new JLabel("Contraseña:", SwingConstants.RIGHT), gdc);

        gdc.gridx = 1;
        txtPass = new JPasswordField();
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPass.setPreferredSize(new Dimension(250, 30));
        panel.add(txtPass, gdc);

        gdc.gridx = 0;
        gdc.gridy = 3;
        gdc.gridwidth = 2;

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setPreferredSize(new Dimension(150, 40));
        btnLogin.setFocusPainted(false);
        btnPanel.add(btnLogin);

        panel.add(btnPanel, gdc);

        add(panel);

        btnLogin.addActionListener(e -> {
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }

        SwingUtilities.invokeLater(() -> new SteamLogin().setVisible(true));
    }
}
