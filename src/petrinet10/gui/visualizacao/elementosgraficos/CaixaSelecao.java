/*
 * CaixaSelecao.java
 *
 * Created on 12 de Maio de 2006, 16:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao.elementosgraficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author _Renatu
 */
public class CaixaSelecao extends ElementoGrafico {
    
    public final static int NORTE = 0;
    public final static int SUL = 1;
    public final static int LESTE = 2;
    public final static int OESTE = 3;
    public final static int NORDESTE = 4;
    public final static int NOROESTE = 5;
    public final static int SUDESTE = 6;
    public final static int SUDOESTE = 7;
    
    private ArrayList elementosselecionados;
    private Rectangle2D.Double norte, sul, leste, oeste, nordeste, noroeste, sudeste, sudoeste;
    
    /** Creates a new instance of CaixaSelecao */
    public CaixaSelecao(ArrayList elementosselecionados) {
        this.elementosselecionados = elementosselecionados;
    }
    
    public void desenhar(Graphics2D g2) {
        int x1, y1, x2, y2;
        x1=Integer.MAX_VALUE;
        x2=0;
        y1=Integer.MAX_VALUE;
        y2=0;
        if (elementosselecionados.size()>0) {
            for (int i=0; i<elementosselecionados.size(); i++) {
                Rectangle2D r2 = ((ElementoGrafico)elementosselecionados.get(i)).getShape().getBounds2D();
                x1 = (int)(r2.getMinX()<x1?r2.getMinX():x1);
                y1 = (int)(r2.getMinY()<y1?r2.getMinY():y1);
                x2 = (int)(r2.getMaxX()>x2?r2.getMaxX():x2);
                y2 = (int)(r2.getMaxY()>y2?r2.getMaxY():y2);
            }
            Rectangle2D.Double r2 = new Rectangle2D.Double(x1, y1, x2-x1, y2-y1);
            g2.setColor(Color.BLACK);
            g2.draw(r2);
            int t = 6;
            noroeste = new Rectangle2D.Double(x1-t/2,y1-t/2,t,t);
            sudoeste = new Rectangle2D.Double(x1-t/2,y2-t/2,t,t);
            nordeste = new Rectangle2D.Double(x2-t/2,y1-t/2,t,t);
            sudeste = new Rectangle2D.Double(x2-t/2,y2-t/2,t,t);
            norte = new Rectangle2D.Double((x1+x2-t)/2,y1-t/2,t,t);
            sul = new Rectangle2D.Double((x1+x2-t)/2,y2-t/2,t,t);
            leste = new Rectangle2D.Double(x2-t/2,(y1+y2-t)/2,t,t);
            oeste = new Rectangle2D.Double(x1-t/2,(y1+y2-t)/2,t,t);
            g2.fill(norte);
            g2.fill(sul);
            g2.fill(leste);
            g2.fill(oeste);
            g2.fill(sudeste);
            g2.fill(sudoeste);
            g2.fill(nordeste);
            g2.fill(noroeste);
        }
    }
    
    public Shape getShape() {
        return null;
    }
    
    public int contem(Point2D.Double ponto) {
        if (elementosselecionados.size()>0) {
            if (norte.contains(ponto)) return this.NORTE;
            else if (sul.contains(ponto)) return this.SUL;
            else if (leste.contains(ponto)) return this.LESTE;
            else if (oeste.contains(ponto)) return this.OESTE;
            else if (nordeste.contains(ponto)) return this.NORDESTE;
            else if (noroeste.contains(ponto)) return this.NOROESTE;
            else if (sudeste.contains(ponto)) return this.SUDESTE;
            else if (sudoeste.contains(ponto)) return this.SUDOESTE;
        }
        return -1;
    }
    
}
