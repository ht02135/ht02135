package com.hung.auction.engine;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;

import com.hung.auction.domain.AbstractIndexRequest;
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

    /* Want to do if I have time:
       1>for BuildIndexEngineImpl to keep collection of task thread.  the task thread should be removed from collection
         upon completion of task thread.
       2>task thread added to database should be marked as REQUEST_PROCESSED column = YES <- soft delete
       3>add ThreadPoolTaskScheduler to start unfinished task (need to check if task already added to collection of task thread)
         and delete soft delete task.
       4>Add rule engine to process request using DROOLS
       5>do optimize term search in collection of document
     */

    private static Logger log = Logger.getLogger(BuildIndexEngineImpl.class);

    // @Autowired tells Spring to find a bean of the declared type and wire in that bean
    // @Autowired annotation to auto wire bean on the setter method, constructor or a field

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

    public void handleIndexRequest(TermIndexRequest indexRequest) {
        taskExecutor.execute(new HandleCreateTermDocumentIndexTask(indexRequest));
    }

    public void handleIndexRequest(TermIndexRequest indexRequest, StringDocument document) {
        taskExecutor.execute(new HandleCreateTermDocumentIndexTask(indexRequest, document));
    }

    public void handleIndexRequest(DocumentIndexRequest indexRequest) {
        if (indexRequest.getDocumentAction().equalsIgnoreCase(DocumentIndexRequest.DELETE_DOCUMENT_INDEX)) {
            // if is delete document index action, then search thru all index and then remove them
            List<TermDocumentIndex> termDocumentIndexes = termDocumentIndexService.findByDocumentId(indexRequest.getDocument().getId());
            for (int i=0;i<termDocumentIndexes.size();i++) {
                termDocumentIndexService.deleteById(termDocumentIndexes.get(i).getId());
            }
        } else if (indexRequest.getDocumentAction().equalsIgnoreCase(DocumentIndexRequest.ADD_DOCUMENT_INDEX)) {
            // get all terms from termService
            List<Term> terms = termService.findAll();
            // loop thru terms and launch HandleCreateTermDocumentIndexTask
            for (int i=0;i<terms.size();i++) {
                TermIndexRequest termIndexRequest = new TermIndexRequest(terms.get(i));
                indexRequestService.save(termIndexRequest);
                handleIndexRequest(termIndexRequest, indexRequest.getDocument());
            }
        }
        // remove document index request
        softDeleteIndexRequest(indexRequest);
    }

    // ------------------ Scheduler method

    public void handleCompletedIndexRequest() {
        log.info("handleCompletedIndexRequest: start");
        List<AbstractIndexRequest> completedRequests = indexRequestService.findAll(AbstractIndexRequest.PROCESSED_STATUS);

        if (completedRequests.size() > 0) log.info("handleCompletedIndexRequest: completedRequests="+completedRequests);

        for (int i=0;i<completedRequests.size();i++) {
            log.info("handleCompletedIndexRequest: hard-delete completedRequest="+completedRequests.indexOf(i));
            indexRequestService.deleteById(completedRequests.get(i).getId());
        }
        log.info("handleCompletedIndexRequest: end");
    }

    // ------------------ Helper Method for Inner Class Task

    private void softDeleteIndexRequest(AbstractIndexRequest indexRequest) {
        indexRequestService.softDeleteById(indexRequest.getId());
    }

    private List<StringDocument> findStringDocuments() {
        return documentService.findStringDocuments();
    }

    private void saveTermDocumentIndex(TermDocumentIndex termDocumentIndex) {
        termDocumentIndexService.save(termDocumentIndex);
    }

    // ------------------ Injection Method ---------------------------------

    public void setTaskExecutor(TaskExecutor taskExecutor) {
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
            // create an index, if term found in document
            if (document.getStrContent().contains(term.getId())) {
                // saveTermDocumentIndex(new TermDocumentIndex(term, document));
                BuildIndexEngineImpl.this.saveTermDocumentIndex(new TermDocumentIndex(term, document));	// illustrate how to call enclosing method
            }
        }

        public void run() {
            if (document != null) {
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
            // deleteIndexRequest(indexRequest);
            BuildIndexEngineImpl.this.softDeleteIndexRequest(indexRequest);	// illustrate how to call enclosing method
        }
    }
}