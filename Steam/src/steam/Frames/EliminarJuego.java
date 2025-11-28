package steam.Frames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import steam.Steam;
/**
 *
 * @author esteb
 */
public class EliminarJuego {

    public static JPanel createPanel(Steam steam) {
        final JTextField txtCodigo = new JTextField();

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(30, 30, 30, 30));

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
        btnDelete.setPreferredSize(new Dimension(220, 44));
        p.add(btnDelete, gdc);

        btnDelete.addActionListener(ae -> {
            String codigo = txtCodigo.getText().trim();

            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(
                        p,
                        "Por favor escribe el código del juego.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int codigoNum;
            try {
                codigoNum = Integer.parseInt(codigo);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        p,
                        "El código debe ser un número entero válido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    p,
                    "¿Seguro que deseas eliminar el juego con código: " + codigoNum + "?\nEsta acción no se puede deshacer.",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean eliminado = steam.deleteGame(codigoNum);
                    if (eliminado) {
                        JOptionPane.showMessageDialog(
                                p,
                                "Juego eliminado exitosamente.",
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        txtCodigo.setText("");
                    } else {
                        JOptionPane.showMessageDialog(
                                p,
                                "No existe un juego con el código " + codigoNum + ".",
                                "No encontrado",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            p,
                            "Error al acceder al archivo de juegos:\n" + ex.getMessage(),
                            "Error IO",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        return p;
    }
}
