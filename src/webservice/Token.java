/*
 * Token.java
 *
 * Created on 26 de Setembro de 2007, 18:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webservice;
import javax.swing.JOptionPane;
import webservice.Conecta;
import petrinet10.core.PetriNet;
import petrinet10.gui.controle.ExecutaRede; //para verificar chamada de metodo
import petrinet10.gui.controle.Controle;

/**
 *
 * @author Mazza
 */
public class Token {
    
    public static Token token;
    
    private boolean primeiraVez = true;
       
    private char id;
    private int tempoFuturo;
    private int status = -1; //-1 implica na ausencia de comunicação e também em "início"
    private int instrucao;
    private int erro;
    
    private int relogioLocal;
    private int tempoLocalAnterior=0;
    private int tempoProximo; //estimativa de atualizacao de tempo
    private int deadlock; //status deadlock
        
    /** Contem as informacoes do Token */
    public void destrinchaToken(String mensagem) {//mensagem vem completa "token ..."
        System.out.println(" ");
        System.out.println("Estacao: "+Conecta.getServidor().stationName+" recebe '"+mensagem+"'");
        Conecta.getServidor().gravacaoResulto("Estacao: "+Conecta.getServidor().stationName+" recebe '"+mensagem+"'");
               
        String token = mensagem.substring(mensagem.indexOf(" "));
        token.trim();

        String erro = token.substring(token.lastIndexOf(" "), token.length());
        erro = erro.trim();

        token = token.substring(0, token.lastIndexOf(" "));
        token.trim();

        String ins = token.substring(token.lastIndexOf(" "), token.length());
        ins = ins.trim();

        token = token.substring(0, token.lastIndexOf(" "));
        token.trim();

        String status = token.substring(token.lastIndexOf(" "), token.length());
        status = status.trim();

        token = token.substring(0, token.lastIndexOf(" "));
        token.trim();

        String tempoFuturo = token.substring(token.lastIndexOf(" "), token.length());
        tempoFuturo = tempoFuturo.trim();

        token = token.substring(0, token.lastIndexOf(" "));
        token.trim();

        String id = token.substring(token.lastIndexOf(" "), token.length());
        id = id.trim();

        token = token.substring(0, token.lastIndexOf(" "));
        token.trim();
                
        // hora de settar todo mundo. parece estupido, mas deixar assim é melhor...
        setId(id.charAt(0));
        setTempoFuturo(Integer.parseInt(tempoFuturo));
        setStatus(Integer.parseInt(status));
        setInstrucao(Integer.parseInt(ins));
        setErro(Integer.parseInt(erro));
        
//        System.out.println("id:"+id);
//        System.out.println("tempoFuturo:"+tempoFuturo);
//        System.out.println("status:"+status);
//        System.out.println("ins:"+ins);
//        System.out.println("erro:"+erro);
    }
        //"chamada A B t1 true/false"
        public void destrinchaChamada(String mensagem) {//mensagem vem completa "token ..."
        
        System.out.println("Estacao: "+Conecta.getServidor().stationName+" recebe '"+mensagem+"'");
        Conecta.getServidor().gravacaoResulto("Estacao: "+Conecta.getServidor().stationName+" recebe '"+mensagem+"'");
            
        String chamada = mensagem.substring(mensagem.indexOf(" "));
        chamada.trim();
        //chamada = "A B t1 true/false"
        
        //char remetente = chamada.charAt(0); //remetente = A
        
        chamada = chamada.substring(chamada.indexOf(" "));
        chamada.trim();
        
        
        //char destinatario = chamada.charAt(0); //destinatario=B
        
        
        chamada = chamada.substring(chamada.indexOf(" "));
        chamada.trim();
        //chamada = t1 true/false;
        
        String transicao = chamada.substring(0, chamada.indexOf(" "));
        //transicao = t1;
        
        chamada.trim();
        chamada = chamada.substring(chamada.lastIndexOf(" ")+1);
        
        //chamada = "true/false";
        char remetente = mensagem.charAt(8);
        char destinatario = mensagem.charAt(10);
        //String diparo = 
        transicao = mensagem.substring(12, mensagem.lastIndexOf(" "));
        
        //Agora ele destrincha corretamente....
        //System.out.println("Destrincha Chamada: "+chamada);
        
        boolean dispara = Boolean.valueOf(chamada);
        
//        System.out.println("Dest.: "+destinatario);
//        System.out.println("rem.: "+remetente);
//        System.out.println("Trans: "+transicao);
//        System.out.println("Disp.: "+dispara);
        
        
               
        //boolean ativa = PetriNet.verificaChamadaDeMetodo(transicao, dispara);
        //boolean ativa = ExecutaRede.getRede().verificaChamadaDeMetodo(transicao, dispara);
        boolean ativa = ExecutaRede.rede.verificaChamadaDeMetodo(transicao,dispara);
        Controle.getControle().refresh_chamada();
        
        //Rodar uma espécie de "atualização" das marcas...
        //if(ativa) ExecutaRede.refresh_chamada();
        
        
        String enviar = "resposta "+destinatario+" "+remetente+" "+ativa;
        
        System.out.println("Estacao: "+Conecta.getServidor().stationName+" envia '"+enviar+"'");
        Conecta.getServidor().gravacaoResulto("Estacao: "+Conecta.getServidor().stationName+" envia '"+enviar+"'");
        
        Conecta.getServidor().envia(enviar);
        //agora vc envia a mensagem montada...
        //FFIIMM.
        //Precisa testar essas coisas de verificaChamadaDeMetodo e o envio..
    }
        
    public void isChamada(String mensagem){
        //preciso saber se destinatario == thisStation.
        char thisStation = Conecta.getServidor().stationName;
                
        //mensagem.charAt(10)=destinatario
        
        if (mensagem.charAt(10)==thisStation){
            //destinatario é esta estacao
            destrinchaChamada(mensagem);
        } else {
            //reenvia
            Conecta.getServidor().envia(mensagem);
        }                
    }
    
    //Setting
    public static Token getToken(){
        if(token == null)token = new Token();
    return token;
  }
    
    public void setId(char aidee){
        this.id = aidee;
    }
    public void setTempoFuturo(int tempo){
        this.tempoFuturo = tempo;
    }
    public void setStatus(int stat){
        this.status = stat;
    }
    public void setInstrucao (int ins){
        this.instrucao = ins;
    }
    public void setErro (int error){
        this.erro = error;
    }
    public void setRelogioLocal(int relogio){
        //this.tempoLocalAnterior = this.relogioLocal;
        this.relogioLocal = relogio;
    }
    public void setTempoProximo(int tempo){
        this.tempoProximo = tempo;
    }
    public void setDeadlock(int deadlock){
        this.deadlock = deadlock;
        //System.out.println("Dealock da estacao "+Conecta.getServidor().stationName+" ="+this.deadlock);
    }
    //getting
        
    public int getId(){
        return this.id;
    }
    public int getTempoFuturo(){
        return this.tempoFuturo;
    }
    public int getStatus(){
        return this.status;
    }
    public int getInstrucao (){
        return this.instrucao;
    }
    public int getErro (){
        return this.erro;
    }
    public int getRelogioLocal(){
        return this.relogioLocal;
    }
    public int getTempoAnterior(){
        return this.tempoLocalAnterior;
    }
    public int getTempoProximo(){
        return this.tempoProximo;
    }
    public int getDeadlock(){
        return this.deadlock;
    }
    //"token id tempofuturo status ins erro"
    public void enviaToken() {
        String mensagemToken;
        mensagemToken = "token "+id+" "+tempoFuturo+" "+status+" "+instrucao+" "+erro;
        Conecta.getServidor().envia(mensagemToken);
        //System.out.println("Estou enviando...:"+mensagemToken);
        System.out.println("Estacao: "+Conecta.getServidor().stationName+" envia '"+mensagemToken+"'");
        Conecta.getServidor().gravacaoResulto("Estacao: "+Conecta.getServidor().stationName+" envia '"+mensagemToken+"'");
        System.out.println(" ");
        if(this.instrucao==0) {
            Conecta.getServidor().desligaConexoes();
            JOptionPane.showMessageDialog(null, "A Simulação Acabou", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    //a ideia será controlar o fluxo... ou quase todo ele.
    //if não for token, nem saia daqui...(recursao)
    public void ControleToken (){
                
        String mensagem_recebida = "nichts";
        //System.out.println("Flag 1");
        //System.out.println(Conecta.getServidor().stationName);
        //System.out.println(this.status);
        if (Conecta.getServidor().stationName=='A' && this.status==-1/*&& this.primeiraVez==true*/){
            this.primeiraVez = false;
            mensagem_recebida ="token 0 0 0 1 0";
            //System.out.println("Flag 2a");
                                    
        } else {
            mensagem_recebida = Conecta.getServidor().recebe();
            //System.out.println("Flag 2b-else");
        }
        
        if (mensagem_recebida.indexOf("token")>=0){  //se naum tiver dá -1
            destrinchaToken(mensagem_recebida);
            //System.out.println("Flag 3");
           
        }
        else if(mensagem_recebida.indexOf("chamada")>=0){
                //isChamada vê o destinatário e toma decisao (reenvia ou destrincha)
                isChamada(mensagem_recebida);
                //se eu usar recursão será que funciona????
                ControleToken();
            }
        else if(mensagem_recebida.indexOf("resposta")>=0){
                Conecta.getServidor().envia(mensagem_recebida);
                //- Reenvia Resposta
                //o recebimento de resposta é dado na PetriNet.java qdo solicitado.
                ControleToken();
        }
        
    }
    
    
    public void TrataToken_Recebe(){
        char stationName = Conecta.getServidor().stationName;
               
        if(this.instrucao!=0){ // 0=parar;
            if(this.erro==1){
                //Verificacao de Erros
                //Se foi detectado algum erro, como deadlock de todos modelos, a simulação deve
                //ser interrompida!!!
                this.instrucao=0;//faco parar
                //JOptionPane.showMessageDialog(null, "A Simulação Acabou", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else if(this.id=='0'){
                //Mantido para que a estrutura do fluxograma seja mantida.
            }
            else if(this.id!=stationName){//testo se eh a MINHA info (meu TOKEN)
                if(this.deadlock==0){
                    //Mantido pela mesma razao.
                } else if(this.status==2){
                    //REGRA 6
                    //O modelo local recebe uma instrução para atualizar seu tempo local de simulação. Neste caso,
                    //ele deve atualizar o seu relógio local com o tempo informado no token e alterar sua variável de
                    //deadlock para zero, a fim de disparar suas transições, caso exista alguma disparável neste
                    //novo tempo local.
                    this.setRelogioLocal(this.tempoFuturo);
                    this.setDeadlock(0);
                }
            }
            else if(this.status==3){
                //REGRA7
                //O modelo local recebe de volta o token que enviou, sem alterações, onde informava aos demais
                //modelos que se encontrava sem mais transições disparáveis. Neste caso, ele deve atualizar o
                //campo de erro do token, informando que o sistema como um todo está travado e a simulação deve
                //ser encerrada.
                this.erro=1; //comeca a passar o token para dizer que acabou.
            }
            else if(this.status==2){
                //REGRA9
                //O modelo local recebe de volta o token que enviou, os demais modelos já atualizaram seus
                //relógios locais restando apenas ele mesmo. Assim, ele libera o token e troca o seu status
                //de “deadlock” para “0”, ou seja, o modelo não está mais em deadlock, podendo disparar suas
                //transições.
                this.id='0';
                this.status=0;
                this.setDeadlock(0);//o pessoal concordou, preciso atualizar o MEU relógio LOCAL
                this.setRelogioLocal(this.tempoFuturo);
            }
        } else { //Chegou ao FIM
            //JOptionPane.showMessageDialog(null, "A Simulação Acabou", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
        
        
    }
    public void TrataToken_Envia(){
        char stationName = Conecta.getServidor().stationName;
        
               
        if(this.instrucao!=0){ // 0=parar;
            if(this.erro==1){
                //Verificacao de Erros
                //Se foi detectado algum erro, como deadlock de todos modelos, a simulação deve
                //ser interrompida!!!
                this.instrucao=0;//faco parar
            } else if(this.id=='0'){
                if(this.deadlock==2){
                    //REGRA 1
                    //O modelo local está em deadlock pois naum possui mais transicões disparáveis
                    //(instantaneas ou temporizadas) na sua lista de disparos, neste caso, ele deve avisar
                    // os demais modelos que ele está em deadlock.
                    this.id=stationName;
                    this.status=3; //i.e., estou no deadlock 2.
                }
                else if(this.deadlock==1){
                    //REGRA 2
                    //O modelo local está em deadlock, pois não possui mais transições instantaneas disparaveis
                    //Neste caso, ele deve consultar o status dos demais modelos, afim de evoluir o tempo de simulacao
                    //do sistema.
                    this.id=stationName;
                    this.status=1;
                    this.tempoFuturo=this.tempoProximo;
                }
            }
            else if(this.id!=stationName){//testo se eh a MINHA info (meu TOKEN)
                if(this.deadlock==0){
                    //REGRA 3
                    //O modelo local não está em deadlock e recebe o token onde um outro modelo está tentando fazer 
                    //uma consulta ou avisar que está em deadlock. Neste caso, ele deve reiniciar os campos do token
                    //e informar qual o seu tempo local de simulação pois este é o menor dentre os modelos.
                    this.id='0';
                    this.status=0;
                    this.tempoFuturo=this.relogioLocal;
                } else if(this.deadlock==1 && this.status==1 && 
                        (this.tempoFuturo>this.tempoProximo) ){
                    //REGRA 4
                    //O modelo local está em deadlock e recebe o token onde um outro modelo, com tempo de simulação 
                    //superior ao local, está fazendo uma consulta. Neste caso, ele deve sobrescrever os campos de 
                    //identificação e tempo futuro com os seus próprios valores, passando a fazer a consulta 
                    //aos demais.
                    this.id=stationName;
                    this.tempoFuturo=this.tempoProximo;
                    if(this.tempoLocalAnterior==this.relogioLocal){
                        this.tempoLocalAnterior=this.relogioLocal;
                    }
                } else if(this.deadlock==1 && this.status==3){
                    //REGRA 5
                    //O modelo local está em deadlock e recebe o token onde um outro modelo informa que está sem 
                    //transições disparáveis. Neste caso, ele deve sobrescrever os campos de identificação e tempo
                    //futuro e trocar o status do token para consulta, informando que este modelo não está sem 
                    //transições na sua lista de disparos.
                    this.id=stationName;
                    this.status=1;
                    this.tempoFuturo= this.tempoProximo;
                    if(this.tempoLocalAnterior==this.relogioLocal){
                        this.tempoLocalAnterior=this.relogioLocal;
                    }
                }
            }
            else if(this.status==1){
                //REGRA8
                //O modelo local recebe de volta o token que enviou, sem alterações, onde fazia uma consulta
                //aos demais modelos de forma a tentar atualizar o seu tempo local de simulação e, 
                //conseqüentemente, o do sistema. Neste caso, ele altera o status do token tal que informe as
                //demais estações para atualizarem seus relógios locais de simulação com o valor utilizado na
                //consulta.
                this.status=2;
            }
        }
        
        
    }
}