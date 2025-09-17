/**
 * PNMLReader2.java
 */
package tcc;

import java.io.File;
import java.io.IOException; //testando... .
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.JDOMException;

public class XMLProcessor
{
     private SAXBuilder saxBuilder;
     private File file;
     private Document doc;
     
     public XMLProcessor(String filePath)
     {
          try
          {
               saxBuilder = new SAXBuilder();
               file = new File(filePath);
               doc = saxBuilder.build(file);
          }
          catch(JDOMException e)
          {
               e.printStackTrace();
          }
          catch(IOException e)
          {
               e.printStackTrace();
          }
     }
     
     public List XMLInterpreter(String s)
     {
          List list = new LinkedList();
          // le e armazena as variáveis de entrada e de saida do modelo em sipn.
          if(s.equals("signals"))
               list = signalsInterpreter();
          // le e armazena dados dos lugares do modelo em sipn.
          else if(s.equals("place"))
               list = placeInterpreter();
          // le e armazena dados das transições e dos arcos do modelo em sipn.
          else if(s.equals("transition"))
               list = transitionInterpreter();
          return list;
     }
     
     public List signalsInterpreter()
     {
          List list = new LinkedList();
          ListIterator itr;
          List elementsList;
          Element element;
          List inputSignalList = new LinkedList();
          List outputSignalList = new LinkedList();
          elementsList = doc.getRootElement().getChild("signals").getChildren("var");
          itr = elementsList.listIterator();
          IOSignals ios;
          while(itr.hasNext())
          {
               element = (Element)itr.next();
               ios = new IOSignals(element.getAttributeValue("type"),
                         element.getAttributeValue("id"),
                         element.getText());
               if(element.getAttributeValue("type").equals("input"))
                    inputSignalList.add(ios);
               else
                    outputSignalList.add(ios);
          }
          list.addAll(inputSignalList);
          list.addAll(outputSignalList);
          return list;
     }
     
     public List placeInterpreter()
     {
          List list = new LinkedList();
          ListIterator itr;
          List elementsList;
          Element element;
          elementsList = doc.getRootElement().getChildren("place");
          itr = elementsList.listIterator();
          Condition c;
          while(itr.hasNext())
          {
               element = (Element)itr.next();
               c = new Condition();
               c.setId(element.getAttributeValue("id"));
               c.setX(Double.parseDouble(element.getAttributeValue("x")));
               c.setY(Double.parseDouble(element.getAttributeValue("y")));
               c.setDesc(element.getAttributeValue("desc"));
               c.setDescOffx(Double.parseDouble(element.getAttributeValue("offx")));
               c.setDescOffy(Double.parseDouble(element.getAttributeValue("offy")));
               List children = element.getChildren();
               ListIterator itr2 = children.listIterator();
               while(itr2.hasNext())
               {
                    element = (Element)itr2.next();
                    if(element.getName().equals("initialMarking"))
                    {
                         c.setInitialMarking(element.getAttributeValue("marking").equals("true") ? true : false);
                         c.setMarkingOffx(Double.parseDouble(element.getAttributeValue("offx")));
                         c.setMarkingOffy(Double.parseDouble(element.getAttributeValue("offy")));
                    }
                    else if(element.getName().equals("output"))
                    {
                         c.setOutputDesc(element.getAttributeValue("desc"));
                         c.setOutputDescOffx(Double.parseDouble(element.getAttributeValue("offx")));
                         c.setOutputDescOffy(Double.parseDouble(element.getAttributeValue("offy")));
                    }
                    else if(element.getName().equals("code"))
                    {
                         // alterar para a nova estrutura do xml de entrada. Ver versão V105.xml
                         //c.setILCode(element.getText());
                         List children2 = element.getChildren("statement");
                         ListIterator itr3 = children2.listIterator();
                         while(itr3.hasNext())
                         {
                              Element element2 = (Element)itr3.next();
                              c.insertAtOperatorsList(element2.getChild("operator").getText());
                              c.insertAtOutputSignalsList(element2.getChild("signal").getText());
                              c.insertAtCommentsList(element2.getChild("comment").getText());
                         }
                    }
               }
               list.add(c);
          }
          return list;
     }
     
     public List transitionInterpreter()
     {
          List list = new LinkedList();
          ListIterator itr;
          List elementsList;
          Element element;
          List<Event> eventList = new LinkedList<Event>();
          elementsList = doc.getRootElement().getChildren("transition");
          itr = elementsList.listIterator();
          Event e;
          while(itr.hasNext())
          {
               element = (Element)itr.next();
               e = new Event();
               e.setId(element.getAttributeValue("id"));
               e.setX(Double.parseDouble(element.getAttributeValue("x")));
               e.setY(Double.parseDouble(element.getAttributeValue("y")));
               e.setType(element.getAttributeValue("type"));
               e.setDesc(element.getAttributeValue("desc"));
               e.setDescOffx(Double.parseDouble(element.getAttributeValue("offx")));
               e.setDescOffy(Double.parseDouble(element.getAttributeValue("offy")));
               List children = element.getChildren();
               ListIterator itr2 = children.listIterator();
               while(itr2.hasNext())
               {
                    element = (Element)itr2.next();
                    if(element.getName().equals("delay"))
                    {
                         e.setInterval(Double.parseDouble(element.getAttributeValue("interval")));
                         e.setIntervalOffx(Double.parseDouble(element.getAttributeValue("offx")));
                         e.setIntervalOffy(Double.parseDouble(element.getAttributeValue("offy")));
                    }
                    else if(element.getName().equals("input"))
                    {
                         e.setInputDesc(element.getAttributeValue("desc"));
                         e.setInputDescOffx(Double.parseDouble(element.getAttributeValue("offx")));
                         e.setInputDescOffy(Double.parseDouble(element.getAttributeValue("offy")));
                    }
                    else if(element.getName().equals("code"))
                    {
                         List children2 = element.getChildren("statement");
                         ListIterator itr3 = children2.listIterator();
                         while(itr3.hasNext())
                         {
                              Element element2 = (Element)itr3.next();
                              e.insertAtOperatorsList(element2.getChild("operator").getText());
                              e.insertAtInputSignalsList(element2.getChild("signal").getText());
                              e.insertAtCommentsList(element2.getChild("comment").getText());            
                         }
                    }
               }
               eventList.add(e);
          }// fecha while externo.
          // associa o arco a transição correspondente.
          elementsList = doc.getRootElement().getChildren("arc");
          itr = elementsList.listIterator();
          List<Condition> placeList = placeInterpreter();
          Arc a;
          while(itr.hasNext())
          {
               element = (Element)itr.next();
               a = new Arc();
               // lê e armazena os dados referentes a cada arco.
               a.setId(element.getAttributeValue("id"));
               a.setX(Double.parseDouble(element.getAttributeValue("x")));
               a.setY(Double.parseDouble(element.getAttributeValue("y")));
               a.setSource(element.getAttributeValue("source"));
               a.setTarget(element.getAttributeValue("target"));
               a.setType(element.getAttributeValue("type"));
               // para cada transição, procura-se por seus pré e pós-lugares.
               for(int j = 0; j < eventList.size(); j++)
               {
                    if(eventList.get(j).getId().equals(a.getSource()) || eventList.get(j).getId().equals(a.getTarget()))
                    {
                         for(int k = 0; k < placeList.size(); k++)
                         {
                              if(placeList.get(k).getId().equals(a.getSource()))
                                   eventList.get(j).insertAtPreList(placeList.get(k));
                              else if(placeList.get(k).getId().equals(a.getTarget()))
                                   eventList.get(j).insertAtPosList(placeList.get(k));
                         }
                    }
                    if(!list.contains(eventList.get(j)))
                         list.add(eventList.get(j));
               }
          }
          return list;
     }
     
     //testing... .
//	public static void main(String args[])
//	{
//		try
//		{
//			File file = new File("C:\\Java\\TCC\\saida.txt");
//			FileWriter fileWriter = new FileWriter(file);
//			PrintWriter printWriter = new PrintWriter(fileWriter);
//			XMLProcessor processor = new XMLProcessor("C:\\Java\\TCC\\V106.xml");
//			List placeList = processor.XMLInterpreter("place");
//			List transitionList = processor.XMLInterpreter("transition");
//			List iosList = processor.XMLInterpreter("signals");
//
//			ListIterator itr = placeList.listIterator();
//			Condition c;
//			while(itr.hasNext())
//			{
//				c = (Condition)itr.next();
//				printWriter.println("Atributo id do place: " + c.getId());
//				printWriter.println("Atributo x do place: " + c.getX());
//				printWriter.println("Atributo y do place: " + c.getY());
//				printWriter.println("Atributo desc do place: " + c.getDesc());
//				printWriter.println("Atributo offx do place: " + c.getDescOffx());
//				printWriter.println("Atributo offy do place: " + c.getDescOffy());
//				printWriter.println("Atributo marking do place: " + c.isMarked());
//				printWriter.println("Atributo markingOffx do place: " + c.getMarkingOffx());
//				printWriter.println("Atributo markingOffy do place: " + c.getMarkingOffy());
//				printWriter.println("Atributo outputDesc do place: " + c.getOutputDesc());
//				printWriter.println("Atributo outputDescOffx do place: " + c.getOutputDescOffx());
//				printWriter.println("Atributo outputDescOffy do place: " + c.getOutputDescOffy());
//				//printWriter.println("Atributo ILCode do place: " + c.getILCode());
//				printWriter.println();
//			}
//			itr = transitionList.listIterator();
//			Event e;
//			while(itr.hasNext())
//			{
//				e = (Event)itr.next();
//				printWriter.println("Atributo id da transition: " + e.getId());
//				printWriter.println("Atributo x da transition: " + e.getX());
//				printWriter.println("Atributo y da transition: " + e.getY());
//				printWriter.println("Atributo desc da transition: " + e.getDesc());
//				printWriter.println("Atributo offx da transition: " + e.getDescOffx());
//				printWriter.println("Atributo offy da transition: " + e.getDescOffy());
//				printWriter.println("Atributo firingConditionDesc da transition: " + e.getInputDesc());
//				printWriter.println("Atributo firingConditionDescOffx da transition: " + e.getInputDescOffx());
//				printWriter.println("Atributo firingConditionDescOffy da transition: " + e.getInputDescOffy());
//				printWriter.println("Atributo ILCode da transition: " + e.getILCode());
//				printWriter.println("Conditions POS da transition: " + e.getCountPosConditions());
//				printWriter.println("Conditions POS: " + e.getFromPosList(0).getId());
//				printWriter.println("Conditions PRE da transition: " + e.getCountPreConditions());
//				printWriter.println("Conditions PRE: " + e.getFromPreList(0).getId());
//				printWriter.println();
//			}
//			itr = iosList.listIterator();
//			IOSignals ios;
//			while(itr.hasNext())
//			{
//				ios = (IOSignals)itr.next();
//				printWriter.println("Atributo type da variável: " + ios.getType());
//				printWriter.println("Atributo id da variável: " + ios.getId());
//				printWriter.println("Atributo desc da variável: " + ios.getDesc());
//				printWriter.println();
//			}
//			printWriter.close();
//			fileWriter.close();
//		}
//		catch(IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
}// fecha class.
