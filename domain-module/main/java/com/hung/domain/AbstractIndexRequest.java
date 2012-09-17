package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*
2.12.2 Joined Subclass Strategy
It has the drawback that it requires that one or more join operations be performed to instantiate
instances of a subclass (for polymorphism). In deep class hierarchies, this may lead to unacceptable performance.
Queries that range over the class hierarchy likewise require joins.
*/

/*
   http://openjpa.apache.org/builds/1.0.4/apache-openjpa-1.0.4/docs/manual/jpa_overview_mapping_inher.html#jpa_overview_mapping_inher_joined_adv

   Advantages:
   1>Using joined subclass tables results in the most normalized database schema, meaning the schema with the least spurious
     or redundant data.
   2>As more subclasses are added to the data model over time, the only schema modification that needs to be made is the addition
     of corresponding subclass tables in the database (rather than having to change the structure of existing tables).
   3>Relations to a base class using this strategy can be loaded through standard joins and can use standard foreign keys, as
     opposed to the machinations required to load polymorphic relations to table-per-class base types, described below.

   Disadvantages:
   1>Aside from certain uses of the table-per-class strategy described below, the joined strategy is often the slowest of the
   inheritance models. Retrieving any subclass requires one or more database joins, and storing subclasses requires multiple
   INSERT or UPDATE statements. This is only the case when persistence operations are performed on subclasses; if most operations
   are performed on the least-derived persistent superclass, then this mapping is very fast.

   Hung:
   1>so best avoid this if you have many subclasses and polymorphic query often
   2>so use it if few subclasses and not polymorphic query often
   3>but how often you dont do (findById, deleteById, findAll) and polymorphic relations?  the answer is VERY FEW.  the only thing
     you can rely on is hope subclasses not gonna grow.  or you run into inefficient query and relations...
   4>of course, you can minimize polymorphic query and relations by doing mostly subclass query (like replace findAll with tons of findSubClassInstance)
     and relations (so no join) or List<SubClass> subClassInstances relation.  but that mean update those code when you have new
     subClasses.  and yet you will still have to use say findById and deleteById from time to time...
*/

/*
    @MappedSuperclass
    1>cant define a table, but can define mapping for its attributes
    2>entities cant have relationship to mapped superclass
    3>mapped superclasses cant be queried

    Hung:
    1>I found example of using @MappedSuperclass with Joined Subclass Strategy to kill polymorphic query and relations
    2>played around with it, i cant really figure out spirit of this in entity modeling.  why would anyone wants to kill
      polymorphic query and relations in its entity modeling is beyond me.  STAY AWAY from @MappedSuperclass
*/

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "INDEX_REQUEST") // had to duplicate this annotation in subclasses otherwise default table name = class name
public abstract class AbstractIndexRequest implements Serializable {

    @Id
    @SequenceGenerator(name = "indexrequest_seq_gen", sequenceName = "indexrequest_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "indexrequest_seq_gen")
    @Column(name="REQUEST_ID", nullable=false)
    protected Integer id;

    // ----------------------------------
    //Getter and Setter methods

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}