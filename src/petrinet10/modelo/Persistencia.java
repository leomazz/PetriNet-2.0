/*
 * Persistencia.java
 *
 * Created on 14 de Novembro de 2006, 01:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.modelo;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import petrinet10.core.Codigo;
import petrinet10.core.Variavel;
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
public class Persistencia {
    
    private static Hashtable lugares, transicoes;
    
    public static boolean salvarEmXml(File arquivo, ArrayList elementosgraficos) {
        boolean retorno = false;
        Document doc = new Document();
        Element raiz = new Element("net");
        doc.setRootElement(raiz);
        raiz.setAttribute("id", "rede");
        adicionarSinais(raiz);
        for (int i=0; i<elementosgraficos.size(); i++) {
            adicionarElemento(raiz, (ElementoGrafico)elementosgraficos.get(i));
        }
        XMLOutputter outp = new XMLOutputter();
        try {
            outp.setFormat(Format.getPrettyFormat());
            outp.output(doc, new FileWriter(arquivo));
            retorno = true;
        } catch (IOException ioe) { }
        return retorno;
    }
    
    private static void adicionarSinais(Element raiz) {
        ArrayList sinais = Controle.getControle().getVariaveis();
        if (sinais!=null && sinais.size()>0) {
            Element signals = new Element("signals");
            raiz.addContent(signals);
            for (int i=0; i<sinais.size(); i++) {
                Element var = new Element("var");
                Variavel v = (Variavel)sinais.get(i);
                String tipo = "input";
                if (v.getEntradasaida()==Variavel.SAIDA) tipo = "output";
                else if (v.getEntradasaida()==Variavel.ENTRADASAIDA) tipo = "input/output";
                var.setAttribute("type", tipo);
                var.setAttribute("id", v.getEndereco());
                var.setText(v.getNome());
                signals.addContent(var);
            }
        }
    }
    
    private static void adicionarElemento(Element raiz, ElementoGrafico eg) {
        Element elemento = null;
        if (eg instanceof JLugar) {
            JLugar lugar = (JLugar)eg;
            elemento = new Element("place");
            elemento.setAttribute("id", lugar.getNome());
            elemento.setAttribute("x", String.valueOf(lugar.getPosicao().getX()));
            elemento.setAttribute("y", String.valueOf(lugar.getPosicao().getY()));
            elemento.setAttribute("desc", lugar.getNome());
            elemento.setAttribute("tamanho", String.valueOf(lugar.getTamanho()));
            elemento.setAttribute("capacidade", String.valueOf(lugar.getCapacidade()));
            elemento.setAttribute("offx", "0");
            elemento.setAttribute("offy", "0");
            Element marcas = new Element("initialMarking");
            marcas.setAttribute("marking", lugar.getInitialtokens()>0?"true":"false");
            marcas.setAttribute("offx", "0");
            marcas.setAttribute("offy", "0");
            if (lugar.getInitialtokens()>0) marcas.setAttribute("value", String.valueOf(lugar.getInitialtokens()));
            elemento.addContent(marcas);
            ArrayList listadecodigos = lugar.getCodigo();
            if (listadecodigos.size()>0) {
                Element codigo = new Element("code");
                elemento.addContent(codigo);
                for (int i=0; i<listadecodigos.size(); i++) {
                    Codigo c = (Codigo)listadecodigos.get(i);
                    Element statement = new Element("statement");
                    Element operador = new Element("operator");
                    Element comment = new Element("comment");
                    comment.setText("X");
                    operador.setText(c.getOperador());
                    Element sinal = new Element("signal");
                    sinal.setText(c.getSinal().getNome());
                    statement.addContent(operador);
                    statement.addContent(sinal);
                    statement.addContent(comment);
                    codigo.addContent(statement);
                }
            }
        } else if (eg instanceof JTransicao) {
            JTransicao transicao = (JTransicao)eg;
            elemento = new Element("transition");
            elemento.setAttribute("id", transicao.getNome());
            elemento.setAttribute("x", String.valueOf(transicao.getPosicao().getX()));
            elemento.setAttribute("y", String.valueOf(transicao.getPosicao().getY()));
            elemento.setAttribute("tamanho", String.valueOf(transicao.getTamanho()));
            elemento.setAttribute("type", "normal");
            elemento.setAttribute("desc", transicao.getNome());
            elemento.setAttribute("offx", "0");
            elemento.setAttribute("offy", "0");
            ArrayList listadecodigos = transicao.getCodigo();
            if (listadecodigos.size()>0) {
                Element codigo = new Element("code");
                elemento.addContent(codigo);
                for (int i=0; i<listadecodigos.size(); i++) {
                    Codigo c = (Codigo)listadecodigos.get(i);
                    Element statement = new Element("statement");
                    Element operador = new Element("operator");
                    Element comment = new Element("comment");
                    comment.setText("X");
                    operador.setText(c.getOperador());
                    Element sinal = new Element("signal");
                    sinal.setText(c.getSinal().getNome());
                    statement.addContent(operador);
                    statement.addContent(sinal);
                    statement.addContent(comment);
                    codigo.addContent(statement);
                }
            }
        } else if (eg instanceof Reta) {
            Reta reta = (Reta)eg;
            elemento = new Element("reta");
            elemento.setAttribute("id", "reta");
            elemento.setAttribute("x", String.valueOf(reta.getPosicao().getX()));
            elemento.setAttribute("y", String.valueOf(reta.getPosicao().getY()));
            elemento.setAttribute("altura", String.valueOf(reta.getAltura()));
            elemento.setAttribute("largura", String.valueOf(reta.getLargura()));
            elemento.setAttribute("espessura", String.valueOf(reta.getEspessura()));
            elemento.setAttribute("cor", String.valueOf(reta.getCor().getRGB()));
        } else if (eg instanceof Retangulo) {
            Retangulo retangulo = (Retangulo)eg;
            elemento = new Element("retangulo");
            elemento.setAttribute("id", "retangulo");
            elemento.setAttribute("x", String.valueOf(retangulo.getPosicao().getX()));
            elemento.setAttribute("y", String.valueOf(retangulo.getPosicao().getY()));
            elemento.setAttribute("altura", String.valueOf(retangulo.getAltura()));
            elemento.setAttribute("largura", String.valueOf(retangulo.getLargura()));
            elemento.setAttribute("espessura", String.valueOf(retangulo.getEspessura()));
            elemento.setAttribute("cor", String.valueOf(retangulo.getCor().getRGB()));
            if (retangulo.getFundo()!=null) elemento.setAttribute("fundo", String.valueOf(retangulo.getFundo().getRGB()));
        } else if (eg instanceof JArco) {
            JArco arco = (JArco)eg;
            elemento = new Element("arc");
            elemento.setAttribute("id", "a0");
            int sentido = arco.getSentido();
            elemento.setAttribute("sentido", String.valueOf(sentido));
            elemento.setAttribute("source", sentido==JArco.LUGAR_TRANSICAO?arco.getLugar().getNome():arco.getTransicao().getNome());
            elemento.setAttribute("target", sentido==JArco.LUGAR_TRANSICAO?arco.getTransicao().getNome():arco.getLugar().getNome());
            elemento.setAttribute("x", "0");
            elemento.setAttribute("y", "0");
            String tipo = "standard";
            if (arco.getTipo()==JArco.TIPO_INIBIDOR) tipo = "inhibitor";
            if (arco.getTipo()==JArco.TIPO_HABILITADOR) tipo = "test";
            elemento.setAttribute("type", tipo);
            ArrayList lista = arco.getPontos();
            if (lista.size()>0) {
                Element pontos = new Element("pontos");
                elemento.addContent(pontos);
                for (int i=0; i<lista.size(); i++) {
                    Point2D.Double ponto = (Point2D.Double)lista.get(i);
                    Element p = new Element("ponto");
                    p.setAttribute("x", String.valueOf(ponto.getX()));
                    p.setAttribute("y", String.valueOf(ponto.getY()));
                    pontos.addContent(p);
                }
            }
        } else if (eg instanceof JTexto) {
            JTexto texto = (JTexto)eg;
            elemento = new Element("texto");
            elemento.setAttribute("id", "texto");
            elemento.setAttribute("x", String.valueOf(texto.getPosicao().getX()));
            elemento.setAttribute("y", String.valueOf(texto.getPosicao().getY()));
            elemento.setAttribute("tamanho", String.valueOf(texto.getTamanto()));
            elemento.setAttribute("cor", String.valueOf(texto.getCor().getRGB()));
            elemento.setText(texto.getTexto());
        } else if (eg instanceof Elipse) {
            Elipse elipse = (Elipse)eg;
            elemento = new Element("elipse");
            elemento.setAttribute("id", "elipse");
            elemento.setAttribute("x", String.valueOf(elipse.getPosicao().getX()));
            elemento.setAttribute("y", String.valueOf(elipse.getPosicao().getY()));
            elemento.setAttribute("altura", String.valueOf(elipse.getAltura()));
            elemento.setAttribute("largura", String.valueOf(elipse.getLargura()));
            elemento.setAttribute("espessura", String.valueOf(elipse.getEspessura()));
            elemento.setAttribute("cor", String.valueOf(elipse.getCor().getRGB()));
            if (elipse.getFundo()!=null) elemento.setAttribute("fundo", String.valueOf(elipse.getFundo().getRGB()));
        }
        raiz.addContent(elemento);
    }
    
    public static ArrayList abrirXML(File arquivo) {
        Document doc = null;
        ArrayList retorno = null;
        Controle.getControle().setVariaveis(new ArrayList());
        try {
            doc = new SAXBuilder().build(arquivo);
        } catch (JDOMException je) {
        } catch (IOException ioe) { }
        if (doc!=null) {
            lugares = new Hashtable();
            transicoes = new Hashtable();
            retorno = new ArrayList();
            Element raiz = doc.getRootElement();
            List filhos = raiz.getChildren();
            for (int i=0; i<filhos.size(); i++) {
                ElementoGrafico eg = getElemento((Element)filhos.get(i));
                if (eg!=null) retorno.add(eg);
            }
        }
        return retorno;
    }
    
    private static ElementoGrafico getElemento(Element elemento) {
        String nome = elemento.getName();
        ElementoGrafico retorno = null;
        if (nome.equals("place")) {
            String nomelugar = elemento.getAttribute("id").getValue();
            String sx = elemento.getAttribute("x").getValue();
            String sy = elemento.getAttribute("y").getValue();
            String stamanho = elemento.getAttribute("tamanho").getValue();
            String scapacidade = elemento.getAttribute("capacidade").getValue();
            retorno = new JLugar(Double.parseDouble(sx), Double.parseDouble(sy), Integer.parseInt(stamanho));
            ((JLugar)retorno).setCapacidade(Integer.parseInt(scapacidade));
            ((JLugar)retorno).setNome(nomelugar);
            lugares.put(nomelugar, retorno);
            Element marcas = elemento.getChild("initialMarking");
            if (marcas!=null) {
                Attribute a = marcas.getAttribute("marking");
                if (a!=null && a.getValue().equals("true")) {
                    Attribute valor = marcas.getAttribute("value");
                    int initialtokens = 1;
                    if (valor!=null) {
                        initialtokens = Integer.parseInt(valor.getValue());
                    }
                    ((JLugar)retorno).setInitialtokens(initialtokens);
                }
            }
            //Ele lê o <code> </code>
            Element codigo = elemento.getChild("code");
            if (codigo!=null) {
                List statements = codigo.getChildren();
                ArrayList listacodigo = new ArrayList();
                for (int i=0; i<statements.size(); i++) {
                    Element e = (Element)statements.get(i);
                    String operador = e.getChild("operator").getText();
                    String sinal = e.getChild("signal").getText();
                    Codigo c = new Codigo(operador, Controle.getControle().getVariavelPeloNome(sinal));
                    listacodigo.add(c);
                }
                ((JLugar)retorno).setCodigo(listacodigo);
            }
        } else if (nome.equals("transition")) {
            String nometransicao = elemento.getAttribute("id").getValue();
            String sx = elemento.getAttribute("x").getValue();
            String sy = elemento.getAttribute("y").getValue();
            String stamanho = elemento.getAttribute("tamanho").getValue();
            String stype = elemento.getAttribute("type").getValue();
            
            //ele cria uma JTransicao SEM "TYPE" ; atualizei... COM TYPE!
            
            if(stype.equals("TF")) {
                String s1 = elemento.getAttribute("s1").getValue();
                String s2 = elemento.getAttribute("s2").getValue();
                String t1 = elemento.getAttribute("t1").getValue();
                String t2 = elemento.getAttribute("t2").getValue();
                
                //nometransicao = t1+"_"+s1+"/"+t2+"_"+s2;
                //System.out.println("nome: "+t1+"_"+s1+"/"+t2+"_"+s2);
                retorno = new JTransicao(Double.parseDouble(sx), 
                    Double.parseDouble(sy), Integer.parseInt(stamanho),
                    stype, s1, s2, t1, t2);
                //System.out.println("stype="+stype);
                //System.out.println("nome="+nometransicao);
                
            } else if(stype.equals("TT")){
                int tempo = Integer.parseInt(elemento.getAttribute("time").getValue());
                retorno = new JTransicao(Double.parseDouble(sx), 
                Double.parseDouble(sy), Integer.parseInt(stamanho), stype, tempo);
                //System.out.println("stype="+stype);
            }
            else {
            
            retorno = new JTransicao(Double.parseDouble(sx), 
                    Double.parseDouble(sy), Integer.parseInt(stamanho),
                    "normal");
            //System.out.println("stype="+stype);
            }
            //aqui ele define o nome e depois joga na HASH.
            
            ((JTransicao)retorno).setNome(nometransicao);
            transicoes.put(nometransicao, retorno); //poe na hashtable
            
            
            Element codigo = elemento.getChild("code");
            //System.out.println("stype="+stype);
            
            //aqui deveremos criar as separacoes... T, TT, TF
            
            
            if (codigo!=null) {
                List statements = codigo.getChildren();
                ArrayList listacodigo = new ArrayList();
                for (int i=0; i<statements.size(); i++) {
                    Element e = (Element)statements.get(i);
                    String operador = e.getChild("operator").getText();
                    String sinal = e.getChild("signal").getText();
                    Codigo c = new Codigo(operador, Controle.getControle().getVariavelPeloNome(sinal));
                    listacodigo.add(c);
                }
                ((JTransicao)retorno).setCodigo(listacodigo);
            }
        } else if (nome.equals("reta")) {
            String sx = elemento.getAttribute("x").getValue();
            String sy = elemento.getAttribute("y").getValue();
            String saltura = elemento.getAttribute("altura").getValue();
            String slargura = elemento.getAttribute("largura").getValue();
            String sespessura = elemento.getAttribute("espessura").getValue();
            String scor = elemento.getAttribute("cor").getValue();
            retorno = new Reta(Double.parseDouble(sx), Double.parseDouble(sy),
                    Double.parseDouble(sx)+Double.parseDouble(slargura),
                    Double.parseDouble(sy)+Double.parseDouble(saltura));
            ((Reta)retorno).setEspessura(Double.parseDouble(sespessura));
            ((Reta)retorno).setCor(new Color(Integer.parseInt(scor)));
        } else if (nome.equals("retangulo")) {
            String sx = elemento.getAttribute("x").getValue();
            String sy = elemento.getAttribute("y").getValue();
            String saltura = elemento.getAttribute("altura").getValue();
            String slargura = elemento.getAttribute("largura").getValue();
            String sespessura = elemento.getAttribute("espessura").getValue();
            String scor = elemento.getAttribute("cor").getValue();
            String sfundo = null;
            if (elemento.getAttribute("fundo")!=null) sfundo = elemento.getAttribute("fundo").getValue();
            retorno = new Retangulo(Double.parseDouble(sx), Double.parseDouble(sy),
                    Double.parseDouble(slargura), Double.parseDouble(saltura));
            ((Retangulo)retorno).setEspessura(Double.parseDouble(sespessura));
            ((Retangulo)retorno).setCor(new Color(Integer.parseInt(scor)));
            if (sfundo!=null) ((Retangulo)retorno).setFundo(new Color(Integer.parseInt(sfundo)));
        } else if (nome.equals("arc")) {
            String ssentido = elemento.getAttribute("sentido").getValue();
            String ssource = elemento.getAttribute("source").getValue();
            String starget = elemento.getAttribute("target").getValue();
            String stype = elemento.getAttribute("type").getValue();
            //criar arco
            int sentido = Integer.parseInt(ssentido);
            JLugar lugar = null;
            JTransicao transicao = null;
            if (sentido==JArco.LUGAR_TRANSICAO) {
                lugar = (JLugar)lugares.get(ssource);
                transicao = (JTransicao)transicoes.get(starget);
            } else {
                lugar = (JLugar)lugares.get(starget);
                transicao = (JTransicao)transicoes.get(ssource);
            }
            retorno = new JArco(lugar, transicao, sentido);
            if (stype.equals("inhibitor")) ((JArco)retorno).setTipo(JArco.TIPO_INIBIDOR);
            if (stype.equals("test")) ((JArco)retorno).setTipo(JArco.TIPO_HABILITADOR);
            Element pontos = elemento.getChild("pontos");
            if (pontos!=null) {
                ArrayList listadepontos = new ArrayList();
                List lista = pontos.getChildren();
                for (int i=0; i<lista.size(); i++) {
                    Element e = (Element)lista.get(i);
                    String sx = e.getAttribute("x").getValue();
                    String sy = e.getAttribute("y").getValue();
                    listadepontos.add(new Point2D.Double(Double.parseDouble(sx), Double.parseDouble(sy)));
                }
                ((JArco)retorno).setPontos(listadepontos);
            }
        } else if (nome.equals("texto")) {
            String sx = elemento.getAttribute("x").getValue();
            String sy = elemento.getAttribute("y").getValue();
            String stamanho = elemento.getAttribute("tamanho").getValue();
            String scor = elemento.getAttribute("cor").getValue();
            String texto = elemento.getText();
            retorno = new JTexto(Double.parseDouble(sx), Double.parseDouble(sy), Color.BLACK, texto);
            ((JTexto)retorno).setTamanho(Integer.parseInt(stamanho));
            ((JTexto)retorno).setCor(new Color(Integer.parseInt(scor)));
        } else if (nome.equals("elipse")) {
            String sx = elemento.getAttribute("x").getValue();
            String sy = elemento.getAttribute("y").getValue();
            String saltura = elemento.getAttribute("altura").getValue();
            String slargura = elemento.getAttribute("largura").getValue();
            String sespessura = elemento.getAttribute("espessura").getValue();
            String scor = elemento.getAttribute("cor").getValue();
            String sfundo = null;
            if (elemento.getAttribute("fundo")!=null) sfundo = elemento.getAttribute("fundo").getValue();
            retorno = new Elipse(Double.parseDouble(sx), Double.parseDouble(sy), Double.parseDouble(slargura), Double.parseDouble(saltura));
            ((Elipse)retorno).setEspessura(Double.parseDouble(sespessura));
            ((Elipse)retorno).setCor(new Color(Integer.parseInt(scor)));
            if (sfundo!=null) ((Elipse)retorno).setFundo(new Color(Integer.parseInt(sfundo)));
        } else if (nome.equals("signals")) {
            List variaveis = elemento.getChildren();
            if (variaveis!=null) {
                ArrayList lista = new ArrayList();
                for (int i=0; i<variaveis.size(); i++) {
                    Element e = (Element)variaveis.get(i);
                    String tipo = e.getAttribute("type").getValue();
                    String id = e.getAttribute("id").getValue();
                    String descricao = e.getText();
                    int itipo = Variavel.ENTRADA;
                    if (tipo.equals("output")) itipo = Variavel.SAIDA;
                    else if (tipo.equals("input/output")) itipo = Variavel.ENTRADASAIDA;
                    Variavel v = new Variavel(descricao, "BOOLEANO", itipo, id);
                    lista.add(v);
                }
                Controle.getControle().setVariaveis(lista);
            }
        }
        return retorno;
    }
    
}
