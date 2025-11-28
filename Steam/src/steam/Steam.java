/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package steam;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author marye
 */
public class Steam {

    RandomAccessFile rcodes;
    RandomAccessFile rgames;
    RandomAccessFile rplayers;

    public Steam() {
        try {

            File fl = new File("steam");
            fl.mkdir();
            fl = new File("steam/download");
            fl.mkdirs();

            rcodes = new RandomAccessFile("steam/codes.stm", "rw");
            rgames = new RandomAccessFile("steam/rgames.stm", "rw");
            rplayers = new RandomAccessFile("steam/rplayers", "rw");

            initCodes();
        } catch (IOException e) {

        }
    }

    private void initCodes() throws IOException {
        if (rcodes.length() == 0) {
            rcodes.writeInt(1);
            rcodes.writeInt(1);
            rcodes.writeInt(1);
        }
    }

    private int getCode(String tipo) throws IOException {
        long pos;
        switch (tipo.toLowerCase()) {
            case "games":
                pos = 0;
                break;
            case "players":
                pos = 4;
                break;
            case "downloads":
                pos = 8;
                break;
            default:
                throw new IllegalArgumentException("Tipo de codigo invalido.");
        }

        rcodes.seek(pos);
        int code = rcodes.readInt();
        rcodes.seek(pos);
        rcodes.writeInt(code + 1);
        return code;
    }

    public boolean existsGame(int code) throws IOException {
        rgames.seek(0);

        while (rgames.getFilePointer() < rgames.length()) {

            int readCode = rgames.readInt();

            long pos = rgames.getFilePointer();
            rgames.readUTF();
            rgames.skipBytes(18);
            rgames.readUTF();

            if (readCode == code) {
                rgames.seek(pos);
                return true;
            }
        }

        return false;
    }

    public boolean existsClient(int code) throws IOException {
        rplayers.seek(0);

        while (rplayers.getFilePointer() < rplayers.length()) {

            int readCode = rplayers.readInt();

            long pos = rplayers.getFilePointer();
            rplayers.readUTF();
            rplayers.readUTF();
            rplayers.readUTF();
            rplayers.skipBytes(12);
            rplayers.readUTF();
            rplayers.readUTF();

            if (readCode == code) {
                rplayers.seek(pos);
                return true;
            }
        }

        return false;
    }

    public boolean updatePriceFor(int code, double precio) throws IOException {
        boolean activo = existsGame(code);
        if (!activo) {
            throw new NoSuchElementException("El juego no existe.");
        }

        rgames.readUTF();
        rgames.skipBytes(6);
        rgames.writeDouble(precio);
        return true;
    }

    public boolean reportForClient(int code, String file) throws IOException {
        boolean activo = existsClient(code);
        if (!activo) {
            throw new NoSuchElementException("El jugador no existe.");
        }
        String username = rplayers.readUTF();
        String password = rplayers.readUTF();
        String name = rplayers.readUTF();
        long nacimiento = rplayers.readLong();
        int contadorDownloads = rplayers.readInt();
        String imagen = rplayers.readUTF();
        String tipoUsuario = rplayers.readUTF();

        PrintWriter pw = new PrintWriter(new FileWriter("steam/" + file));
        pw.println("REPORTE DE CLIENTE");
        pw.println("--------------------");
        pw.println("Codigo: " + code);
        pw.println("Username: " + username);
        pw.println("Password: " + password);
        pw.println("Nombre: " + name);
        pw.println("Nacimiento: " + new Date(nacimiento));
        pw.println("Numero de descargas: " + contadorDownloads);
        pw.println("Imagen: " + imagen);
        pw.println("Tipo de Usuario: " + tipoUsuario);
        pw.close();
        return true;
    }

    public ArrayList<JPanel> getGamePanels() throws IOException {
        ArrayList<JPanel> lista = new ArrayList<>();
        rgames.seek(0);

        while (rgames.getFilePointer() < rgames.length()) {

            int code = rgames.readInt();
            String title = rgames.readUTF();
            char os = rgames.readChar();
            int edadMin = rgames.readInt();
            rgames.readDouble();
            int cont = rgames.readInt();
            String imagen = rgames.readUTF();

            JPanel p = new JPanel();
            p.setLayout(new BorderLayout(10, 10));

            ImageIcon img = new ImageIcon(imagen);
            JLabel lblImg = new JLabel(img);

            JTextArea info = new JTextArea(
                    "Codigo: " + code + "\n"
                    + "Titulo: " + title + "\n"
                    + "Sistema Operativo: " + os + "\n"
                    + "Edad minima: " + edadMin + "\n"
                    + "Descargas: " + cont
            );

            info.setEditable(false);
            info.setOpaque(false);
            info.setLineWrap(true);
            info.setWrapStyleWord(true);

            p.add(lblImg, BorderLayout.WEST);
            p.add(info, BorderLayout.CENTER);

            lista.add(p);
        }

        return lista;
    }
}
