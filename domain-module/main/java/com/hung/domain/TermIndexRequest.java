package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TERM_INDEX_REQUEST") // had to duplicate this annotation in subclasses otherwise default table name = class name
@PrimaryKeyJoinColumn(name="REQUEST_ID")
public class TermIndexRequest extends AbstractIndexRequest implements Serializable {

    @OneToOne
    @JoinColumn(name="REQUEST_TERM_ID")
    private Term term;

    public TermIndexRequest() {
    }

    public TermIndexRequest(Term term) {
        setTerm(term);
    }

    // ----------------------------------
    //Getter and Setter methods

    public Term getTerm() { return term; }
    public void setTerm(Term term) { this.term = term; }

    public String toString() {
        return "[id="+id+",status="+status+",term="+term+"]";
    }
}