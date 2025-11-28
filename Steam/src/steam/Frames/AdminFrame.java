package steam.Frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import steam.Steam;

/**
 *
 * @author esteb
 */
public class AdminFrame extends JFrame {

    private JPanel centerPanel;
    private Steam steam;

    public AdminFrame() {
        steam = new Steam();
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

        btnRegPlayer.addActionListener(e -> cambiarPanelCentral(RegistrarPlayer.createPanel(steam)));
        btnModPlayer.addActionListener(e -> cambiarPanelCentral(ModificarPlayer.createPanel(steam)));
        btnDelPlayer.addActionListener(e -> cambiarPanelCentral(EliminarPlayer.createPanel(steam)));

        btnRegGame.addActionListener(e -> cambiarPanelCentral(RegistrarJuego.createPanel(steam)));
        btnModGame.addActionListener(e -> cambiarPanelCentral(ModificarJuego.createPanel(steam)));
        btnDelGame.addActionListener(e -> cambiarPanelCentral(EliminarJuego.createPanel(steam)));

        btnChangePrice.addActionListener(e -> cambiarPanelCentral(CambiarPrecio.createPanel(steam)));
        btnGenReport.addActionListener(e -> cambiarPanelCentral(GenerarReporte.createPanel(steam)));

        btnViewGames.addActionListener(e -> mostrarPanelJuegos());
        btnViewReports.addActionListener(e -> cambiarPanelCentral(VerReportes.createPanel()));

        btnViewDownloads.addActionListener(e -> mostrarPanelDescargas());
    }

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
        txtArea.setText("Bienvenido al sistema Steam Admin.\n\n"
                + "Usa el menú izquierdo para gestionar Archivos Binarios.");

        centerPanel.add(lblCenter, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(txtArea), BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void mostrarPanelJuegos() {
        centerPanel.removeAll();

        JLabel lblTitulo = new JLabel("Listado de Juegos (steam/games.stm)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));

        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 14));

        String contenido = cargarDatosDelArchivoBinarioJuegos();
        ta.setText(contenido);

        centerPanel.add(lblTitulo, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(ta), BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private String cargarDatosDelArchivoBinarioJuegos() {
        File archivo = new File("steam/games.stm");
        if (!archivo.exists()) {
            return "No hay juegos registrados aún (el archivo steam/games.stm no existe).\n"
                    + "Registra un juego desde el panel correspondiente.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-8s | %-25s | %-4s | %-10s | %-6s | %-10s%n",
                "COD", "TITULO", "SO", "PRECIO", "EDAD", "DESCARGAS"));
        sb.append("-------------------------------------------------------------------------------\n");

        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int codigo = raf.readInt();
                String titulo = raf.readUTF();
                char so = raf.readChar();
                int edad = raf.readInt();
                double precio = raf.readDouble();
                int contDescargas = raf.readInt();
                String rutaImg = raf.readUTF();

                sb.append(String.format("%-8d | %-25s | %-4s | $%-9.2f | %-6d | %-10d%n",
                        codigo, titulo, String.valueOf(so), precio, edad, contDescargas));
            }
        } catch (IOException e) {
            sb.append("\nERROR al leer steam/games.stm: ").append(e.getMessage());
        }
        return sb.toString();
    }

    private void mostrarPanelDescargas() {
        centerPanel.removeAll();

        JLabel lblTitulo = new JLabel("Descargas generadas (carpeta steam/downloads)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));

        JPanel panelDescargas = new JPanel(new BorderLayout(10, 10));

        DefaultListModel<File> modeloLista = new DefaultListModel<>();
        JList<File> listaArchivos = new JList<>(modeloLista);
        listaArchivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        File carpetaDownloads = new File("steam/downloads");
        if (carpetaDownloads.exists() && carpetaDownloads.isDirectory()) {
            File[] archivos = carpetaDownloads.listFiles((dir, name) -> name.toLowerCase().endsWith(".stm") || name.toLowerCase().endsWith(".txt"));
            if (archivos != null && archivos.length > 0) {
                for (File f : archivos) {
                    modeloLista.addElement(f);
                }
            }
        }

        listaArchivos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof File) {
                    File f = (File) value;
                    setText(f.getName());
                }
                return this;
            }
        });

        JScrollPane scrollLista = new JScrollPane(listaArchivos);
        scrollLista.setPreferredSize(new Dimension(260, 0));

        JTextArea taContenido = new JTextArea();
        taContenido.setEditable(false);
        taContenido.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollContenido = new JScrollPane(taContenido);

        listaArchivos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                File seleccionado = listaArchivos.getSelectedValue();
                if (seleccionado != null) {
                    taContenido.setText(leerContenidoTexto(seleccionado));
                    taContenido.setCaretPosition(0);
                }
            }
        });

        panelDescargas.add(scrollLista, BorderLayout.WEST);
        panelDescargas.add(scrollContenido, BorderLayout.CENTER);

        centerPanel.add(lblTitulo, BorderLayout.NORTH);
        centerPanel.add(panelDescargas, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private String leerContenidoTexto(File archivo) {
        StringBuilder sb = new StringBuilder();
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append(System.lineSeparator());
            }
        } catch (IOException e) {
            sb.append("ERROR al leer archivo ").append(archivo.getName())
                    .append(": ").append(e.getMessage());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(() -> new AdminFrame().setVisible(true));
    }
}
