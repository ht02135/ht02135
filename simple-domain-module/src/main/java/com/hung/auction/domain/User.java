package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="AUCTION_USER")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="USER_TYPE", discriminatorType=DiscriminatorType.STRING, length=20)
public abstract class User implements Serializable {

	@Id
	@SequenceGenerator(name = "user_seq_gen", sequenceName = "user_sequence")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq_gen")
	@Column(name="USER_ID", nullable=false)
	private Integer id;
	
	@Column(name="USER_NAME", nullable=false)
	private String name;
	
	@Column(name="USER_EMAIL", nullable=false)
	private String email;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name="street", column=@Column(name="HOME_STREET")),
			@AttributeOverride(name="zipcode", column=@Column(name="HOME_ZIPCODE")),
			@AttributeOverride(name="city", column=@Column(name="HOME_CITY"))
	})
	private Address homeAddress;
	
    public User() {}
    
    public User(String name, Address homeAddress, String email) {
    	setName(name);
    	setHomeAddress(homeAddress);
    	setEmail(email);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Address getHomeAddress() { return homeAddress; }
    public void setHomeAddress(Address homeAddress) { this.homeAddress = homeAddress; }
    
    public String toString() {
    	return "[name="+name+",email="+email+",homeAddress="+homeAddress+"]";
    }
}