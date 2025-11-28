package steam.Frames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import steam.Steam;

/**
 *
 * @author esteb
 */
public class GenerarReporte {

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
        JLabel lblTitulo = new JLabel("GENERAR REPORTE DE CLIENTE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        p.add(lblTitulo, gdc);

        gdc.gridwidth = 1;

        gdc.gridx = 0;
        gdc.gridy = 1;
        gdc.weightx = 0.0;
        JLabel lblCod = new JLabel("Código del cliente:");
        lblCod.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        p.add(lblCod, gdc);

        gdc.gridx = 1;
        gdc.weightx = 1.0;
        txtCodigo.setPreferredSize(new Dimension(200, 30));
        p.add(txtCodigo, gdc);

        gdc.gridx = 0;
        gdc.gridy = 2;
        gdc.gridwidth = 2;
        gdc.weightx = 0.0;

        JButton btnGen = new JButton("Generar Reporte");
        btnGen.setPreferredSize(new Dimension(220, 44));
        p.add(btnGen, gdc);

        btnGen.addActionListener(e -> {
            String codigoStr = txtCodigo.getText().trim();

            if (codigoStr.isEmpty()) {
                JOptionPane.showMessageDialog(
                        p,
                        "Por favor ingresa el código del cliente.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int codigo;
            try {
                codigo = Integer.parseInt(codigoStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        p,
                        "El código debe ser un número entero válido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String nombreArchivo = "reporte_cliente_" + codigo + ".txt";

            try {
                boolean ok = steam.reportForClient(codigo, nombreArchivo);
                if (ok) {
                    JOptionPane.showMessageDialog(
                            p,
                            "Reporte generado exitosamente.\n\n"
                            + "Archivo: steam/" + nombreArchivo,
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    txtCodigo.setText("");
                }
            } catch (NoSuchElementException ex) {
                JOptionPane.showMessageDialog(
                        p,
                        ex.getMessage(), 
                        "No encontrado",
                        JOptionPane.WARNING_MESSAGE
                );
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        p,
                        "Error al generar el reporte:\n" + ex.getMessage(),
                        "Error IO",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        return p;
    }
}
