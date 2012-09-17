package com.hung.auction.domain2;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*
    2.12.1 Single Table per Class Hierarchy Strategy
    This mapping strategy provides good support for polymorphic relationships between entities and for queries
    that range over the class hierarchy. It has the drawback, however, that it requires that the columns that
    correspond to state specific to the subclasses be nullable.

    select
        singletabl0_.SINGLE_TABLE_ANIMAL_ID as SINGLE2_101_,
        singletabl0_.SINGLE_TABLE_ANIMAL_NAME as SINGLE3_101_,
        singletabl0_.owner_OWNER_ID as owner4_101_,
        singletabl0_.SINGLE_TABLE_ANIMAL_TYPE as SINGLE1_101_
    from
        SINGLE_TABLE_ANIMAL singletabl0_

    Hung:
    1>efficient polymorphic query and relation
    2>my default choice?
*/

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "SINGLE_TABLE_ANIMAL") // had to duplicate this annotation in subclasses otherwise default table name = class name
@DiscriminatorColumn(name = "SINGLE_TABLE_ANIMAL_TYPE", discriminatorType = DiscriminatorType.STRING) // had to duplicate this annotation in subclasses otherwise no discriminator column was generated by Hibernate Tools SchemaExport
public abstract class SingleTableAnimal {
    @Id
    @SequenceGenerator(name = "single_table_animal_seq_gen", sequenceName = "single_table_animal_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "single_table_animal_seq_gen")
    @Column(name="SINGLE_TABLE_ANIMAL_ID", nullable=false)
    private Integer id;

    @Column(name="SINGLE_TABLE_ANIMAL_NAME", nullable=false)
    private String name;

    @ManyToOne
    private Owner owner = null;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Owner getOwner() { return owner; }
    public void setOwner(Owner owner) { this.owner = owner; }
}