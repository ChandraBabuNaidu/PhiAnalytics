package proj.Base;

public class Pojo {
	private String property;
	private String propertyType;
	private String expectedmessage;
	
	
	
	public Pojo( String Property, String propertyType ){
		this.property=Property;
		this.propertyType=propertyType;
	}
	public Pojo( String property, String propertyType,String expectedmessage ){
		this.property= property;
		this.propertyType=propertyType;
		this.expectedmessage=expectedmessage;
	}
	
	
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getExpectedmessage() {
		return expectedmessage;
	}

	public void setExpectedmessage(String expectedmessage) {
		this.expectedmessage = expectedmessage;
	}
	
	

}
