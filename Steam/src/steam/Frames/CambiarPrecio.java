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
public class CambiarPrecio {

    public static JPanel createPanel(Steam steam) {
        final JTextField txtCodigo = new JTextField();
        final JTextField txtPrecio = new JTextField();
        
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(15, 15, 15, 15);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        gdc.gridx = 0; 
        gdc.gridy = 0;
        gdc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("ACTUALIZAR COSTO DE JUEGO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        p.add(lblTitulo, gdc);

        gdc.gridwidth = 1; 

        gdc.gridx = 0; gdc.gridy = 1; gdc.weightx = 0.0;
        JLabel lblCod = new JLabel("Código del juego:");
        lblCod.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        p.add(lblCod, gdc);

        gdc.gridx = 1; gdc.weightx = 1.0;
        txtCodigo.setPreferredSize(new Dimension(200, 30));
        p.add(txtCodigo, gdc);

        gdc.gridx = 0; gdc.gridy = 2; gdc.weightx = 0.0;
        JLabel lblPrecio = new JLabel("Nuevo Precio ($):");
        lblPrecio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        p.add(lblPrecio, gdc);

        gdc.gridx = 1; gdc.weightx = 1.0;
        txtPrecio.setPreferredSize(new Dimension(200, 30));
        p.add(txtPrecio, gdc);

        gdc.gridx = 0; gdc.gridy = 3; gdc.gridwidth = 2; gdc.weightx = 0.0;
        
        JButton btnChange = new JButton("Aplicar Precio");
        btnChange.setPreferredSize(new Dimension(220, 44));
        p.add(btnChange, gdc);

        btnChange.addActionListener(e -> {
            String codigo = txtCodigo.getText().trim();
            String precio = txtPrecio.getText().trim();
            
            if (codigo.isEmpty() || precio.isEmpty()) {
                JOptionPane.showMessageDialog(
                        p,
                        "Por favor llena todos los campos.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            try {
                int codigoNum = Integer.parseInt(codigo);
                double precioNum = Double.parseDouble(precio);

                if (precioNum < 0) {
                    JOptionPane.showMessageDialog(
                            p,
                            "El precio no puede ser negativo.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                boolean ok = steam.updatePriceFor(codigoNum, precioNum);

                if (ok) {
                    JOptionPane.showMessageDialog(
                            p,
                            "Precio actualizado para el juego con código " + codigoNum
                            + " a: $" + String.format("%.2f", precioNum),
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    txtCodigo.setText("");
                    txtPrecio.setText("");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        p,
                        "Código y precio deben ser números válidos.\nEjemplo precio: 19.99",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
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
                        "Error de lectura/escritura en el archivo de juegos:\n" + ex.getMessage(),
                        "Error IO",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        
        return p;
    }
}