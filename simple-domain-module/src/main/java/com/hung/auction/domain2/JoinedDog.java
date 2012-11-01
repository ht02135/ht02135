package com.hung.auction.domain2;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "JOINED_DOG") // had to duplicate this annotation in subclasses otherwise default table name = class name
@PrimaryKeyJoinColumn(name="JOINED_DOG_ID")
public class JoinedDog extends JoinedAnimal implements Serializable {
    public JoinedDog() {}
    public JoinedDog(String name) {
        this.setName(name);
    }
}