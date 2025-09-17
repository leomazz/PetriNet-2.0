/*
 * ThreadCliente.java
 *
 * Created on 26 de Novembro de 2006, 23:48
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
import java.util.Timer;
import javax.swing.JOptionPane;

/**
 *
 * @author _Renatu
 */
public class ThreadCliente implements Runnable {
    
    JanelaRemoto jr;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    Socket soquete;
    ArrayList variaveis;
    boolean enviar;
    Tarefa t;
    
    /** Creates a new instance of ThreadCliente */
    public ThreadCliente(JanelaRemoto jr) {
        this.jr = jr;
        t = new Tarefa(jr);
    }
    
    public void enviar() {
        t.enviar();
    }
    
    public void run() {
        try {
            soquete = new Socket(jr.ip.getText(), 9999);
            oos = new ObjectOutputStream(soquete.getOutputStream());
            ois = new ObjectInputStream(soquete.getInputStream());
            oos.writeObject(new String("STATUS"));
            Object objeto = ois.readObject();
            if (objeto instanceof ArrayList) {
                variaveis = (ArrayList)objeto;
                jr.montarjanela(variaveis);
            }
            soquete.close();
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(t, 0, 500);
        } catch (IOException ioe) {
        } catch (ClassNotFoundException cnfe) {
        }
    }
    
}
