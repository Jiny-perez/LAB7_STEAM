/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steam.Frames;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author esteb
 */
public class VerReportes {
    public static JPanel createPanel() {
        JPanel viewPanel = new JPanel(new BorderLayout());
        viewPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Historial de Reportes Generados", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        viewPanel.add(lblTitle, BorderLayout.NORTH);

        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 14)); 
        
        ta.setText(
            String.format("%-15s | %-10s | %s\n", "FECHA", "TIPO", "DESCRIPCIÓN/DETALLE") +
            "------------------------------------------------------------------------------------------------------\n" +
            String.format("%-15s | %-10s | %s\n", "2025-10-22", "ERROR", "Fallo de conexión al servidor de autenticación.") +
            String.format("%-15s | %-10s | %s\n", "2025-10-23", "COMPRA", "Cliente 105 compró juego ID 005 (Minecraft).") +
            String.format("%-15s | %-10s | %s\n", "2025-10-23", "ERROR", "Archivo de precios corrupto. Requiere revisión.") +
            "\n[...Aquí se cargaría el contenido del archivo de reportes...]"
        );
        
        viewPanel.add(new JScrollPane(ta), BorderLayout.CENTER);
        
        return viewPanel;
    }
}
