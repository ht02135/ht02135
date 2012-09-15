package com.hung.auction.engine;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;

import com.hung.auction.domain.AbstractDocument;
import com.hung.auction.domain.AbstractIndexRequest;
import com.hung.auction.domain.BinaryDocument;
import com.hung.auction.domain.DocumentIndexRequest;
import com.hung.auction.domain.StringDocument;
import com.hung.auction.domain.Term;
import com.hung.auction.domain.TermDocumentIndex;
import com.hung.auction.domain.TermIndexRequest;
import com.hung.auction.service.DocumentService;
import com.hung.auction.service.IndexRequestService;
import com.hung.auction.service.TermDocumentIndexService;
import com.hung.auction.service.TermService;

public class BuildIndexEngineImpl implements BuildIndexEngine {

    private static Logger log = Logger.getLogger(BuildIndexEngineImpl.class);

    @Autowired
    @Qualifier("termService")
    private TermService termService;

    @Autowired
    @Qualifier("documentService")
    private DocumentService documentService;

    @Autowired
    @Qualifier("indexRequestService")
    private IndexRequestService indexRequestService;

    @Autowired
    @Qualifier("termDocumentIndexService")
    private TermDocumentIndexService termDocumentIndexService;

    private TaskExecutor taskExecutor;

    public TermIndexRequest createTermIndexRequest(String term) {
        Term newTerm = new Term(term);
        termService.save(newTerm);
        return createTermIndexRequest(newTerm);
    }

    public TermIndexRequest createTermIndexRequest(Term term) {
        TermIndexRequest indexRequest = new TermIndexRequest(term);
        indexRequestService.save(indexRequest);
        return indexRequest;
    }

    public DocumentIndexRequest createDocumentIndexRequest(StringDocument document, String documentAction) {
        DocumentIndexRequest indexRequest = new DocumentIndexRequest(document, documentAction);
        indexRequestService.save(indexRequest);
        return indexRequest;
    }

    public void handleTermIndexRequest(TermIndexRequest indexRequest) {
        log.info("handleTermIndexRequest: indexRequest="+indexRequest);

        // launch HandleCreateTermDocumentIndexTask
        taskExecutor.execute(new HandleCreateTermDocumentIndexTask(indexRequest));
    }

    public void handleTermIndexRequest(TermIndexRequest indexRequest, StringDocument document) {
        log.info("handleTermIndexRequest: indexRequest="+indexRequest+" document="+document);

        // launch HandleCreateTermDocumentIndexTask
        taskExecutor.execute(new HandleCreateTermDocumentIndexTask(indexRequest, document));
    }

    public void handleDocumentIndexRequest(DocumentIndexRequest indexRequest) {
        log.info("handleDocumentIndexRequest: indexRequest="+indexRequest);

        if (indexRequest.getDocumentAction().equalsIgnoreCase(DocumentIndexRequest.DELETE_DOCUMENT_INDEX)) {
            log.info("handleDocumentIndexRequest: DELETE_DOCUMENT_INDEX");

            // if is delete document index action, then search thru all index and then remove them
            List<TermDocumentIndex> termDocumentIndexes = termDocumentIndexService.findByDocumentId(indexRequest.getDocument().getId());

            for (int i=0;i<termDocumentIndexes.size();i++) {
                termDocumentIndexService.deleteById(termDocumentIndexes.get(i).getId());
            }
        } else if (indexRequest.getDocumentAction().equalsIgnoreCase(DocumentIndexRequest.ADD_DOCUMENT_INDEX)) {
            log.info("handleDocumentIndexRequest: ADD_DOCUMENT_INDEX");

            // get all terms from termService
            List<Term> terms = termService.findAll();
            log.info("handleDocumentIndexRequest: terms="+terms);

            // loop thru terms and launch HandleCreateTermDocumentIndexTask
            for (int i=0;i<terms.size();i++) {
                TermIndexRequest termIndexRequest = new TermIndexRequest(terms.get(i));
                indexRequestService.save(termIndexRequest);
                handleTermIndexRequest(termIndexRequest, indexRequest.getDocument());
            }
        }
        // remove document index request
        log.info("handleDocumentIndexRequest: delete indexRequest="+indexRequest);
        deleteIndexRequest(indexRequest);
    }

    // ------------------ Helper Method for Inner Class Task

    private void deleteIndexRequest(AbstractIndexRequest indexRequest) {
        indexRequestService.deleteById(indexRequest.getId());
    }

    private List<StringDocument> findStringDocuments() {
        return documentService.findStringDocuments();
    }

    private void saveTermDocumentIndex(TermDocumentIndex termDocumentIndex) {
        termDocumentIndexService.save(termDocumentIndex);
    }

    // ------------------ Injection Method ---------------------------------

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        log.info("*********************************************");
        log.info("setTaskExecutor: taskExecutor="+taskExecutor);
        log.info("*********************************************");
        this.taskExecutor = taskExecutor;
    }

    // ---------------- Inner Class: Tasks ----------------------------------------------

    private class HandleCreateTermDocumentIndexTask implements Runnable {

        private TermIndexRequest indexRequest;
        private StringDocument document;

        private HandleCreateTermDocumentIndexTask(TermIndexRequest indexRequest) {
            this.indexRequest = indexRequest;
        }

        private HandleCreateTermDocumentIndexTask(TermIndexRequest indexRequest, StringDocument document) {
            this.indexRequest = indexRequest;
            this.document = document;
        }

        private void createTermDocumentIndex(Term term, StringDocument document) {
            log.info("HandleCreateTermDocumentIndexTask.createTermDocumentIndex: term="+term+" document="+document);

            // create an index, if term found in document
            if (document.getStrContent().contains(term.getId())) {
                // saveTermDocumentIndex(new TermDocumentIndex(term, document));
                BuildIndexEngineImpl.this.saveTermDocumentIndex(new TermDocumentIndex(term, document));	// illustrate how to call enclosing method
            }
        }

        public void run() {
            log.info("******************************* RUN  ***********************************************");
            log.info("HandleCreateTermDocumentIndexTask.run: processing indexRequest="+indexRequest);
            log.info("******************************* RUN  ***********************************************");

            if (document != null) {
                log.info("HandleCreateTermDocumentIndexTask.run: document="+document+" not null");
                createTermDocumentIndex(indexRequest.getTerm(), (StringDocument) document);
            } else {
                // List<StringDocument> documents = findStringDocuments();
                List<StringDocument> documents = BuildIndexEngineImpl.this.findStringDocuments();	// illustrate how to call enclosing method
                log.info("HandleCreateTermDocumentIndexTask.run: documents="+documents);

                for (int i=0;i<documents.size();i++) {
                    if (documents.get(i) instanceof StringDocument) {
                        createTermDocumentIndex(indexRequest.getTerm(), (StringDocument) documents.get(i));
                    }
                }
            }
            // remove term index request
            log.info("HandleCreateTermDocumentIndexTask.run: delete indexRequest="+indexRequest);
            // deleteIndexRequest(indexRequest);
            BuildIndexEngineImpl.this.deleteIndexRequest(indexRequest);	// illustrate how to call enclosing method
        }
    }
}