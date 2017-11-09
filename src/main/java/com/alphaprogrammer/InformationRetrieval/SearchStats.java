package com.alphaprogrammer.InformationRetrieval;

/**
 * Created by younes on 6/11/17.
 */
public class SearchStats {

    private int documentsSearched;
    private long durationNanos;

    public int getDocumentsSearched() {
        return documentsSearched;
    }

    public void setDocumentsSearched(int documentsSearched) {
        this.documentsSearched = documentsSearched;
    }

    public long getDurationNanos() {
        return durationNanos;
    }

    public void setDurationNanos(long durationNanos) {
        this.durationNanos = durationNanos;
    }

}
