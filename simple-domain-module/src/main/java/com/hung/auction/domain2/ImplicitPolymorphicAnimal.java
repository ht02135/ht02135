package com.hung.auction.domain2;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

/*
   This strategy is called Table Per Concrete Class with implicit polymorphism (indirect polymorphism)
   1>need to duplicate properties of the super-class to map them to all concrete class table
   2>to do that, you need to annotate super-class @MappedSuperclass
   3>@MappedSuperclass has following attribute
     a>cant define a table, but can define mapping for its attributes
     b>entities cant have relationship to mapped superclass
     c>mapped superclasses cant be queried

   ********************************************************************************
   this strategy feels like a real PAIN compare to Table Per Class with Union stratey
   ********************************************************************************
   Hung:
   1>you can do implicit (indirect) polymorphism by union all subclass query.  but you need to build that SQL
     in your dao.
   2>hibernate doesnt seem to support UNION in HQL or Criteria.  you probably have to use UNION like (copied
     from Table Per Class with Union strategy)

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
*/

// Table Per Concrete Class
@MappedSuperclass
public abstract class ImplicitPolymorphicAnimal {

    @Column(name="IMPLICIT_POLYMORPHIC_ANIMAL_NAME", nullable=false)
    private String name;

    @ManyToOne
    private Owner owner = null;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Owner getOwner() { return owner; }
    public void setOwner(Owner owner) { this.owner = owner; }
}