package com.hung.auction.domain2;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "TABLE_PER_CLASS_CAT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TablePerClassCat extends TablePerClassAnimal implements Serializable {
    public TablePerClassCat() {}
    public TablePerClassCat(String name) {
        this.setName(name);
    }
}