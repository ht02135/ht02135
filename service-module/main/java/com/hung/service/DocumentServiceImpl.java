package com.hung.auction.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.dao.DocumentDAO;
import com.hung.auction.domain.AbstractDocument;
import com.hung.auction.domain.StringDocument;

@Service("documentService")
public class DocumentServiceImpl implements DocumentService {

    private static Logger log = Logger.getLogger(DocumentServiceImpl.class);

    @Autowired
    @Qualifier("documentDAO")
    private DocumentDAO documentDAO;

    @Autowired
    @Qualifier("buildIndexService")
    private BuildIndexService buildIndexService;

    public DocumentServiceImpl() {
    }

    public DocumentServiceImpl(DocumentDAO documentDAO) {
        setDocumentDAO(documentDAO);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void save(AbstractDocument document) {
        log.info("save: document="+document);
        documentDAO.save(document);
        if (document instanceof StringDocument) {
            log.info("save: before buildIndexForDocument");
            buildIndexService.buildIndexForDocument((StringDocument) document);
        }
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public AbstractDocument findById(Integer id) {
        log.info("findById: id="+id);
        AbstractDocument abstractDocument =  documentDAO.findById(id);
        return abstractDocument;
    }

    public List<StringDocument> findByTerm(String term) {
        return documentDAO.findByTerm(term);
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<AbstractDocument> findAll() {
        return documentDAO.findAll();
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<StringDocument> findStringDocuments() {
        return documentDAO.findStringDocuments();
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void deleteById(Integer id) {
        log.info("deleteById: id="+id);

        AbstractDocument document = findById(id);
        if (document instanceof StringDocument) {
            log.info("save: before deleteIndexForDocument");
            buildIndexService.deleteIndexForDocument((StringDocument) document);
        }
        documentDAO.deleteById(document.getId());
    }

    // injection methods

    public void setDocumentDAO(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

}
