package com.alphaprogrammer.InformationRetrieval.engine;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by younes on 6/11/17.
 */
public class ParsedDocumentMetrics {
    private Corpus corpus;
    private ParsedDocument document;
    private ImmutableMap<String, DocumentPostingCollection> termsToPostings;

    //metrics
    private Double magnitude;
    private ImmutableMap<String, Double> tfidfCache;
    private ImmutableMap<String, Double> tfidfLtcCache;

    public ParsedDocumentMetrics(Corpus corpus, ParsedDocument document,
                                 ImmutableMap<String, DocumentPostingCollection> termsToPostings) {
        this.corpus = corpus;
        this.document = document;
        this.termsToPostings = termsToPostings;
        Map<String, Double> tfm = new HashMap<>();
        Map<String, Double> tfml = new HashMap<>();

        //init tfidf cache
        for (String word : document.getUniqueWords()) {
            tfm.put(word, calcTfidf(word));
            //pour les ltc
            tfml.put(word, calcLtc(word));
        }
        this.tfidfCache = ImmutableMap.copyOf(tfm);
        this.tfidfLtcCache=ImmutableMap.copyOf(tfml);
        //prime magnitude cache
        getMagnitude();
    }

    public double getTfidf(String word) {
        Double retVal = tfidfCache.get(word);
        if (retVal == null) {
            return 0;
        }

        return retVal;
    }
    public double getTfidfLtc(String word) {
        Double retVal = tfidfLtcCache.get(word);
        if (retVal == null) {
            return 0;
        }

        return retVal;
    }

    public double getMagnitude() {
        if (magnitude == null) {
            double sumOfSquares = 0;
            for (String word : document.getUniqueWords()) {
                double d = getTfidf(word);
                sumOfSquares += d * d;
            }

            magnitude = Math.sqrt(sumOfSquares);
        }

        return magnitude;
    }

    public ParsedDocument getDocument() {
        return this.document;
    }

    private double calcTfidf(String word) {
        int wordFreq = document.getWordFrequency(word);
        if (wordFreq == 0) {
            return 0;
        }
        return getInverseDocumentFrequency(word) * document.getWordFrequency(word);
    }
    private double calcLtc(String word) {
        int wordFreq = document.getWordFrequency(word);
        if (wordFreq == 0) {
            return 0;
        }
        return getInverseDocumentFrequency(word) *(1+ Math.log(document.getWordFrequency(word)));
    }

    private double getInverseDocumentFrequency(String word) {
        double totalNumDocuments = corpus.size();
        double numDocsWithTerm = numDocumentsTermIsIn(word);
        return Math.log((totalNumDocuments) / (1 + numDocsWithTerm));
    }

    private int numDocumentsTermIsIn(String term) {
        if (!termsToPostings.containsKey(term)) {
            return 0;
        }

        return termsToPostings.get(term).getUniqueDocuments().size();
    }

}
