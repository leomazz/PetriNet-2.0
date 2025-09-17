/*
 * Tarefa.java
 *
 * Created on 27 de Novembro de 2006, 02:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package remoto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TimerTask;
import javax.swing.JOptionPane;

/**
 *
 * @author _Renatu
 */
public class Tarefa extends TimerTask {
    
    Socket soquete;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    ArrayList variaveis;
    JanelaRemoto jr;
    boolean enviar;
    
    /** Creates a new instance of Tarefa */
    public Tarefa(JanelaRemoto jr) {
        this.jr = jr;
    }
    
    public void enviar() {
        this.enviar = true;
    }
    
    public void run() {
        try {
            soquete = new Socket(jr.ip.getText(), 9999);
            oos = new ObjectOutputStream(soquete.getOutputStream());
            ois = new ObjectInputStream(soquete.getInputStream());
            if (enviar) {
                oos.writeObject(jr.getVariaveis());
                enviar = false;
            }
            oos.writeObject(new String("STATUS"));
            Object objeto = ois.readObject();
            if (objeto instanceof ArrayList) {
                variaveis = (ArrayList)objeto;
                jr.montarjanela(variaveis);
            }
            soquete.close();
        } catch (IOException ioe) {
        } catch (ClassNotFoundException cnfe) {
        }
    }
    
}
