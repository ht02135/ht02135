package com.hung.auction.domain2;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "IMPLICIT_POLYMORPHIC_DOG") // had to duplicate this annotation in subclasses otherwise default table name = class name
@AttributeOverride(name="name", column=@Column(name="IMPLICIT_POLYMORPHIC_DOG_NAME", nullable=false))
public class ImplicitPolymorphicDog extends ImplicitPolymorphicAnimal implements Serializable {

    // wanted to name implicit_ploymorphic_dog_sequence, but too long, shorten to implicit_dog_sequence
    @Id
    @SequenceGenerator(name = "implicit_dog_seq_gen", sequenceName = "implicit_dog_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "implicit_dog_seq_gen")
    @Column(name="IMPLICIT_POLYMORPHIC_DOG_ID", nullable=false)
    private Integer id;

    public ImplicitPolymorphicDog() {}
    public ImplicitPolymorphicDog(String name) {
        this.setName(name);
    }
}