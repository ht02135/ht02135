package com.hung.auction.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hung.auction.domain.AbstractDocument;
import com.hung.auction.domain.BinaryDocument;
import com.hung.auction.domain.StringDocument;
import com.hung.auction.service.DocumentService;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private static Logger log = Logger.getLogger(DocumentController.class);

    public static final String TEXT_TYPE = "text";

    @Autowired
    @Qualifier("documentService")
    private DocumentService documentService;	// dont need setXXX method when autowire

    // display list of documents
    @RequestMapping(method=RequestMethod.GET)
    public String displayDocuments(Model model) {
        log.info("displayDocuments: enter");

        // debug domainUsers
        model.addAttribute("document", new BinaryDocument());

        List<AbstractDocument> documents = documentService.findAll();
        log.info("displayDocuments: documents="+documents);
        model.addAttribute("documents", documents);

        return "documents/list";
    }

    // probably should use /upload, must intuitive than /save
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("document") BinaryDocument document, @RequestParam("file") MultipartFile file) {
        log.info("save: enter");

        log.info("save: file.getName()=" + file.getName());
        log.info("save: file.getContentType()=" + file.getContentType());

        AbstractDocument tobeUploadDocument = null;
        if (file.getContentType().contains(TEXT_TYPE)) {
            tobeUploadDocument = new StringDocument();
        } else {
            tobeUploadDocument = new BinaryDocument();
        }
        tobeUploadDocument.setName(document.getName());
        tobeUploadDocument.setFileName(file.getOriginalFilename());
        tobeUploadDocument.setContentType(file.getContentType());

        try {
            InputStream in = file.getInputStream();
            tobeUploadDocument.uploadFrom(in);
            in.close();
        } catch (IOException e) {
            log.info("save: e="+e.toString());
        } catch(Exception e) {
            log.info("save: e="+e.toString());
        }

        documentService.save(tobeUploadDocument);

        return "redirect:/auction/documents";
    }

    // download an document.  params=download is passed by ?download
    @RequestMapping(value="/{documentId}", params="download", method=RequestMethod.GET)
    public String downloadFromForm(@PathVariable("documentId") Integer documentId, HttpServletResponse response) {
        log.info("downloadFromForm: enter");

        AbstractDocument document = documentService.findById(documentId);
        response.setHeader("Content-Disposition","attachment; filename=\""+document.getFileName()+"\"");
        response.setContentType(document.getContentType());

        try {
            OutputStream out = response.getOutputStream();
            document.downloadTo(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            log.info("downloadFromForm: e="+e.toString());
        }

        return null;
    }

    // download an document.  params=remove is passed by ?remove
    @RequestMapping(value="/{documentId}", params="remove", method=RequestMethod.GET)
    public String removeFromForm(@PathVariable("documentId") Integer documentId) {
        log.info("removeFromForm: enter");

        documentService.deleteById(documentId);
        return "redirect:/auction/documents";
    }
}
