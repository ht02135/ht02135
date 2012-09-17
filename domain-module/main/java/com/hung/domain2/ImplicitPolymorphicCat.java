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
@Table(name = "IMPLICIT_POLYMORPHIC_CAT") // had to duplicate this annotation in subclasses otherwise default table name = class name
@AttributeOverride(name="name", column=@Column(name="IMPLICIT_POLYMORPHIC_CAT_NAME", nullable=false))
public class ImplicitPolymorphicCat extends ImplicitPolymorphicAnimal implements Serializable {

    // wanted to name implicit_ploymorphic_cat_sequence, but too long, shorten to implicit_cat_sequence
    @Id
    @SequenceGenerator(name = "implicit_cat_seq_gen", sequenceName = "implicit_cat_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "implicit_cat_seq_gen")
    @Column(name="IMPLICIT_POLYMORPHIC_CAT_ID", nullable=false)
    private Integer id;

    public ImplicitPolymorphicCat() {}
    public ImplicitPolymorphicCat(String name) {
        this.setName(name);
    }
}