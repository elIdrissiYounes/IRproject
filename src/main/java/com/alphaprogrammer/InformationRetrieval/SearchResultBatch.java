package com.alphaprogrammer.InformationRetrieval;

import java.util.List;

/**
 * Created by younes on 6/11/17.
 */
public class SearchResultBatch {
    private List<SearchResult> searchResults;
    private SearchStats stats;

    public SearchStats getStats() {
        return stats;
    }

    public void setStats(SearchStats stats) {
        this.stats = stats;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }
}
