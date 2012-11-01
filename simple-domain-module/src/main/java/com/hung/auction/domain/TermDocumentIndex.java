package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@Entity
@Table(name="TERM_DOCUMENT_INDEX")
@org.hibernate.annotations.Table(appliesTo="TERM_DOCUMENT_INDEX", indexes={@Index(name="termDocumentIndexIndex", columnNames={"INDEX_ID", "INDEX_TERM_ID", "INDEX_DOCUMENT_ID"})})
public class TermDocumentIndex implements Serializable {

    @Id
    @SequenceGenerator(name = "termdocumentindex_seq_gen", sequenceName = "termdocumentindex_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "termdocumentindex_seq_gen")
    @Column(name="INDEX_ID", nullable=false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="INDEX_TERM_ID")
    private Term term;

    @ManyToOne
    @JoinColumn(name="INDEX_DOCUMENT_ID")
    private StringDocument document;

    public TermDocumentIndex() {}

    public TermDocumentIndex(Term term, StringDocument document) {
        setTerm(term);
        setDocument(document);
    }

    // ----------------------------------
    //Getter and Setter methods

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Term getTerm() { return term; }
    public void setTerm(Term term) { this.term = term; }

    public StringDocument getDocument() { return document; }
    public void setDocument(StringDocument document) { this.document = document; }

    public String toString() {
        return "[id="+id+",term="+term+",document="+document+"]";
    }
}