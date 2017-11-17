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
    private ImmutableMap<String, Double> tfidfBm25Cache;

    public ParsedDocumentMetrics(Corpus corpus, ParsedDocument document,
                                 ImmutableMap<String, DocumentPostingCollection> termsToPostings) {
        this.corpus = corpus;
        this.document = document;
        this.termsToPostings = termsToPostings;
        Map<String, Double> tfm = new HashMap<>();
        Map<String, Double> tfml = new HashMap<>();
        Map<String, Double> tfmTfBm25 = new HashMap<>();

        //init tfidf cache
        for (String word : document.getUniqueWords()) {
            tfm.put(word, calcTfidf(word));
            //pour les ltc
            tfml.put(word, calcLtc(word));
            tfmTfBm25.put(word,calcBM(word));
            
        }
        this.tfidfCache = ImmutableMap.copyOf(tfm);
        this.tfidfLtcCache=ImmutableMap.copyOf(tfml);
        this.tfidfBm25Cache=ImmutableMap.copyOf(tfmTfBm25);
        //prime magnitude cache
        getMagnitude();
    }

    private double calcBM(String word) { 
    	 double k1 = 1.2;
    	 double b = 0.7;

    	 int wordFreq = document.getWordFrequency(word);
         if (wordFreq == 0) {
             return 0;
         }
         return getInverseDocumentFrequency(word) * ( (document.getWordFrequency(word)*(k1+1)) / (document.getWordFrequency(word) + k1 * (1-b+b * ((corpus.size()/ (termsToPostings.keySet().size() / corpus.size())) ) )));
     
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

    public double getBm25(String word) {
        Double retVal = tfidfBm25Cache.get(word);
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
        return Math.log((totalNumDocuments) / (numDocsWithTerm));
    }

    private int numDocumentsTermIsIn(String term) {
        if (!termsToPostings.containsKey(term)) {
            return 0;
        }

        return termsToPostings.get(term).getUniqueDocuments().size();
    }

}
