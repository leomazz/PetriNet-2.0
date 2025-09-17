/**
 * IOSignals.java
 */
package tcc;

public class IOSignals
{
	private String type;
	private String id;
	private String desc;
	
	public IOSignals(String type, String id, String desc)
	{
		this.type = type;
		this.id = id;
		this.desc = desc;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getId()
	{
		return id;
	}
	
	public String getDesc()
	{
		return desc;
	}
}