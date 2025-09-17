/**
 * Arc.java
 */
package tcc;

public class Arc extends PetriNetElement
{
	//private Condition connectedCondition;
	//private Event connectedEvent;
	private String source;
	private String target;
	private String type;
	private static int count;
	
	public Arc()
	{
		count ++;
	}
	
//	public void setConnectedCondition(Condition connectedCondition)
//	{
//		this.connectedCondition = connectedCondition;
//	}
//	
//	public Condition getConnectedCondition()
//	{
//		return connectedCondition;
//	}
//	
//	public void setConnectedEvent(Event connectedEvent)
//	{
//		this.connectedEvent = connectedEvent;
//	}
//	
//	public Event getConnectedEvent()
//	{
//		return connectedEvent;
//	}
	
	public void setSource(String source)
	{
		this.source = source;
	}
	
	public String getSource()
	{
		return source;
	}
	
	public void setTarget(String target)
	{
		this.target = target;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getTarget()
	{
		return target;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public String toString()
	{
		return String.format("%s: \n%s\n%s: %s\n%s: %s\n%s: %s",
				 "Object Arc as a string has",
				 super.toString(),
				 "source", source,
				 "target", target,
				 "count", count);
	}
}
