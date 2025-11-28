/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steam.Frames;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author esteb
 */
public class UserFrame extends JFrame {
    
    public UserFrame() { 
        setTitle("STEAM Clone - Panel Principal de Usuario");
        setSize(1100, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton btnCatalog = new JButton("Ver catálogo de videojuegos");
        JButton btnDownload = new JButton("Descargar videojuego");
        JButton btnViewDownloaded = new JButton("Ver juegos descargados");
        JButton btnProfile = new JButton("Configurar perfil ()"); 
        JButton btnCounter = new JButton("Ver contador de descargas");
        JButton btnLogout = new JButton("Cerrar sesión");
                
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(new EmptyBorder(20, 20, 20, 20));
        left.setPreferredSize(new Dimension(320, 0));
        
        JLabel lbl = new JLabel("Operaciones de usuario");
        lbl.setFont(lbl.getFont().deriveFont(18f));
        left.add(lbl);
        left.add(Box.createVerticalStrut(12));
        
        Dimension bsize = new Dimension(260, 40);
        
        for (JButton b : new JButton[]{btnCatalog, btnDownload, btnViewDownloaded, btnProfile, btnCounter}) {
            b.setMaximumSize(bsize);
            left.add(b);
            left.add(Box.createVerticalStrut(8));
        }
        
        left.add(Box.createVerticalStrut(16));
        btnLogout.setMaximumSize(bsize);
        left.add(btnLogout);

        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(new EmptyBorder(20, 20, 20, 20));
        //JLabel lblCenter = new JLabel("Bienvenido, " + user.getName() + " (" + user.getUsername() + ")");
        //lblCenter.setFont(lblCenter.getFont().deriveFont(16f));
        //center.add(lblCenter, BorderLayout.NORTH);
        
        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setFont(ta.getFont().deriveFont(14f));
        ta.setText("Aquí se mostrará un resumen de la actividad del usuario (ej. juegos más recientes, recomendaciones, etc.).");
        center.add(new JScrollPane(ta), BorderLayout.CENTER);

        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        
        
        btnCatalog.addActionListener(e -> {
           //new CatalogFrame().setVisible(true);
        });
        
        // 2. Descargar Videojuego: Muestra un mensaje pendiente
        btnDownload.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Descarga: funcionalidad pendiente de implementar.");
        });
        
        // 3. Ver Juegos Descargados: Abre la ventana de Descargas, pasando el usuario
        btnViewDownloaded.addActionListener(e -> {
            //new UserDownloadsFrame(user).setVisible(true);
        });
        
        // 4. Configurar Perfil: Abre la ventana de Perfil, pasando el usuario
        btnProfile.addActionListener(e -> {
            //new UserProfileFrame(user).setVisible(true);
        });
        
        btnCounter.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Contador: funcionalidad pendiente de implementar.");
        });
        
        btnLogout.addActionListener(e -> {
        });
    }
    
    public static void main(String[] args) {
        new UserFrame().setVisible(true);
    }
}