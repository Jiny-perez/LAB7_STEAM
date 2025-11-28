package steam.Frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminFrame extends JFrame {

    private JPanel centerPanel; 

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

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mostrarBienvenida(); 

        add(left, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        btnRegPlayer.addActionListener(e -> cambiarPanelCentral(RegistrarPlayer.createPanel()));
        btnModPlayer.addActionListener(e -> cambiarPanelCentral(ModificarPlayer.createPanel()));
        btnDelPlayer.addActionListener(e -> cambiarPanelCentral(EliminarPlayer.createPanel()));

        btnRegGame.addActionListener(e -> cambiarPanelCentral(RegistrarJuego.createPanel()));
        btnModGame.addActionListener(e -> cambiarPanelCentral(ModificarJuego.createPanel()));
        btnDelGame.addActionListener(e -> cambiarPanelCentral(EliminarJuego.createPanel()));
        
        btnChangePrice.addActionListener(e -> cambiarPanelCentral(CambiarPrecio.createPanel()));
        btnGenReport.addActionListener(e -> cambiarPanelCentral(GenerarReporte.createPanel()));

        // --- PANELES VISORES ---
        btnViewGames.addActionListener(e -> mostrarPanelJuegos());
        btnViewReports.addActionListener(e -> cambiarPanelCentral(VerReportes.createPanel()));
       // btnViewDownloads.addActionListener(e -> cambiarPanelCentral(AdminViewDownloadsFrame.createPanel()));
    }

    // -------------------------------------------------------------------------
    // MÉTODO CENTRAL PARA EL CAMBIO DINÁMICO
    // -------------------------------------------------------------------------

    private void cambiarPanelCentral(JPanel nuevoPanel) {
        centerPanel.removeAll(); 
        centerPanel.add(nuevoPanel, BorderLayout.CENTER); 
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void mostrarBienvenida() {
        centerPanel.removeAll(); 
        JLabel lblCenter = new JLabel("Panel principal de administración");
        lblCenter.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblCenter.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JTextArea txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtArea.setText("Bienvenido al sistema Steam Admin.\n\nUsa el menú izquierdo para gestionar Archivos Binarios.");
        
        centerPanel.add(lblCenter, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(txtArea), BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }
    
    private void mostrarPanelJuegos() {
        centerPanel.removeAll(); 
        JLabel lblTitulo = new JLabel("Listado de Juegos (Desde Archivo Binario)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 14)); 
        String contenido = cargarDatosDelArchivoBinario();
        ta.setText(contenido);
        centerPanel.add(lblTitulo, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(ta), BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private String cargarDatosDelArchivoBinario() {
        File archivo = new File("steam_juegos.dat"); 
        if (!archivo.exists()) {
            return "No hay juegos registrados aun (El archivo no existe).\nCrea uno registrando un juego.";
        }
        String texto = ""; 
        texto = texto + String.format("%-10s | %-25s | %-10s | %-10s\n", "CODIGO", "TITULO", "PRECIO", "EDAD");
        texto = texto + "----------------------------------------------------------------------\n";

        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int codigo = raf.readInt();
                String titulo = raf.readUTF(); 
                double precio = raf.readDouble();
                int edad = raf.readInt();
                texto = texto + String.format("%-10d | %-25s | $%-9.2f | %-10d\n", codigo, titulo, precio, edad);
            }
        } catch (IOException e) {
            texto = texto + "ERROR al leer el archivo binario: " + e.getMessage();
        }
        return texto;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new AdminFrame().setVisible(true));
    }
}