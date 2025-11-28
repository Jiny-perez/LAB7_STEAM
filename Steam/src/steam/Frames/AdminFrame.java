/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steam.Frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author esteb
 */
public class AdminFrame extends JFrame{
    public AdminFrame() {
        setTitle("ADMIN - Panel Principal");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(new EmptyBorder(20, 20, 20, 20));
        left.setPreferredSize(new Dimension(320, 0));

        JLabel lblTitle = new JLabel("Operaciones de administrador");
        lblTitle.setFont(lblTitle.getFont().deriveFont(18f));
        left.add(lblTitle);
        left.add(Box.createVerticalStrut(12));

        JButton btnRegPlayer = new JButton("Registrar player");
        JButton btnModPlayer = new JButton("Modificar player");
        JButton btnDelPlayer = new JButton("Eliminar player");
        JButton btnRegGame = new JButton("Registrar juego");
        JButton btnModGame = new JButton("Modificar juego");
        JButton btnDelGame = new JButton("Eliminar juego");
        JButton btnChangePrice = new JButton("Cambiar precio de juego");
        JButton btnViewGames = new JButton("Ver lista completa de juegos");
        JButton btnViewReports = new JButton("Ver reportes");
        JButton btnGenReport = new JButton("Generar reporte de cliente");
        JButton btnViewDownloads = new JButton("Ver descargas generadas");
        
        Dimension btnSize = new Dimension(260, 40);
        JButton[] buttons = {
            btnRegPlayer, btnModPlayer, btnDelPlayer, 
            btnRegGame, btnModGame, btnDelGame, 
            btnChangePrice, btnViewGames, btnViewReports, 
            btnGenReport, btnViewDownloads
        };

        for (JButton b : buttons) {
            b.setMaximumSize(btnSize);
            b.setPreferredSize(btnSize);
            left.add(b);
            left.add(Box.createVerticalStrut(8));
        }


        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblCenter = new JLabel("Panel principal de administración (vista resumen)");
        lblCenter.setFont(lblCenter.getFont().deriveFont(16f));
        center.add(lblCenter, BorderLayout.NORTH);
        
        JTextArea txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setFont(txtArea.getFont().deriveFont(14f));
        txtArea.setText("Área de resumen / previsualización\n(Placeholder)");
        center.add(new JScrollPane(txtArea), BorderLayout.CENTER);

        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);

        
        btnRegPlayer.addActionListener(e -> {
            System.out.println("Abrir Registrar Player");
        });
        
        btnModPlayer.addActionListener(e -> {
             System.out.println("Abrir Modificar Player");
        });
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminFrame().setVisible(true));
    }
}
