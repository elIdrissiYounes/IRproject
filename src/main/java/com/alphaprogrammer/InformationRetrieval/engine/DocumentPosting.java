package com.alphaprogrammer.InformationRetrieval.engine;

/**
 * Created by younes on 6/11/17.
 */
public class DocumentPosting {

    private DocumentTerm documentTerm;
    private ParsedDocument parsedDocument;
    public DocumentPosting(DocumentTerm documentTerm, ParsedDocument document) {
        this.documentTerm = documentTerm;
        this.parsedDocument = document;
    }

    public DocumentTerm getDocumentTerm() {
        return documentTerm;
    }

    public ParsedDocument getParsedDocument() {
        return parsedDocument;
    }

}
