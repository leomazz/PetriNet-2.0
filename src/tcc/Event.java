/**
 * Event.java
 */
package tcc;

import java.util.List;
import java.util.LinkedList;

public class Event extends PetriNetElement
{
     private List<Condition> preConditionList;
     private List<Condition> posConditionList;
     private List<String> inputSignalsList;
     private List<String> operatorsList;
     private List<String> commentsList;
     private int countPreConditions;
     private int countPosConditions;
     private double delay;
     private double delayOffx;
     private double delayOffy;
     private String type;
     private double interval;
     private double intervalOffx;
     private double intervalOffy;
     private String inputDesc;
     private double inputDescOffx;
     private double inputDescOffy;
     private static int count;
     
     public Event()
     {
          preConditionList = new LinkedList<Condition>();
          posConditionList = new LinkedList<Condition>();
          inputSignalsList = new LinkedList<String>();
          operatorsList = new LinkedList<String>();
          commentsList = new LinkedList<String>();
          count ++;
     }
     
     public void setDelay(double delay)
     {
          this.delay = (delay > 0.0) ? delay : 0.0;
     }
     
     public void setDelayOffx(double delayOffx)
     {
          this.delayOffx = delayOffx;
     }
     
     public void setDelayOffy(double delayOffy)
     {
          this.delayOffy = delayOffy;
     }
     
     public void setType(String type)
     {
          this.type = type;
     }
     
     public void setInterval(double interval)
     {
          this.interval = interval;
     }
     
     public void setIntervalOffx(double x)
     {
          intervalOffx = x;
     }
     
     public void setIntervalOffy(double y)
     {
          intervalOffy = y;
     }
     
     public void setInputDesc(String inputDesc)
     {
          this.inputDesc = inputDesc;
     }
     
     public void setInputDescOffx(double inputDescOffx)
     {
          this.inputDescOffx = inputDescOffx;
     }
     
     public void setInputDescOffy(double inputDescOffy)
     {
          this.inputDescOffy = inputDescOffy;
     }
     
     public double getDelay()
     {
          return delay;
     }
     
     public double getDelayOffy()
     {
          return delayOffy;
     }
     
     public String getType()
     {
          return type;
     }
     
     public double getDelayOffx()
     {
          return delayOffx;
     }
     
     public int getCountPreConditions()
     {
          return countPreConditions;
     }
     
     public int getCountPosConditions()
     {
          return countPosConditions;
     }
     
     public double getIntervalOffx()
     {
          return intervalOffx;
     }
     
     public double getIntervalOffy()
     {
          return intervalOffy;
     }
     
     public String getInputDesc()
     {
          return inputDesc;
     }
     
     public double getInputDescOffx()
     {
          return inputDescOffx;
     }
     
     public double getInputDescOffy()
     {
          return inputDescOffy;
     }
     
     public int getCount()
     {
          return count;
     }
     
     public void insertAtPreList(Condition c)
     {
          preConditionList.add(c);
          countPreConditions++;
     }
     
     public void insertAtPosList(Condition c)
     {
          posConditionList.add(c);
          countPosConditions++;
     }
     
     public Condition getFromPreList(Condition c)
     {
          int i = preConditionList.indexOf(c);
          return preConditionList.get(i);
     }
     
     public Condition getFromPreList(int i)
     {
          return preConditionList.get(i);
     }
     
     public Condition getFromPosList(Arc arc)
     {
          int i = posConditionList.indexOf(arc);
          return posConditionList.get(i);
     }
     
     public Condition getFromPosList(int i)
     {
          return posConditionList.get(i);
     }
     
     public String toString()
     {
          return String.format("%s: \n%s\n%s: %d\n%s: %d\n%s: %.2f\n%s: %d",
                    "Object Event as a string has",
                    super.toString(),
                    "countPreArcs", countPreConditions,
                    "countPosArcs", countPosConditions,
                    "delay", delay,
                    "count", count);
     }
     
     public void setPreConditionList(List<Condition> preConditionList)
     {
          this.preConditionList = preConditionList;
     }
     
     public void setPosConditionList(List<Condition> posConditionList)
     {
          this.posConditionList = posConditionList;
     }
     
     public List<Condition> getPosConditionList()
     {
          return posConditionList;
     }
     
     public List<Condition> getPreConditionList()
     {
          return preConditionList;
     }
     
     public void setInputSignalsList(List<String> inputSignalsList)
     {
          this.inputSignalsList = inputSignalsList;
     }
     
     public List<String> getInputSignalsList()
     {
          return inputSignalsList;
     }
     
     public void insertAtInputSignalsList(String signal)
     {
          inputSignalsList.add(signal);
     }
     
     public String getFromInputSignalsList(String signal)
     {
          int i = inputSignalsList.indexOf(signal);
          return inputSignalsList.get(i);
     }
     
     public String getFromInputSignalsList(int i)
     {
          return inputSignalsList.get(i);
     }
     
     public void setOperatorsList(List<String> operatorsList)
     {
          this.operatorsList = operatorsList;
     }
     
     public List<String> getOperatorsList()
     {
          return operatorsList;
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
