package steam.Frames;

import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import steam.Frames.AdminFrame;
import steam.Frames.UserFrame;
import steam.Steam;

public class SteamLogin extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private final Steam steam;  

    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "1234";   

    public SteamLogin() {
        
        steam = new Steam(); 
        
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

        btnLogin.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        if (user.equalsIgnoreCase(ADMIN_USER) && pass.equals(ADMIN_PASS)) {
            JOptionPane.showMessageDialog(this, "Bienvenido Administrador.");
            new AdminFrame(steam).setVisible(true);
            this.dispose();
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile("steam/rplayers", "r")) {

            while (raf.getFilePointer() < raf.length()) {

                int code = raf.readInt();
                String username = raf.readUTF();
                String password = raf.readUTF();
                String nombre = raf.readUTF();
                long nacimiento = raf.readLong();
                int cont = raf.readInt();
                String imagen = raf.readUTF();
                String tipo = raf.readUTF();

                if (username.equals(user) && password.equals(pass)) {
                    JOptionPane.showMessageDialog(this, "Bienvenido " + nombre);
                    new UserFrame(steam /*, code */).setVisible(true);
                    this.dispose();
                    return;
                }
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer archivo de usuarios:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.",
                "Acceso denegado", JOptionPane.ERROR_MESSAGE);
    }
}
