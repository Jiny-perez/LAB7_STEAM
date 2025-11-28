package steam.Frames;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import steam.Steam;

/**
 *
 * @author esteb
 */
public class CatalogFrame extends JFrame {

    private final Steam steam;
    private final JPanel content;
    private final JComboBox<String> cbSO;
    private final JTextField txtBuscar;

    public CatalogFrame(Steam steam) {
        this.steam = steam;

        setTitle("Catálogo de Videojuegos");
        setSize(1100, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBorder(new EmptyBorder(12, 12, 12, 12));
        
        top.add(new JLabel("Filtrar por SO:"));
        cbSO = new JComboBox<>(new String[]{"Todos", "Windows", "Mac", "Linux"});
        top.add(cbSO);
        
        top.add(Box.createHorizontalStrut(20));
        top.add(new JLabel("Buscar por Título:"));
        txtBuscar = new JTextField(20);
        top.add(txtBuscar);

        add(top, BorderLayout.NORTH);

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(12, 12, 12, 12));
        
        JScrollPane sp = new JScrollPane(content);
        add(sp, BorderLayout.CENTER);

        cargarCatalogo();
    }
    private void cargarCatalogo() {
        content.removeAll();

        try {
            ArrayList<JPanel> gamePanels = steam.getGamePanels();

            if (gamePanels.isEmpty()) {
                JLabel lbl = new JLabel("No hay juegos registrados aún.");
                lbl.setFont(lbl.getFont().deriveFont(16f));
                content.add(lbl);
            } else {
                for (JPanel gamePanel : gamePanels) {
                    JPanel wrapper = new JPanel(new BorderLayout());
                    wrapper.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    wrapper.setMaximumSize(new Dimension(1020, 180));
                    wrapper.setBackground(Color.WHITE);

                    wrapper.add(gamePanel, BorderLayout.CENTER);

                    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    btnPanel.setOpaque(false);
                    JButton btnDownload = new JButton("Descargar");
                    btnPanel.add(btnDownload);

                    wrapper.add(btnPanel, BorderLayout.SOUTH);

                    content.add(wrapper);
                    content.add(Box.createVerticalStrut(10));
                }
            }
        } catch (IOException e) {
            JLabel lblError = new JLabel("Error al cargar el catálogo: " + e.getMessage());
            lblError.setForeground(Color.RED);
            content.add(lblError);
        }

        content.revalidate();
        content.repaint();
    }
}
