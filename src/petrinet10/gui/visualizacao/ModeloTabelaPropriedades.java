/*
 * ModeloTabelaPropriedades.java
 *
 * Created on 9 de Novembro de 2006, 14:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import petrinet10.gui.controle.Controle;
import petrinet10.gui.visualizacao.elementosgraficos.ElementoGrafico;
import petrinet10.gui.visualizacao.elementosgraficos.Elipse;
import petrinet10.gui.visualizacao.elementosgraficos.JArco;
import petrinet10.gui.visualizacao.elementosgraficos.JLugar;
import petrinet10.gui.visualizacao.elementosgraficos.JTexto;
import petrinet10.gui.visualizacao.elementosgraficos.JTransicao;
import petrinet10.gui.visualizacao.elementosgraficos.Reta;
import petrinet10.gui.visualizacao.elementosgraficos.Retangulo;

/**
 *
 * @author _Renatu
 */
public class ModeloTabelaPropriedades extends AbstractTableModel implements TableModelListener {
    
    private ElementoGrafico elementografico;
    private Class classescolunas[];
    private String propriedades[];
    private Object[] valores;
    
    /** Creates a new instance of ModeloTabelaPropriedades */
    public ModeloTabelaPropriedades() {
        valoresPadrao();
        addTableModelListener(this);
    }
    
    private void valoresPadrao() {
        classescolunas = null;
        propriedades = null;
        valores = null;
    }
    
    public void setElementoGrafico(ElementoGrafico eg) {
        this.elementografico = eg;
        if (eg==null) {
            valoresPadrao();
        } else if (eg instanceof JLugar) {
            JLugar lugar = (JLugar)eg;
            propriedades = new String[] { "Nome", "Tamanho" , "Marcas", "Marcas Iniciais", "Capacidade", "Código" };
            valores = new Object[] {    lugar.getNome(),
                new Integer(lugar.getTamanho()),
                new Integer(lugar.getTokens()),
                new Integer(lugar.getInitialtokens()),
                new Integer(lugar.getCapacidade()),
                lugar.getCodigo() };
        } else if (eg instanceof JTransicao) {
            JTransicao transicao = (JTransicao)eg;
            //DEVO INSERIOR O CAMPO TYPE?? ou editaremos o código no notepad?
            propriedades = new String[] { "Nome", "Tamanho", "Código" };
            valores = new Object[] {    transicao.getNome(),
                new Integer(transicao.getTamanho()),
                transicao.getCodigo() };
        } else if (eg instanceof Reta) {
            Reta reta = (Reta)eg;
            propriedades = new String[] { "Cor", "Espessura" };
            valores = new Object[] {    reta.getCor() ,
            new Double(reta.getEspessura()) };
        } else if (eg instanceof Retangulo) {
            Retangulo retangulo = (Retangulo)eg;
            propriedades = new String[] { "Cor", "Espessura", "Preenchimento" };
            valores = new Object[] {    retangulo.getCor(),
            new Double(retangulo.getEspessura()),
            retangulo.getFundo() };
        } else if (eg instanceof JTexto) {
            JTexto texto = (JTexto)eg;
            propriedades = new String[] { "Texto", "Cor", "Tamanho" };
            valores = new Object[] {    texto.getTexto(),
            texto.getCor(),
            new Integer(texto.getTamanto()) };
        } else if (eg instanceof Elipse) {
            Elipse elipse = (Elipse)eg;
            propriedades = new String[] { "Cor", "Espessura", "Preenchimento" };
            valores = new Object[] {    elipse.getCor(),
            new Double(elipse.getEspessura()),
            elipse.getFundo() };
        } else if (eg instanceof JArco) {
            JArco arco = (JArco)eg;
            propriedades = new String[] { "Peso", "Tipo" };
            int tipo = arco.getTipo();
            String stipo = "Normal";
            if (tipo == JArco.TIPO_INIBIDOR) stipo = "Inibidor";
            if (tipo == JArco.TIPO_HABILITADOR) stipo = "Habilitador";
            valores = new Object[] { new Integer(arco.getPeso()) , stipo };
        }
        fireTableDataChanged();
    }
    
    public int getColumnCount() {
        return 2;
    }
    
    public int getRowCount() {
        return propriedades==null?0:propriedades.length;
    }
    
    public Object getValueAt(int i, int i0) {
        if (i0==0) {
            return propriedades[i];
        } else {
            if (valores!=null) {
                return valores[i];
            } else {
                return null;
            }
        }
    }
    
    public Class getColumnClass(int i) {
        return i==0?String.class:Object.class;
    }
    
    public String getColumnName(int i) {
        if (i==0) return "Propriedade";
        else return "Valor";
    }
    
    public boolean isCellEditable(int i, int i0) {
        if (i0==1) return true;
        else return false;
    }
    
    public void setValueAt(Object object, int i, int i0) {
        valores[i] = object;
        fireTableCellUpdated(i,i0);
    }
    
    public void tableChanged(TableModelEvent tableModelEvent) {
        if (elementografico instanceof JLugar) {
            JLugar lugar = (JLugar)elementografico;
            String nome = (String)valores[0];
            Integer tamanho = (Integer)valores[1];
            Integer marcas = (Integer)valores[2];
            Integer marcasiniciais = (Integer)valores[3];
            Integer capacidade = (Integer)valores[4];
            ArrayList codigo = (ArrayList)valores[5];
            lugar.setNome(nome);
            lugar.setTamanho(tamanho.intValue());
            lugar.setTokens(marcas.intValue());
            lugar.setInitialtokens(marcasiniciais.intValue());
            lugar.setCapacidade(capacidade.intValue());
            lugar.setCodigo(codigo);
            Controle.getControle().repintarJanelaInterna();
        } else if (elementografico instanceof JTransicao) {
            JTransicao transicao = (JTransicao)elementografico;
            String nome = (String)valores[0];
            Integer tamanho = (Integer)valores[1];
            ArrayList codigo = (ArrayList)valores[2];
            transicao.setNome(nome);
            transicao.setTamanho(tamanho.intValue());
            transicao.setCodigo(codigo);
            Controle.getControle().repintarJanelaInterna();
        } else if (elementografico instanceof Reta) {
            Reta reta = (Reta)elementografico;
            Color cor = (Color)valores[0];
            Double espessura = (Double)valores[1];
            reta.setCor(cor);
            reta.setEspessura(espessura.doubleValue());
            Controle.getControle().repintarJanelaInterna();
        } else if (elementografico instanceof Retangulo) {
            Retangulo retangulo = (Retangulo)elementografico;
            Color cor = (Color)valores[0];
            Double espessura = (Double)valores[1];
            Color preenchimento = (Color)valores[2];
            retangulo.setCor(cor);
            retangulo.setFundo(preenchimento);
            retangulo.setEspessura(espessura.doubleValue());
            Controle.getControle().repintarJanelaInterna();
        } else if (elementografico instanceof JTexto) {
            JTexto jtexto = (JTexto)elementografico;
            String texto = (String)valores[0];
            Color cor = (Color)valores[1];
            Integer tamanho = (Integer)valores[2];
            jtexto.setCor(cor);
            jtexto.setTexto(texto);
            jtexto.setTamanho(tamanho.intValue());
            Controle.getControle().repintarJanelaInterna();
        } else if (elementografico instanceof Elipse) {
            Elipse elipse = (Elipse)elementografico;
            Color cor = (Color)valores[0];
            Double espessura = (Double)valores[1];
            Color preenchimento = (Color)valores[2];
            elipse.setCor(cor);
            elipse.setFundo(preenchimento);
            elipse.setEspessura(espessura.doubleValue());
            Controle.getControle().repintarJanelaInterna();
        } else if (elementografico instanceof JArco) {
            JArco arco = (JArco)elementografico;
            Integer peso = (Integer)valores[0];
            String stipo = (String)valores[1];
            int tipo = JArco.TIPO_NORMAL;
            if (stipo.equals("Habilitador")) tipo = JArco.TIPO_HABILITADOR;
            if (stipo.equals("Inibidor")) tipo = JArco.TIPO_INIBIDOR;
            arco.setPeso(peso.intValue());
            arco.setTipo(tipo);
        }
    }
    
    public ElementoGrafico getElementoGrafico() {
        return this.elementografico;
    }
    
}
