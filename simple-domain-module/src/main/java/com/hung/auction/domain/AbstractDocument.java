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

/*
 12:30:00,535 ERROR org.hibernate.tool.hbm2ddl.SchemaExport - Unsuccessful: create index indexedColumns on DOCUMENT (DOCUMENT_ID, DOCUMENT_STRCONTENT)
 12:30:00,535 ERROR org.hibernate.tool.hbm2ddl.SchemaExport - ORA-02327: cannot create index on expression with datatype LOB
 @org.hibernate.annotations.Table(appliesTo="DOCUMENT", indexes={@Index(name="documentIndex", columnNames={"DOCUMENT_ID", "DOCUMENT_STRCONTENT"})})
*/

/*
2.12.1 Single Table per Class Hierarchy Strategy
This mapping strategy provides good support for polymorphic relationships between entities and for queries
that range over the class hierarchy. It has the drawback, however, that it requires that the columns that
correspond to state specific to the subclasses be nullable.
*/

/*
   http://openjpa.apache.org/builds/1.0.4/apache-openjpa-1.0.4/docs/manual/jpa_overview_mapping_inher.html#jpa_overview_mapping_inher_joined_adv

   Advantages:
   1>Single table inheritance mapping is the fastest of all inheritance models, since it never requires a join to retrieve a persistent
     instance from the database. Similarly, persisting or updating a persistent instance requires only a single INSERT or UPDATE statement.
   2>Finally, relations to any class within a single table inheritance hierarchy are just as efficient as relations to a base class.

   Disadvantages
   1>The larger the inheritance model gets, the "wider" the mapped table gets, in that for every field in the entire inheritance hierarchy,
     a column must exist in the mapped table. This may have undesirable consequence on the database size, since a wide or deep inheritance
     hierarchy will result in tables with many mostly-empty columns.

   Hung:
   1>efficient polymorphic query and relations to any class.  why would anyone use other inheritance???
   2>all other inheritance run into efficiency issue with polymorphic query or relation.  and there is just alots of polymorphic query
     and relation in OO.  and since when subclasses of hierarchy stop growing?  so other inheritance just not flexible to deal with
     growth and common used feature like query and relations.  complex query and relations.
   3>this inheritance should be by default...
 */

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

    // ----------------------------------
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