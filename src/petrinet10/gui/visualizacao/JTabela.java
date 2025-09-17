/*
 * JTabela.java
 *
 * Created on 15 de Novembro de 2006, 14:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import petrinet10.gui.visualizacao.elementosgraficos.JArco;

/**
 *
 * @author _Renatu
 */
public class JTabela extends JTable {
    
    private EditorDeCor editor;
    private RenderizadorDeCor renderizador;
    private DefaultTableCellRenderer rendpadrao;
    private DefaultCellEditor editorpadrao;
    private EditorDeNumeros editordeint, editordedouble;
    private RenderizadorDeNumeros rendnumeros;
    private RenderizadorDeCodigo rendcodigo;
    private EditorDeCodigo editorcodigo;
    private DefaultCellEditor editorarco;
    
    /** Creates a new instance of JTabela */
    public JTabela() {
        super();
        editor = new EditorDeCor();
        renderizador = new RenderizadorDeCor();
        rendpadrao = new DefaultTableCellRenderer();
        editorpadrao = new DefaultCellEditor(new JTextField());
        editordeint = new EditorDeNumeros(EditorDeNumeros.INT);
        editordedouble = new EditorDeNumeros(EditorDeNumeros.DOUBLE);
        rendnumeros = new RenderizadorDeNumeros();
        rendcodigo = new RenderizadorDeCodigo();
        editorcodigo = new EditorDeCodigo();
        JComboBox combo = new JComboBox(new String[] { "Normal", "Habilitador", "Inibidor" });
        editorarco = new DefaultCellEditor(combo);
    }
    
    public TableCellRenderer getCellRenderer(int i, int j) {
        Object valor = getValueAt(i,j);
        if (j==0) {
            return rendpadrao;
        } else if (valor instanceof Color) {
            return renderizador;
        } else if (valor instanceof Double) {
            return rendnumeros;
        } else if (valor instanceof Integer) {
            return rendnumeros;
        } else if (valor instanceof ArrayList) {
            return rendcodigo;
        } else {
            return rendpadrao;
        }
    }
    
    public TableCellEditor getCellEditor(int i, int i0) {
        Object valor = getValueAt(i,i0);
        ModeloTabelaPropriedades mtp = (ModeloTabelaPropriedades)this.getModel();
        if (i0==0) {
            return editorpadrao;
        } else if (valor instanceof Color) {
            return editor;
        } else if (valor instanceof Double) {
            return editordedouble;
        } else if (valor instanceof Integer) {
            return editordeint;
        } else if (valor instanceof ArrayList) {
            return editorcodigo;
        } else if (mtp!=null && mtp.getElementoGrafico() instanceof JArco && valor instanceof String) {
            return editorarco;
        } else {
            return editorpadrao;
        }
    }
}
