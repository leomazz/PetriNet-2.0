/*
 * Remoto.java
 *
 * Created on 26 de Novembro de 2006, 22:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package remoto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import petrinet10.core.Variavel;
import petrinet10.gui.controle.Controle;

/**
 *
 * @author _Renatu
 */
public class Remoto implements Runnable {
    
    private static Remoto remoto;
    private boolean stop, enviar;
    
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(9999);
            while (true) {
                Socket soquete = servidor.accept();
                tratar(soquete);
                soquete.close();
            }
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Erro de I/O: " + ioe.toString());
        }
    }
    
    public static void enviarStatus() {
        if (remoto!=null) remoto.enviar = true;
    }
    
    private void tratar(Socket soquete) {
        try {
            ObjectInputStream ois = new ObjectInputStream(soquete.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(soquete.getOutputStream());
            boolean done = false;
            Object o = ois.readObject();
            if (o instanceof String) {
                String s = (String)o;
                if (s.equals("STATUS")) {
                    oos.writeObject(Controle.getControle().getVariaveis());
                    oos.flush();
                }
            } else if (o instanceof ArrayList) {
                ArrayList lista = (ArrayList)o;
                for (int i=0; i<lista.size(); i++) {
                    Variavel entrada = (Variavel)lista.get(i);
                    Variavel local = Controle.getControle().getVariavelPeloNome(entrada.getNome());
                    local.setLigado(entrada.isLigado());
                }
                Controle.getControle().atualizarVariaveis();
            }
            if (enviar) {
                oos.writeObject(Controle.getControle().getVariaveis());
                oos.flush();
                enviar = false;
           }
        } catch (IOException ioe) {
        } catch (ClassNotFoundException cnfe) {
        }
    }
    
    public Remoto() {
        stop = false;
        enviar = true;
    }
    
    public void parar() {
        stop = true;
    }
    
    public static void iniciaServidor() {
        remoto = new Remoto();
        Thread t = new Thread(remoto);
        t.start();
    }
    
    public static void finalizaServidor() {
        remoto.parar();
    }
}
