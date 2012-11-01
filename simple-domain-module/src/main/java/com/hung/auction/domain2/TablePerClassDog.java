package com.hung.auction.domain2;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "TABLE_PER_CLASS_DOG")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TablePerClassDog extends TablePerClassAnimal implements Serializable {
    public TablePerClassDog() {}
    public TablePerClassDog(String name) {
        this.setName(name);
    }
}