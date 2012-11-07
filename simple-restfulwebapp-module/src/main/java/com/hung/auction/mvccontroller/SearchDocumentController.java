package com.hung.auction.mvccontroller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hung.auction.client.ClientSearchResult;
import com.hung.auction.domain.StringDocument;
import com.hung.auction.service.DocumentService;
import com.hung.auction.service.SearchTermService;

@Controller
@RequestMapping("/searchdocuments")
public class SearchDocumentController implements IAuthenticateController {

    private static Logger log = Logger.getLogger(SearchDocumentController.class);

    public static final String TEXT_TYPE = "text";

    @Autowired
    @Qualifier("documentService")
    private DocumentService documentService;	// dont need setXXX method when autowire

    @Autowired
    @Qualifier("searchTermService")
    private SearchTermService searchTermService;

    // display list of documents
    @RequestMapping(method=RequestMethod.GET)
    public String displayDocuments(Model model) {
        log.info("displayDocuments: enter");

        List<StringDocument> documents = documentService.findStringDocuments();
        ClientSearchResult clientSearchResult = new ClientSearchResult(documents, ClientSearchResult.OK_STATUS);
        model.addAttribute("clientSearchResult", clientSearchResult);

        return "searchdocuments/list";
    }

    @RequestMapping(value = "/search", method=RequestMethod.POST)
    public String searchDocuments(Model model, @RequestParam("term") String term) {
        log.info("displayDocuments: enter");

        ClientSearchResult clientSearchResult = searchTermService.findByTerm(term);
        model.addAttribute("clientSearchResult", clientSearchResult);

        return "searchdocuments/list";
    }
}
