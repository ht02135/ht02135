package com.hung.auction.domain2;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "OWNER") // had to duplicate this annotation in subclasses otherwise default table name = class name
public class Owner implements Serializable {
    @Id
    @SequenceGenerator(name = "owner_seq_gen", sequenceName = "owner_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "owner_seq_gen")
    @Column(name="OWNER_ID", nullable=false)
    private Integer id;

    @Column(name="OWNER_NAME", nullable=false)
    private String name;

    ////////////// Table Per Hierarchy /////////////////////////////////////////////////////////

    @OneToMany(mappedBy="owner")
    List<SingleTableAnimal> singleTableAnimals = null;

    ////////////// Joined Strategy (Table Per Subclass (regardless is abstract or non-abstract)

    @OneToMany(mappedBy="owner")
    List<JoinedAnimal> joinedAnimals = null;

    ////////////// Table Per Class with Implicit (indirect, not trivial) Polymorphism

    // SuperAnimal is @MappedSuperclass, entities cant have relationship to mapped superclass
    // entities can only have relationship to subclasses (SuperDog and SuperCat)
    @OneToMany(mappedBy="owner")
    List<ImplicitPolymorphicDog> implicitPloymorphicDogs = null;

    @OneToMany(mappedBy="owner")
    List<ImplicitPolymorphicCat> implicitPloymorphicCats = null;

    ////////////// Table Per Class with Union ////////////////////////////////////////////////////

    @OneToMany(mappedBy="owner")
    List<TablePerClassAnimal> tablePerClassAnimals = null;

    /////////////////////////////////////////////////////////////////////////////////////////////

    public Owner() {}
    public Owner(String name) {
        this.setName(name);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    ////////////// Table Per Hierarchy /////////////////////////////////////////////////////////

    public List<SingleTableAnimal> getSingleTableAnimals() { return singleTableAnimals; }
    public void setSingleTableAnimals(List<SingleTableAnimal> singleTableAnimals) { this.singleTableAnimals = singleTableAnimals; }

    ////////////// Joined Strategy (Table Per Subclass (table will be created per each abstrct/non-abstract class))

    public List<JoinedAnimal> getJoinedAnimals() { return joinedAnimals; }
    public void setJoinedAnimals(List<JoinedAnimal> joinedAnimals) { this.joinedAnimals = joinedAnimals; }

    ////////////// Table Per Class with Implicit (indirect, not trivial) Polymorphism

    // SuperAnimal is @MappedSuperclass, entities cant have relationship to mapped superclass
    // entities can only have relationship to subclasses

    public List<ImplicitPolymorphicCat> getImplicitPolymorphicCats() { return implicitPloymorphicCats; }
    public void setImplicitPolymorphicCat(List<ImplicitPolymorphicCat> implicitPloymorphicCats) { this.implicitPloymorphicCats = implicitPloymorphicCats; }

    public List<ImplicitPolymorphicDog> getImplicitPolymorphicDogs() { return implicitPloymorphicDogs; }
    public void setImplicitPolymorphicDogs(List<ImplicitPolymorphicDog> implicitPloymorphicDogs) { this.implicitPloymorphicDogs = implicitPloymorphicDogs; }

    ////////////// Table Per Class with Union ////////////////////////////////////////////////////

    public List<TablePerClassAnimal> getTablePerClassAnimals() { return tablePerClassAnimals; }
    public void setTablePerClassAnimals(List<TablePerClassAnimal> tablePerClassAnimals) { this.tablePerClassAnimals = tablePerClassAnimals; }

}