/**
 * Condition.java
 */
package tcc;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe Condition: elemento da Rede de Petri.
 */
public class Condition extends PetriNetElement
{
     private List<String> outputSignalsList;
     private List<String> operatorsList;
     private List<String> commentsList;
     private boolean currentMarking;
     private boolean initialMarking;
     private double markingOffx;
     private double markingOffy;
     private String outputDesc;
     private double outputDescOffx;
     private double outputDescOffy;
     private static int count;
     
     public Condition()
     {
          //outputSignalsList = new LinkedList<IOSignals>();
          outputSignalsList = new LinkedList<String>();
          operatorsList = new LinkedList<String>();
          commentsList = new LinkedList<String>();
          count ++;
     }
     
     public boolean isMarked()
     {
          return currentMarking;
     }
     
     public void setMarking(boolean marking)
     {
          currentMarking = marking;
     }
     
     public void setInitialMarking(boolean marking)
     {
          initialMarking = currentMarking = marking;
     }
     
     public void setMarkingOffx(double markingOffx)
     {
          this.markingOffx = markingOffx;
     }
     
     public void setMarkingOffy(double markingOffy)
     {
          this.markingOffy = markingOffy;
     }
     
     public void setMarkingOffxy(double markingOffx, double markingOffy)
     {
          this.markingOffx = markingOffx; this.markingOffy = markingOffy;
     }
     
     public void setOutputDesc(String outputDesc)
     {
          this.outputDesc = outputDesc;
     }
     
     public void setOutputDescOffx(double outputDescOffx)
     {
          this.outputDescOffx = outputDescOffx;
     }
     
     public void setOutputDescOffy(double outputDescOffy)
     {
          this.outputDescOffy = outputDescOffy;
     }
     
     public void setOutputDescOffxy(double outputDescOffx, double outputDescOffy)
     {
          this.outputDescOffx = outputDescOffx; this.outputDescOffy = outputDescOffy;
     }
     
     public boolean getInitialMarking()
     {
          return initialMarking;
     }
     
     public double getMarkingOffx()
     {
          return markingOffx;
     }
     
     public double getMarkingOffy()
     {
          return markingOffy;
     }
     
     public double getOutputDescOffx()
     {
          return outputDescOffx;
     }
     
     public double getOutputDescOffy()
     {
          return outputDescOffy;
     }
     
     public String getOutputDesc()
     {
          return outputDesc;
     }
     
     public int getCount()
     {
          return count;
     }
     
     public void setOutputSignalsList(List<String> outputSignals)
     {
          this.outputSignalsList = outputSignals;
     }
     
     public List<String> getOutputSignalsList()
     {
          return outputSignalsList;
     }
     
     public void insertAtOutputSignalsList(String signal)
     {
          outputSignalsList.add(signal);
     }
     
     public String getFromOutputSignalsList(String signal)
     {
          int i = outputSignalsList.indexOf(signal);
          return outputSignalsList.get(i);
     }
     
     public String getFromOutputSignalsList(int i)
     {
          return outputSignalsList.get(i);
     }
     
     public String toString()
     {
          return String.format("%s: \n%s\n%s: %s\n%s: %s\n%s: %d\n",
                    "Object Condition as a string has",
                    super.toString(),
                    "currentMarking", ( currentMarking == true ) ? "marked" : "not marked",
                    "initialMarking", ( initialMarking == true ) ? "marked" : "not marked",
                    "count", count);
     }
     
     public List<String> getOperatorsList()
     {
          return operatorsList;
     }
     
     public void setOperatorsList(List<String> operatorsList)
     {
          this.operatorsList = operatorsList;
     }
     
     public void insertAtOperatorsList(String operator)
     {
          operatorsList.add(operator);
     }
     
     public String getFromOperatorsList(String operator)
     {
          int i = operatorsList.indexOf(operator);
          return operatorsList.get(i);
     }
     
     public String getFromOperatorsList(int i)
     {
          return operatorsList.get(i);
     }
     
     public void insertAtCommentsList(String comment)
     {
          commentsList.add(comment);
     }
     
     public String getFromCommentsList(String comment)
     {
          int i = commentsList.indexOf(comment);
          return commentsList.get(i);
     }
     
     public String getFromCommentsList(int i)
     {
          return commentsList.get(i);
     }
}
