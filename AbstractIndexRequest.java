package com.hung.auction.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.hung.auction.entity.SuperDog;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "INDEX_REQUEST") // had to duplicate this annotation in subclasses otherwise default table name = class name
public abstract class AbstractIndexRequest implements Serializable {

    @Id
    @SequenceGenerator(name = "indexrequest_seq_gen", sequenceName = "indexrequest_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "indexrequest_seq_gen")
    @Column(name="REQUEST_ID", nullable=false)
    protected Integer id;

    //Getter and Setter methods

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}