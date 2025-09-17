/**
 * PetriNetElement.java
 */
package tcc;

public class PetriNetElement
{
	private String id;
	private String desc;
	double x;
	double y;
	private double descOffx;
	private double descOffy;
	
	public PetriNetElement()
	{}
	
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	
	public void setDescOffx(double descOffx)
	{
		this.descOffx = descOffx;
	}
	
	public void setDescOffy(double descOffy)
	{
		this.descOffy = descOffy;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public void setXY(double x, double y)
	{
		this.x = x; this.y = y;
	}
	
	public String getDesc()
	{
		return desc;
	}
	
	public double getDescOffx()
	{
		return descOffx;
	}
	
	public double getDescOffy()
	{
		return descOffy;
	}
	
	public String getId()
	{
		return id;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public String toString()
	{
		return String.format("%s: %s\n%s: %s",
				 "id", id,
				 "desc", desc);
	}
}
