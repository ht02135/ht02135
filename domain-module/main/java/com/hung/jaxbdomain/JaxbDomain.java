package com.hung.auction.jaxbdomain;

import java.io.Serializable;
import java.util.Comparator;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

import com.hung.auction.domain.Domain;

@XmlRootElement(name="JaxbDomain")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class JaxbDomain implements Comparable, Serializable  {
	
	private static Logger log = Logger.getLogger(JaxbDomain.class);

	public static final String ROOT_NAME = "root"; 
	
	public static final Comparator PARENT_NAME_COMPARATOR = new JaxvDomainParentNameComparator();

	@XmlAttribute(name="name")
	private String name = "";
	
	@XmlElement(name="parentDomainName")
	private String parentDomainName = "";
	
    public JaxbDomain() {}
    
    public JaxbDomain(String name, String parentDomainName) {
    	setName(name);
    	setParentDomainName(parentDomainName);
    }
    
    public JaxbDomain(Domain domain) {
    	setName(domain.getName());
    	if (domain.getParentDomain() != null) {
    		setParentDomainName(domain.getParentDomain().getName());
    	}
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getParentDomainName() { return parentDomainName; }
    public void setParentDomainName(String parentDomainName) { this.parentDomainName = parentDomainName; }
	
    public String toString() {
    	return "[name="+name+",parentDomainName="+parentDomainName+"]";
    }
    
    // ------- Comparable interface-----
    
	public int compareTo(Object o) {
		log.info("compareTo called");
		
		JaxbDomain domain = (JaxbDomain) o;
        return this.name.compareTo(domain.getName());
	}
	
	@Override
    public boolean equals(Object obj) {
    	log.info("equals: called, this.toString()="+this.toString()+" obj.toString()="+obj.toString());
    	
        if (obj == null) {
        	log.info("equals: obj == null");
        	return false;	// null check
        }
        if (this == obj) { 
        	log.info("equals: this == obj");
        	return true;	// reference check (reflexive, symmetric, transitive => true)
        }

        // Some people would prefer 'if (!(o instanceof ClassName)) ...'
        if (getClass() != obj.getClass()) {
        	log.info("equals: getClass() != obj.getClass()");
        	return false;
        }
        
        // field comparison
        Domain otherObj = (Domain) obj;
    	return new EqualsBuilder()
    		.append(this.name, otherObj.getName())	// name is the key field
    		.isEquals();
    }
    
    @Override
    public int hashCode(){
    	log.info("hashCode: called, this.toString()="+this.toString());
    	
    	int hasCode = new HashCodeBuilder()
    		.append(this.name)	// name is the key field
    		.toHashCode();
    	log.info("hashCode: hasCode="+hasCode);
    	
        return hasCode;
    }
    
    // ------ nested class : comparator
    
    // parentDomain name
    private static class JaxvDomainParentNameComparator implements Comparator, Serializable {
    	
    	private static Logger log = Logger.getLogger(JaxvDomainParentNameComparator.class);

		@Override
		public int compare(Object o1, Object o2) {
			log.info("compare called");
			
			JaxbDomain domain1 = (JaxbDomain) o1;
			JaxbDomain domain2 = (JaxbDomain) o2;
			
			String parentName1 = (domain1.getParentDomainName() != null) ? domain1.getParentDomainName() : "";
			String parentName2 = (domain2.getParentDomainName() != null) ? domain2.getParentDomainName() : "";
			
	        return parentName1.compareTo(parentName2);
		}
    }
}