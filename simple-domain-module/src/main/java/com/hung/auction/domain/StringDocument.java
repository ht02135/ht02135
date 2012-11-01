package com.hung.auction.domain;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;

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
@DiscriminatorValue("STRING")
public class StringDocument extends AbstractDocument implements Serializable {

    private static Logger log = Logger.getLogger(StringDocument.class);

    @Column(name="DOCUMENT_STRCONTENT", nullable=true)
    @Lob
    @Basic(fetch = FetchType.EAGER)
    protected String strContent;

    public StringDocument() { super(); }

    public String getStrContent() { return strContent; }
    public void setStrContent(String strContent) { this.strContent = strContent; }

    public String toString() {
        // return "[id="+id+",name="+name+",fileName="+fileName+",contentType="+contentType+",strContent="+strContent+"]";
        return "[id="+id+",name="+name+",fileName="+fileName+",contentType="+contentType+"]";
    }

    @Override
    public void uploadFrom(InputStream in) {
        try {
            // use IOUtils: InputStream -> StringWriter -> String
            StringWriter writer = new StringWriter();
            IOUtils.copy(in, writer);
            this.setStrContent(writer.toString());
        } catch (IOException e) {
            log.info("uploadFrom: e="+e.toString());
        } catch(Exception e) {
            log.info("uploadFrom: e="+e.toString());
        }
    }

    @Override
    public void downloadTo(OutputStream out) {
        // TODO Auto-generated method stub

        try {
            // use IOUtils: StringReader(String) -> OutputStream
            StringReader reader = new StringReader(strContent);
            IOUtils.copy(reader, out);
        } catch (IOException e) {
            log.info("uploadFrom: e="+e.toString());
        } catch(Exception e) {
            log.info("uploadFrom: e="+e.toString());
        }
    }
}
