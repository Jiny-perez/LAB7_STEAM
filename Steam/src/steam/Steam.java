package steam;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
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
            fl = new File("steam/downloads");
            fl.mkdirs();

            rcodes = new RandomAccessFile("steam/codes.stm", "rw");
            rgames = new RandomAccessFile("steam/games.stm", "rw");
            rplayers = new RandomAccessFile("steam/rplayers", "rw");

            initCodes();
        } catch (IOException e) {

        }
    }

    private void initCodes() throws IOException {
        if (rcodes.length() == 0) {
            rcodes.seek(0);
            rcodes.writeInt(1);
            rcodes.writeInt(1);
            rcodes.writeInt(1);
        }
    }

    private int getCode(String tipo) throws IOException {
        long pos;
        switch (tipo.toLowerCase()) {
            case "game":
                pos = 0;
                break;
            case "player":
                pos = 4;
                break;
            case "download":
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

    public void addGame(String titulo, char so, int edadMinima, double precio, String rutaImg) throws IOException {
        int code = getCode("game");

        rgames.seek(rgames.length());
        rgames.writeInt(code);
        rgames.writeUTF(titulo);
        rgames.writeChar(so);
        rgames.writeInt(edadMinima);
        rgames.writeDouble(precio);
        rgames.writeInt(0);
        rgames.writeUTF(rutaImg);
    }

    public void addPlayer(String username, String password, String nombre, long nacimiento, String rutaImg, String tipo) throws IOException {
        int code = getCode("player");

        rplayers.seek(rplayers.length());
        rplayers.writeInt(code);
        rplayers.writeUTF(username);
        rplayers.writeUTF(password);
        rplayers.writeUTF(nombre);
        rplayers.writeLong(nacimiento);
        rplayers.writeInt(0);
        rplayers.writeUTF(rutaImg);
        rplayers.writeUTF(tipo);
    }

    public boolean downdloadGame(int codeGame, int codeCliente, char so) throws IOException {
        boolean existsGame = existsGame(codeGame);
        boolean existsClient = existsClient(codeCliente);

        if (!existsClient || !existsGame) {
            return false;
        }

        rgames.seek(0);
        String titulo = null;
        char sistemaOperativo = ' ';
        int EdadMin = 0;
        double Precio = 0.0;
        int gcontDownloads = 0;
        String grutaImagen = null;
        long posGameCont = -1;
        boolean foundGame = false;

        while (rgames.getFilePointer() < rgames.length()) {
            int codigo = rgames.readInt();

            if (codigo == codeGame) {
                titulo = rgames.readUTF();
                sistemaOperativo = rgames.readChar();
                EdadMin = rgames.readInt();
                Precio = rgames.readDouble();
                posGameCont = rgames.getFilePointer();
                gcontDownloads = rgames.readInt();
                grutaImagen = rgames.readUTF();
                foundGame = true;
                break;
            } else {
                rgames.readUTF();
                rgames.readChar();
                rgames.readInt();
                rgames.readDouble();
                rgames.readInt();
                rgames.readUTF();
            }
        }

        if (!foundGame) {
            return false;
        }

        rplayers.seek(0);
        String username = null;
        String password = null;
        String nombreCliente = null;
        long nacimiento = 0L;
        int ccontDownloads = 0;
        String crutaImagen = null;
        String tipoUsuario = null;
        long posClienteCont = -1;
        boolean foundClient = false;

        while (rplayers.getFilePointer() < rplayers.length()) {
            int readCode = rplayers.readInt();
            if (readCode == codeCliente) {
                username = rplayers.readUTF();
                password = rplayers.readUTF();
                nombreCliente = rplayers.readUTF();
                nacimiento = rplayers.readLong();
                posClienteCont = rplayers.getFilePointer();
                ccontDownloads = rplayers.readInt();
                crutaImagen = rplayers.readUTF();
                tipoUsuario = rplayers.readUTF();
                foundClient = true;
                break;
            } else {
                rplayers.readUTF();
                rplayers.readUTF();
                rplayers.readUTF();
                rplayers.readLong();

                try {
                    rplayers.readInt();
                    rplayers.readUTF();
                    rplayers.readUTF();
                } catch (IOException ex) {
                }
            }
        }

        if (!foundClient) {
            return false;
        }

        char requestedSO = Character.toUpperCase(so);
        char providedSO = Character.toUpperCase(sistemaOperativo);
        if (requestedSO != providedSO) {
            return false;
        }

        Calendar fecha = Calendar.getInstance();
        int yearActual = fecha.get(Calendar.YEAR);
        fecha.setTimeInMillis(nacimiento);
        int yearNacimiento = fecha.get(Calendar.YEAR);
        int anio = yearActual - yearNacimiento;

        Calendar fechaNacimiento = Calendar.getInstance();
        fechaNacimiento.setTimeInMillis(nacimiento);
        Calendar hoy = Calendar.getInstance();
        if (hoy.get(Calendar.MONTH) < fechaNacimiento.get(Calendar.MONTH) || (hoy.get(Calendar.MONTH) == fechaNacimiento.get(Calendar.MONTH)
                && hoy.get(Calendar.DAY_OF_MONTH) < fechaNacimiento.get(Calendar.DAY_OF_MONTH))) {
            anio--;
        }

        if (anio < EdadMin) {
            return false;
        }

        int downloadCode = getCode("download");
        String downloadFileName = "steam/downloads/download_" + downloadCode + ".stm";
        PrintWriter pw = new PrintWriter(new FileWriter(downloadFileName));
        pw.println(new Date());
        pw.println(grutaImagen);
        pw.println("Download #" + downloadCode);
        pw.println(nombreCliente + " ha bajado " + titulo);
        pw.println("a un precio de $ " + Precio + ".");
        pw.close();

        if (posGameCont >= 0) {
            rgames.seek(posGameCont);
            rgames.writeInt(ccontDownloads + 1);
        }

        if (posClienteCont >= 0) {
            rplayers.seek(posClienteCont);
            rplayers.writeInt(ccontDownloads + 1);
        }

        return true;
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

            rgames.readInt();
            String title = rgames.readUTF();
            char os = rgames.readChar();
            int edadMin = rgames.readInt();
            double precio = rgames.readDouble();
            rgames.readInt();
            String imagen = rgames.readUTF();

            JPanel p = new JPanel();
            p.setLayout(new BorderLayout(10, 10));

            ImageIcon img = new ImageIcon(imagen);
            JLabel lblImg = new JLabel(img);

            JTextArea info = new JTextArea(
                    "Titulo: " + title + "\n"
                    + "Sistema Operativo: " + os + "\n"
                    + "Edad minima: " + edadMin + "\n"
                    + "Precio: " + precio
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

    public boolean modificarGame(int codeBuscado, String newTitle, char newOs,
            int newEdadMin, double newPrecio,
            int newContDescargas, String newImg)
            throws IOException {

        RandomAccessFile temp = new RandomAccessFile("steam/games_tmp.stm", "rw");
        temp.setLength(0);
        temp.seek(0);

        rgames.seek(0);
        boolean seEncontro = false;

        while (rgames.getFilePointer() < rgames.length()) {

            int code = rgames.readInt();
            String title = rgames.readUTF();
            char os = rgames.readChar();
            int edad = rgames.readInt();
            double precio = rgames.readDouble();
            int cont = rgames.readInt();
            String img = rgames.readUTF();

            if (code == codeBuscado) {
                temp.writeInt(code);
                temp.writeUTF(newTitle);
                temp.writeChar(newOs);
                temp.writeInt(newEdadMin);
                temp.writeDouble(newPrecio);
                temp.writeInt(newContDescargas);
                temp.writeUTF(newImg);
                seEncontro = true;
            } else {
                temp.writeInt(code);
                temp.writeUTF(title);
                temp.writeChar(os);
                temp.writeInt(edad);
                temp.writeDouble(precio);
                temp.writeInt(cont);
                temp.writeUTF(img);
            }
        }

        temp.close();

        if (!seEncontro) {
            new File("steam/games_tmp.stm").delete();
            return false;
        }

        rgames.close();
        File original = new File("steam/games.stm");
        File tmp = new File("steam/games_tmp.stm");

        original.delete();
        tmp.renameTo(original);

        rgames = new RandomAccessFile("steam/games.stm", "rw");

        return true;
    }

    public boolean modificarPlayer(int codeBuscado, String newUsername, String newPassword,
            String newName, long newNacimiento, int newContador,
            String newImagePath, String newTipoUsuario) throws IOException {

        RandomAccessFile temp = new RandomAccessFile("steam/player_tmp.stm", "rw");
        temp.setLength(0);
        temp.seek(0);

        rplayers.seek(0);
        boolean seEncontro = false;

        while (rplayers.getFilePointer() < rplayers.length()) {

            int code = rplayers.readInt();
            String username = rplayers.readUTF();
            String password = rplayers.readUTF();
            String name = rplayers.readUTF();
            long nacimiento = rplayers.readLong();
            int cont = rplayers.readInt();
            String imagen = rplayers.readUTF();
            String tipo = rplayers.readUTF();

            if (code == codeBuscado) {
                temp.writeInt(code);
                temp.writeUTF(newUsername);
                temp.writeUTF(newPassword);
                temp.writeUTF(newName);
                temp.writeLong(newNacimiento);
                temp.writeInt(newContador);
                temp.writeUTF(newImagePath);
                temp.writeUTF(newTipoUsuario);
                seEncontro = true;
            } else {
                temp.writeInt(code);
                temp.writeUTF(username);
                temp.writeUTF(password);
                temp.writeUTF(name);
                temp.writeLong(nacimiento);
                temp.writeInt(cont);
                temp.writeUTF(imagen);
                temp.writeUTF(tipo);
            }
        }

        temp.close();

        if (!seEncontro) {
            new File("steam/player_tmp.stm").delete();
            throw new NoSuchElementException("El jugador con codigo " + codeBuscado + " no existe.");
        }

        rplayers.close();
        File original = new File("steam/player.stm");
        File tmp = new File("steam/player_tmp.stm");

        original.delete();
        tmp.renameTo(original);

        rplayers = new RandomAccessFile("steam/player.stm", "rw");

        return true;
    }

    public boolean deleteGame(int codeBuscado) throws IOException {
        RandomAccessFile temp = new RandomAccessFile("steam/games_tmp.stm", "rw");
        temp.setLength(0);
        temp.seek(0);

        rgames.seek(0);
        boolean found = false;

        while (rgames.getFilePointer() < rgames.length()) {
            int code = rgames.readInt();
            String title = rgames.readUTF();
            char os = rgames.readChar();
            int edad = rgames.readInt();
            double precio = rgames.readDouble();
            int cont = rgames.readInt();
            String img = rgames.readUTF();

            if (code == codeBuscado) {
                found = true;
            } else {
                temp.writeInt(code);
                temp.writeUTF(title);
                temp.writeChar(os);
                temp.writeInt(edad);
                temp.writeDouble(precio);
                temp.writeInt(cont);
                temp.writeUTF(img);
            }
        }

        temp.close();

        if (!found) {
            new File("steam/games_tmp.stm").delete();
            return false;
        }

        rgames.close();
        File original = new File("steam/games.stm");
        File tmp = new File("steam/games_tmp.stm");

        original.delete();
        tmp.renameTo(original);

        rgames = new RandomAccessFile("steam/games.stm", "rw");

        return true;
    }

    public boolean deletePlayer(int codeBuscado) throws IOException {
        RandomAccessFile temp = new RandomAccessFile("steam/player_tmp.stm", "rw");
        temp.setLength(0);
        temp.seek(0);

        rplayers.seek(0);
        boolean found = false;

        while (rplayers.getFilePointer() < rplayers.length()) {
            int code = rplayers.readInt();
            String username = rplayers.readUTF();
            String password = rplayers.readUTF();
            String name = rplayers.readUTF();
            long nacimiento = rplayers.readLong();
            int cont = rplayers.readInt();
            String imagen = rplayers.readUTF();
            String tipo = rplayers.readUTF();

            if (code == codeBuscado) {
                found = true;
            } else {
                temp.writeInt(code);
                temp.writeUTF(username);
                temp.writeUTF(password);
                temp.writeUTF(name);
                temp.writeLong(nacimiento);
                temp.writeInt(cont);
                temp.writeUTF(imagen);
                temp.writeUTF(tipo);
            }
        }

        temp.close();

        if (!found) {
            new File("steam/player_tmp.stm").delete();
            return false;
        }

        rplayers.close();
        File original = new File("steam/player.stm");
        File tmp = new File("steam/player_tmp.stm");

        original.delete();
        tmp.renameTo(original);

        rplayers = new RandomAccessFile("steam/player.stm", "rw");

        return true;
    }

    public RandomAccessFile getRcodes() {
        return rcodes;
    }

    public RandomAccessFile getRgames() {
        return rgames;
    }

    public RandomAccessFile getRplayers() {
        return rplayers;
    }
    

}
