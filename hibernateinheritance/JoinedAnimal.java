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
2.12.2 Joined Subclass Strategy
It has the drawback that it requires that one or more join operations be performed to instantiate
instances of a subclass (for polymorphism). In deep class hierarchies, this may lead to unacceptable performance.
Queries that range over the class hierarchy likewise require joins.

Hibernate:
    select
        joinedanim0_.JOINED_ANIMAL_ID as JOINED1_98_,
        joinedanim0_.JOINED_ANIMAL_NAME as JOINED2_98_,
        joinedanim0_.owner_OWNER_ID as owner3_98_,
        case
            when joinedanim0_1_.JOINED_ANIMAL_ID is not null then 1
            when joinedanim0_2_.JOINED_DOG_ID is not null then 2
            when joinedanim0_.JOINED_ANIMAL_ID is not null then 0
        end as clazz_
    from
        JOINED_ANIMAL joinedanim0_
    left outer join
        JOINED_CAT joinedanim0_1_
            on joinedanim0_.JOINED_ANIMAL_ID=joinedanim0_1_.JOINED_ANIMAL_ID
    left outer join
        JOINED_DOG joinedanim0_2_
            on joinedanim0_.JOINED_ANIMAL_ID=joinedanim0_2_.JOINED_DOG_ID

    Hung:
    1>Polymorphic query and relation requires join all tables (table per abstract/non-abstract class)
    2>this strategy is normalized table.  only quesiton is performance of Join vs other strategy.
*/

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "JOINED_ANIMAL") // verified table is created for abstract class for InheritanceType.JOINED
public abstract class JoinedAnimal {

    @Id
    @SequenceGenerator(name = "joined_animal_seq_gen", sequenceName = "joined_animal_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "joined_animal_seq_gen")
    @Column(name="JOINED_ANIMAL_ID", nullable=false)
    private Integer id;

    @Column(name="JOINED_ANIMAL_NAME", nullable=false)
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