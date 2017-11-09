package com.alphaprogrammer.InformationRetrieval;

/**
 * Created by younes on 6/11/17.
 */
public interface TextSearchIndex {
    SearchResultBatch search(String searchTerm, int maxResults);

    int numDocuments();

    int termCount();
}
