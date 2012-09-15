package com.hung.auction.domain;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

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

@Entity
@Table(name="DOCUMENT")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DOCUMENT_TYPE", discriminatorType=DiscriminatorType.STRING, length=20)
public abstract class AbstractDocument implements Serializable {

    @Id
    @SequenceGenerator(name = "document_seq_gen", sequenceName = "document_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "document_seq_gen")
    @Column(name="DOCUMENT_ID", nullable=false)
    protected Integer id;

    @Column(name="DOCUMENT_NAME", nullable=false)
    protected String name;

    @Column(name="DOCUMENT_FILENAME", nullable=false)
    protected String fileName;

    @Column(name="DOCUMENT_CONTENTTYPE", nullable=false)
    protected String contentType;

    //Getter and Setter methods

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    // ----------- abstract methods --------------------

    abstract public void uploadFrom(InputStream in);

    abstract public void downloadTo(OutputStream out);

}