package com.hung.auction.domain2;

import javax.persistence.Column;
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
   This strategy is called Table Per Class with Unions
   1>Polymorphic association is possible with UNION

Hibernate:
    select
        tablepercl0_.TABLE_PER_CLASS_ANIMAL_ID as TABLE1_102_,
        tablepercl0_.TABLE_PER_CLASS_ANIMAL_NAME as TABLE2_102_,
        tablepercl0_.owner_OWNER_ID as owner3_102_,
        tablepercl0_.clazz_ as clazz_
    from
        ( select
            TABLE_PER_CLASS_ANIMAL_NAME,
            owner_OWNER_ID,
            TABLE_PER_CLASS_ANIMAL_ID,
            1 as clazz_
        from
            TABLE_PER_CLASS_CAT
        union
        all select
            TABLE_PER_CLASS_ANIMAL_NAME,
            owner_OWNER_ID,
            TABLE_PER_CLASS_ANIMAL_ID,
            2 as clazz_
        from
            TABLE_PER_CLASS_DOG
    ) tablepercl0_

    ********************************************************************************
    this strategy (Union) VS Joined Strategy.  which is better?
    ********************************************************************************
    Hung:
    1>polymorphic query and relation can be achieved by union of all subclass query
    2>this strategy (not normalized) VS Joined Strategy (normalized).  so if not normalized,
      then is this better than Table per Hierarchy?
    3>Union implies multiple subclass query (select).  how is Union VS Join?

    Someone mentioned that this is not recommended for MOST CASE
*/

// Table Per Class
@Entity
@Table(name = "TABLE_PER_CLASS_ANIMAL") // verified no table is created for abstract class for InheritanceType.TABLE_PER_CLASS
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TablePerClassAnimal {

    // wanted to name table_per_class_animal_sequence, but too long, shorten to per_class_animal_sequence
    @Id
    @SequenceGenerator(name = "per_class_animal_seq_gen", sequenceName = "per_class_animal_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "per_class_animal_seq_gen")
    @Column(name="TABLE_PER_CLASS_ANIMAL_ID", nullable=false)
    private Integer id;

    @Column(name="TABLE_PER_CLASS_ANIMAL_NAME", nullable=false)
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