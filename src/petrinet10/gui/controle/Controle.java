/*
 * Controle.java
 *
 * Created on 20 de Abril de 2006, 00:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.controle;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import petrinet10.core.Variavel;
import petrinet10.gui.visualizacao.JanelaInterna;
import petrinet10.gui.visualizacao.JanelaPrincipal;
import petrinet10.gui.visualizacao.ModeloTabelaGerenciador;
import petrinet10.gui.visualizacao.ModeloTabelaPropriedades;
import petrinet10.gui.visualizacao.PainelIO;
import petrinet10.gui.visualizacao.PetriPanel;
import petrinet10.gui.visualizacao.elementosgraficos.ElementoGrafico;
import petrinet10.gui.visualizacao.elementosgraficos.JLugar;
import petrinet10.modelo.Persistencia;
import remoto.Remoto;
import tcc.TranslateController;
import webservice.Token;

/**
 *
 * @author _Renatu
 */
public class Controle {
    
    public static final int SETA = 0;
    public static final int RETA = 1;
    public static final int RETANGULO = 2;
    public static final int ELIPSE = 3;
    public static final int LUGAR = 4;
    public static final int TRANSICAO = 5;
    public static final int TEXTO = 6;
    
    private static JanelaPrincipal janelaprincipal;
    private static ArrayList janelasinternas;
    private static Controle controle;
    private static int funcao;
    
    private int numerolugar, numerotransicao;
    private ModeloTabelaPropriedades mtp;
    private ArrayList operadores, variaveis;
    private ModeloTabelaGerenciador modelogerenciador;
    private JTable tabelagerenciador;
    private JPanel painelIO;
    
    private Controle() {
        numerolugar=0;
        numerotransicao=0;
        operadores = new ArrayList();
        variaveis = new ArrayList();
        operadores.add("R");
        operadores.add("S");
        operadores.add("=");
        operadores.add("A");
        operadores.add("AN");
    }
    
    public Cursor getPetriPanelCursor() {
        if (funcao==Controle.SETA) {
            return new Cursor(Cursor.DEFAULT_CURSOR);
        } else if (funcao==Controle.TEXTO) {
            return new Cursor(Cursor.TEXT_CURSOR);
        } else {
            return new Cursor(Cursor.CROSSHAIR_CURSOR);
        }
    }
    
    public static Controle getControle() {
        if (controle==null) controle = new Controle();
        return controle;
    }
    
    public void inicializar() {
        janelaprincipal = new JanelaPrincipal();
        janelasinternas = new ArrayList();
        janelaprincipal.setVisible(true);        
    }
    
    public void criarNovaJanelaInterna(String titulo) {
        JanelaInterna janelainterna = new JanelaInterna(titulo);
        janelasinternas.add(janelainterna);
        janelaprincipal.getJDesktopPane().add(janelainterna);
        janelainterna.setVisible(true);
        try {
            janelainterna.setSelected(true);
        } catch (PropertyVetoException pve) { }
    }
    
    public void setFuncao(int funcao) {
        this.funcao = funcao;
    }
    
    public int getFuncao() {
        return this.funcao;
    }
    
    public void limparSelecoes() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            ji.getPetriPanel().limparSelecao();
            ji.getPetriPanel().repaint();
        }
    }
    
    public void ligar() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                ji.getPetriPanel().ligar();
            }
        }
    }
    
    //essa funcao eh chamada na hora de rodar o programa...
    
    public void rodar() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                ji.getPetriPanel().rodar();
            }
        }
    }
    
    public void parar() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                ji.getPetriPanel().parar();
            }
        }
    }
    
    public void desligar() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                ji.getPetriPanel().desligar();
            }
        }
    }

    ////////////////////// Inseri essa "auto" só para saber o que acontecia.
    public void auto() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                ji.getPetriPanel().run_auto();
            }
        }
    }    
    
    //Chama a .passo() da ExecutaRede (que MODIFICAREMOS)
    public void passo() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                ji.getPetriPanel().passo();
            }
        }
    }
    
    public String getProximoNomeTransicao() {
        return "T" + numerotransicao;
    }
    
    public void incrementaNumeroTransicao() {
        numerotransicao++;
    }
    
    public void incrementaNumeroLugar() {
        numerolugar++;
    }
    
    public void setModeloTabelaPropriedades(ModeloTabelaPropriedades mtp) {
        this.mtp = mtp;
    }
    
    public String getProximoNomeLugar() {
        return "L" + numerolugar;
    }
    
    public void atualizarPropriedades() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                PetriPanel painel = ji.getPetriPanel();
                ArrayList selecionados = painel.getSelecionados();
                if (selecionados.size()==1) {
                    mtp.setElementoGrafico((ElementoGrafico)selecionados.get(0));
                    janelaprincipal.repintarTabela();
                } else if (selecionados.size()==0) {
                    mtp.setElementoGrafico(null);
                }
            }
        }
    }
    
    public void repintarJanelaInterna() {
        janelaprincipal.repaint();
    }
    
    public void salvar() {
        JanelaInterna ji = null;
        for (int i=0; i<janelasinternas.size(); i++) {
            if (((JanelaInterna)janelasinternas.get(i)).isSelected()) {
                ji = (JanelaInterna)janelasinternas.get(i);
            }
        }
        if (ji!=null) {
            JFileChooser escolhedor = new JFileChooser();
            escolhedor.setAcceptAllFileFilterUsed(false);
            escolhedor.setFileFilter(new FileFilter() {
                public boolean accept(File file) {
                    return file.getName().endsWith(".xml");
                }
                public String getDescription() {
                    return "Arquivos XML";
                }
            });
            if (escolhedor.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
                PetriPanel painel = ji.getPetriPanel();
                File arquivo = escolhedor.getSelectedFile();
                try {
                    if (!arquivo.getName().endsWith(".xml")) {
                        System.out.println("asfas");
                        arquivo = new File(arquivo.getCanonicalPath() + ".xml");
                    }
                } catch (IOException ioe) { }
                Persistencia.salvarEmXml(arquivo, painel.getElementosGraficos());
                TranslateController.geraIL(arquivo, new File(arquivo.getAbsolutePath().replaceAll("xml", "txt")));
            }
        }
    }
    
    public void abrir() {
        JFileChooser escolhedor = new JFileChooser();
        escolhedor.setAcceptAllFileFilterUsed(false);
        escolhedor.setFileFilter(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".xml");
            }
            public String getDescription() {
                return "Arquivos XML";
            }
        });
        if (escolhedor.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
            File arquivo = escolhedor.getSelectedFile();
            this.criarNovaJanelaInterna(arquivo.getName());
            JanelaInterna ji = null;
            for (int i=0; i<janelasinternas.size(); i++) {
                if (((JanelaInterna)janelasinternas.get(i)).isSelected()) {
                    ji = (JanelaInterna)janelasinternas.get(i);
                }
            }
            PetriPanel painel = ji.getPetriPanel();
            
            painel.setElementosGraficos(Persistencia.abrirXML(arquivo));
        }
        atualizarGerenciador();
    }
    
    public void addVariavel(Variavel variavel) {
        variaveis.add(variavel);
    }
    
    public void removeVariavel(String variavel) {
        variaveis.remove(variavel);
    }
    
    public ArrayList getVariaveis() {
        return variaveis;
    }
    
    public ArrayList getOperadores() {
        return operadores;
    }
    
    public void setVariaveis(ArrayList variaveis) {
        this.variaveis = variaveis;
    }
    
    public void enviarParaTras() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                ji.getPetriPanel().enviarPraTras();
            }
        }
    }
    
    public void trazerParaFrente() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                ji.getPetriPanel().trazerPraFrente();
            }
        }
    }
    
    public void avancar() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                ji.getPetriPanel().avancar();
            }
        }
    }
    
    public void recuar() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                ji.getPetriPanel().recuar();
            }
        }
    }
    
    public ArrayList getJanelasInternas() {
        return this.janelasinternas;
    }
    
    public void setModeloGerenciador(ModeloTabelaGerenciador mtg) {
        this.modelogerenciador = mtg;
    }
    
    public void setTabelaGerenciador(JTable tabelagerenciador) {
        this.tabelagerenciador = tabelagerenciador;
    }
    
    public void atualizarGerenciador() {
        modelogerenciador.atualizar();
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                ArrayList elementos = ji.getPetriPanel().getElementosGraficos();
                ArrayList selecionados = ji.getPetriPanel().getSelecionados();
                for (int j=0; j<selecionados.size(); j++) {
                    int linha = elementos.indexOf(selecionados.get(j));
                    tabelagerenciador.addRowSelectionInterval(linha, linha);
                }
            }
        }
        Controle.getControle().repintarJanelaInterna();
    }
    
    public JComponent getPainelIO() {
        if(painelIO==null) {
            painelIO = new JPanel();
        }
        painelIO.removeAll();
        painelIO.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JScrollPane jsp = new JScrollPane(PainelIO.montaPainel());
        jsp.setBorder(null);
        painelIO.add(jsp);
        return painelIO;
    }
    
    public Variavel getVariavelPeloNome(String nome) {
        Variavel retorno = null;
        for (int i=0; i<variaveis.size(); i++) {
            Variavel v = (Variavel)variaveis.get(i);
            if (v.getNome().equals(nome)) retorno = v;
        }
        return retorno;
    }
    
    public void atualizarVariaveis() {
        PainelIO.getCurrentPainel().atualiza();
        Remoto.enviarStatus();
    }
    
    public void reset() {
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                PetriPanel painel = ji.getPetriPanel();
                ArrayList elementos = painel.getElementosGraficos();
                for (int j=0; j<elementos.size(); j++) {
                    ElementoGrafico eg = (ElementoGrafico)elementos.get(j);
                    if (eg instanceof JLugar) ((JLugar)eg).resetToInitialTokens();
                }
                painel.repaint();
                desligar();
                ligar();
            }
        }
    }
    
    public void iniciaServidor() {
        Remoto.iniciaServidor();
    }
    
    public void finalizaServidor() {
        Remoto.finalizaServidor();
    }
    public void refresh_tempo(){
        janelaprincipal.jLabel11.setText(" TempoLocal = "+Token.getToken().getRelogioLocal());
        repintarJanelaInterna();
        janelaprincipal.jPanel3.paintImmediately(0,0,1280,800);
//        janelaprincipal.jToolBar1.paintImmediately(0,0,1024,768);
    }
    public void refresh_chamada(){
        
        for (int i=0; i<janelasinternas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelasinternas.get(i);
            if (ji.isSelected()) {
                ji.getPetriPanel().refresh_chamada();
            }
        }
    }
}