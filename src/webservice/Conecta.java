/*
 * Conecta.java
 *
 * Created on 20 de Agosto de 2007, 19:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webservice;

import java.net.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Mazza
 */
public class Conecta {
   /**
    * Port number on server, if none is specified on the command line.
    */
   static final int DEFAULT_PORT = 1728;
    /**
    * Handshake string. Each end of the connection sends this  string to the
    * other just after the connection is opened.  This is done to confirm that
    * the program on the other side of the connection is a CLChat program.
    */
   static final String HANDSHAKE = "Token";
    /**
    * This character is prepended to every message that is sent.
    *Naming the Slave Station 'A';
    */
   static final char ThisStation = 'A';
    /**
    * This character is sent to the connected program when the user quits.
    */
    static final char CLOSE = '1';

    //VARIAVEIS DO SERVIDOR
    public int port;   // The port on which the server listens.

    ServerSocket listener;  // Listens for a connection request.
    Socket connection;      // For communication with the client.

    BufferedReader incoming;  // Stream for receiving data from client.
    PrintWriter outgoing;     // Stream for sending data to client.
    //String messageOut;        // A message to be sent to the client.
    String messageIn;         // A message received from the client.
    public char stationName;

    BufferedReader userInput; // A wrapper for System.in, for reading
                            // lines of input from the user.

    //VARIAVEIS DO CLIENTE

    public String computer;  // The computer where the server is running,
                    // as specified on the command line.  It can
                    // be either an IP number or a domain name.
                    //O SERVIDOR DE A É B, PORT=1729, B é C =1730,
                    //de C é A = 1728

  public int port2;   // The port on which the server listens.

  Socket connection2;      // For communication with the server.

  BufferedReader incoming2;  // Stream for receiving data from server.
  PrintWriter outgoing2;     // Stream for sending data to server.
  //String messageOut2;        // A message to be sent to the server.
  String messageIn2;         // A message received from the server.

  BufferedReader userInput2; // A wrapper for System.in, for reading
                              // lines of input from the user.

  char destinationName;  //Nome da Estacao DESTINO

  public static Conecta servidor;// = null;
  
  //Para Gravacao de Arquivo
  private boolean arquivo=false;
  private String nomeDoArq;
  private File f;
  
  private boolean conexao_desligada=false;
  
  
  //Cria objeto dentro da classe, de modo a ser acessado por todas as classes.
  public static Conecta getServidor(){
      if(servidor == null) servidor = new Conecta();
      return servidor;
  }
  
  
/** Inicia Estacao A */
  public void iniciaA(char outstationName, int outport, String outcomputer, int outport2) {
      /*ABRE A COMUNICACAO COM A ESTACAO B, PREVIAMENTE INICIALIZADA */
            
      /*this.*/stationName=outstationName;
      /*this.*/port = outport;
      /*this.*/computer = outcomputer;
      /*this.*/port2 = outport2;
      
    try {
        System.out.println("Estacao "+ this.stationName+" a conectar-se no " + computer + " na porta " + port2);
        this.gravacaoResulto("Estacao "+ this.stationName+" a conectar-se no " + computer + " na porta " + port2);
        connection2 = new Socket(computer,port2);
        incoming2 = new BufferedReader(
                       new InputStreamReader(connection2.getInputStream()) );
        outgoing2 = new PrintWriter(connection2.getOutputStream());
        outgoing2.println(HANDSHAKE);  // Send handshake to client.
        outgoing2.flush();
        messageIn2 = incoming2.readLine();  // Receive handshake from client.
        if (! messageIn2.equals(HANDSHAKE) ) {
            throw new IOException("Connected program is not Token!");
        }
        System.out.println("Socket Conectado.");
        this.gravacaoResulto("Socket Conectado.");
    }
    catch (Exception e2) {
        System.out.println("An error occurred while opening connection.");
        System.out.println(e2.toString());
        return;
    }

    try {
        listener = new ServerSocket(port);
        System.out.println("Estacao "+ this.stationName+" a ouvir na porta " + listener.getLocalPort());
        this.gravacaoResulto("Estacao "+ this.stationName+" a ouvir na porta " + listener.getLocalPort());
        connection = listener.accept();
        listener.close();
        incoming = new BufferedReader(
                       new InputStreamReader(connection.getInputStream()) );
        outgoing = new PrintWriter(connection.getOutputStream());
        outgoing.println(HANDSHAKE);  // Send handshake to client.
        outgoing.flush();
        messageIn = incoming.readLine();  // Receive handshake from client.
        if (! HANDSHAKE.equals(messageIn) ) {
           throw new Exception("Connected program is not a Token!");
        }
        System.out.println("ServerSocket Conectado.");
        this.gravacaoResulto("ServerSocket Conectado.");
    }
    catch (Exception e) {
        System.out.println("An error occurred while opening connection.");
        System.out.println(e.toString());
        return;
    }
    
}
  
    public void inicia(char outstationName, int outport, String outcomputer, int outport2) {
      /*ABRE A COMUNICACAO COM A ESTACAO B, PREVIAMENTE INICIALIZADA */

      
      /*this.*/stationName=outstationName;
      /*this.*/port = outport;
      /*this.*/computer = outcomputer;
      /*this.*/port2 = outport2;
        
   try {
        listener = new ServerSocket(port);
        //System.out.println("Listening on port " + listener.getLocalPort());
        System.out.println("Estacao "+ this.stationName+" a ouvir na porta " + listener.getLocalPort());
        this.gravacaoResulto("Estacao "+ this.stationName+" a ouvir na porta " + listener.getLocalPort());
        connection = listener.accept();
        listener.close();
        incoming = new BufferedReader(
                       new InputStreamReader(connection.getInputStream()) );
        outgoing = new PrintWriter(connection.getOutputStream());
        outgoing.println(HANDSHAKE);  // Send handshake to client.
        outgoing.flush();
        messageIn = incoming.readLine();  // Receive handshake from client.
        if (! HANDSHAKE.equals(messageIn) ) {
           throw new Exception("Connected program is not a Token!");
        }
        System.out.println("ServerSocket Conectado.");
        this.gravacaoResulto("ServerSocket Conectado.");
   }
    catch (Exception e) {
        System.out.println("An error occurred while opening connection.");
        System.out.println(e.toString());
        return;
    }
    try {
        //System.out.println("Connecting to " + computer + " on port " + port2);
        System.out.println("Estacao "+ this.stationName+" a conectar-se no " + computer + " na porta " + port2);
        this.gravacaoResulto("Estacao "+ this.stationName+" a conectar-se no " + computer + " na porta " + port2);
        connection2 = new Socket(computer,port2);
        incoming2 = new BufferedReader(
                       new InputStreamReader(connection2.getInputStream()) );
        outgoing2 = new PrintWriter(connection2.getOutputStream());
        outgoing2.println(HANDSHAKE);  // Send handshake to client.
        outgoing2.flush();
        messageIn2 = incoming2.readLine();  // Receive handshake from client.
        if (! messageIn2.equals(HANDSHAKE) ) {
            throw new IOException("Connected program is not Token!");
        }
        System.out.println("Socket Conectado.");
        this.gravacaoResulto("Socket Conectado.");
    }
    catch (Exception e2) {
        System.out.println("An error occurred while opening connection.");
        System.out.println(e2.toString());
        return;
    }
    
}
  
    public void envia(String messageOut2) {
        try {
            userInput2 = new BufferedReader(new InputStreamReader(System.in));
            
             outgoing2.println(messageOut2);
             outgoing2.flush();
             
             if (messageOut2.equalsIgnoreCase("quit"))  {
                 // User wants to quit.  Inform the other side
                 // of the connection, then close the connection.
                 outgoing2.println(CLOSE);
                 outgoing2.flush();
                 connection2.close();
                 System.out.println("Connection closed.");
             }
         
//ENVIA A MENSAGEM PROPRIAMENTE DITA
         
         }
      
      catch (Exception e) {
         System.out.println("Sorry, an error has occurred.  Connection lost.");
         System.out.println(e.toString());
         System.exit(1);
      }
  } 
    public String recebe(){
    
        try {
            userInput2 = new BufferedReader(new InputStreamReader(System.in));
            //System.out.println("NOTE: Enter 'quit' to end the program.\n");

             //esse while é fundamental para o andamento do programa!!!
             //AQUI DEVERA ESPERAR ESTACAO C, I.E., CRIAR NOVA COMUNICACAO COM OUTRA PORTA

            messageIn = incoming.readLine();
            if (messageIn.length() > 0) {
                    // The first character of the message is a command. If
                    // the command is CLOSE, then the connection is closed.
                    // Otherwise, remove the command character from the
                    // message and proceed.
                //System.out.println("message is: "+messageIn);
                if(messageIn.equalsIgnoreCase("quit")){
                    if(stationName!='A') envia("quit");
                    connection.close();
                    System.out.println("Connection close at other end.");
                }
                if (messageIn.charAt(0) == CLOSE) {
                    System.out.println("Connection closed at other end.");
                    connection.close();
               }
            }
         }
      
      catch (Exception e) {
         System.out.println("Sorry, an error has occurred.  Connection lost.");
         System.out.println(e.toString());
         messageIn = "erro";
         System.exit(1);
      }        
    return messageIn;
    }
    // PetriNet.verificaChamadaDeMetodo(transicao) deve ser chamada...
    public boolean enviaRecebeChamada(String transicao, char station, boolean dispara){
        boolean ativa;
        String pergunta, resposta;
        
        //monta a mensagem padrão
        pergunta = "chamada "+stationName+" "+station+" "+transicao+" "+dispara; //estacao, codigo, ti etc.
        //System.out.println("station: "+station);
        //System.out.println(pergunta);
        envia(pergunta);
        
        System.out.println("Estacao: "+Conecta.getServidor().stationName+" envia '"+pergunta+"'");
        Conecta.getServidor().gravacaoResulto("Estacao: "+Conecta.getServidor().stationName+" envia '"+pergunta+"'");
        
        
        resposta = recebe(); //a resposta= "resposta remetente destinatario ativa/inativa"
        
        System.out.println("Estacao: "+Conecta.getServidor().stationName+" recebe '"+resposta+"'");
        Conecta.getServidor().gravacaoResulto("Estacao: "+Conecta.getServidor().stationName+" recebe '"+resposta+"'");
        
        //destrinchando a mensagem
        resposta = resposta.trim(); //cleanin'up
        //resposta="resposta A B sim";

        //resposta
	String resp_chamada = resposta.substring(0, resposta.indexOf(" ")).trim();
	
        //ps: ao usar "trim()", eu retiro os espaços no comeco e no fim da String...
        
        //para verificacao
        //if (resp_chamada.equals("resposta")) System.out.println("OK recebimento resposta");
        
        // remetente destinatario ativa/inativa
        // A B sim
	String info = resposta.substring(resposta.indexOf(" "));//pegando a posicao do " "
        
	info = info.trim();
	int i = info.indexOf(" ");
	
        //A
        String remet = info.substring(0, i);

        //destinatario ativa/inativa
	info = info.substring(i);
	info = info.trim();
	i = info.indexOf(" ");
	
        //destinatario
        //B
        String dest = info.substring(0,i);

        info = info.trim();
	i = info.indexOf(" ");
	
        //ativa/inativa
	//true / false
        String ativa_inativa = info.substring(i).trim();

	//System.out.println("resposta:"+resposta);
	//System.out.println("remet:"+remet);
	//System.out.println("dest:"+dest);
	//System.out.println("ativa ? "+ativa_inativa.equals("sim"));       
        
        if (ativa_inativa.equals("true")) ativa=true;
        else ativa=false;
                
        //ativa=true;
        return ativa;
    }
    
    public void desligaConexoes(){
        
        if(this.conexao_desligada==false) {
            //vou tentar alterar isso aqui para funcionar de uma maneira um tanto melhor...
            //long startTime = System.currentTimeMillis(); // time at start in millisecs
            this.conexao_desligada=true;
            //Vou fazer  5 segundos de espera na interface...
            try {
                Thread.sleep(5000);
            } catch (InterruptedException exc) {
            }
            //while(System.currentTimeMillis()-startTime <= 5000);
            
            try {
                //kill socket connections
                connection2.close();
            } catch (UnknownHostException ex) {
              System.err.println(ex);
              System.out.println("UHE");
            } catch (SocketException ex) {
              System.err.println(ex);
              System.out.println("SE");
            } catch (IOException ex) {
              System.err.println(ex);
              System.out.println("IOE");
            }
            System.out.println("Socket Closed.");
            this.gravacaoResulto("Socket Closed.");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException exc) {
            }
            //while(System.currentTimeMillis()-startTime <=5000);
            try {
                //kill serverSocket connections.
                connection.close();
            } catch (UnknownHostException ex) {
              System.err.println(ex);
              System.out.println("UHE2");
            } catch (SocketException ex) {
              System.err.println(ex);
              System.out.println("SE2");
            } catch (IOException ex) {
              System.err.println(ex);
              System.out.println("IOE2");
            }
            System.out.println("ServerSocket Closed.");
            this.gravacaoResulto("ServerSocket Closed.");
        } else {
        }
    }

    public void teste(){
        servidor.envia("line1");
        System.out.println(servidor.recebe());
        servidor.envia("line2");
        System.out.println(servidor.recebe());
        servidor.envia("line3");
        System.out.println(servidor.recebe());
        servidor.envia("line4");
        System.out.println(servidor.recebe());
        //servidor.envia("last");
        
    }
    
    public void gravacaoResulto(String linha){
        
        if(this.arquivo==false) {
            this.arquivo=true;
            this.nomeDoArq = "Resultado_"+this.stationName+".txt";
            this.f= new File(nomeDoArq);
        }
                
        try {
            FileWriter fw = new FileWriter(this.f, true);
            BufferedWriter escrita = new BufferedWriter(fw);
            escrita.write(linha);
            escrita.newLine();
            escrita.flush();
            escrita.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("\n Arquivo Invalido");
        }
        
    }
}
