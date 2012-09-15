package com.hung.auction.domain;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

@Entity
@Table(name="DOCUMENT")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DOCUMENT_TYPE", discriminatorType=DiscriminatorType.STRING, length=20)
@DiscriminatorValue("BINARY")
public class BinaryDocument extends AbstractDocument implements Serializable {

    private static Logger log = Logger.getLogger(BinaryDocument.class);

    @Column(name="DOCUMENT_CONTENT", nullable=true)
    @Lob
    @Basic(fetch = FetchType.EAGER)
    protected byte[] content;

    public BinaryDocument() { super(); }

    public byte[] getContent() { return content; }
    public void setContent(byte[] content) { this.content = content; }

    public String toString() {
        return "[id="+id+",name="+name+",fileName="+fileName+",contentType="+contentType+"]";
    }

    public void uploadFrom(InputStream in) {
        try {
            setContent(IOUtils.toByteArray(in));
        } catch (IOException e) {
            log.info("uploadFrom: e="+e.toString());
        } catch(Exception e) {
            log.info("uploadFrom: e="+e.toString());
        }
    }

    @Override
    public void downloadTo(OutputStream out) {
        try {
            IOUtils.write(getContent(), out);
        } catch (IOException e) {
            log.info("downloadTo: e="+e.toString());
        } catch(Exception e) {
            log.info("downloadTo: e="+e.toString());
        }
    }
}