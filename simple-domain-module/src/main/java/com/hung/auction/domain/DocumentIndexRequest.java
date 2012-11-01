package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "DOCUMENT_INDEX_REQUEST") // had to duplicate this annotation in subclasses otherwise default table name = class name
@PrimaryKeyJoinColumn(name="REQUEST_ID")
public class DocumentIndexRequest extends AbstractIndexRequest implements Serializable {

    public static final String ADD_DOCUMENT_INDEX = "add_document_index";
    public static final String DELETE_DOCUMENT_INDEX = "delete_document_index";

    @Column(name="REQUEST_DOCUMENT_ACTION")
    protected String documentAction;

    @OneToOne
    @JoinColumn(name="REQUEST_DOCUMENT_ID")
    private StringDocument document;

    public DocumentIndexRequest() {}

    public DocumentIndexRequest(StringDocument document, String documentAction) {
        setDocument(document);
        setDocumentAction(documentAction);
    }

    // ----------------------------------
    //Getter and Setter methods

    public String getDocumentAction() { return documentAction; }
    public void setDocumentAction(String documentAction) { this.documentAction = documentAction; }

    public StringDocument getDocument() { return document; }
    public void setDocument(StringDocument document) { this.document = document; }

    public String toString() {
        return "[id="+id+",status="+status+",document="+document+",documentAction="+documentAction+"]";
    }
}