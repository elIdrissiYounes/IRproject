package com.alphaprogrammer.InformationRetrieval;

/**
 * Created by younes on 6/11/17.
 */
public class SearchResult {
	private double ltn;
	private double ltc;
	private double bm25;

	private double relevanceScoreCosine;
    private Object uniqueIdentifier;


	public double getBm25() {
		return bm25;
	}

	public void setBm25(double bm25) {
		this.bm25 = bm25;
	}

	public double getLtc() {
		return ltc;
	}

	public void setLtc(double ltc) {
		this.ltc = ltc;
	}
	public double getLtn() {
		return ltn;
	}

	public void setLtn(double ltn) {
		this.ltn = ltn;
	}

	


    public Object getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(Object uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public double getRelevanceScoreCosine() {
        return relevanceScoreCosine;
    }

    public void setRelevanceScoreCosine(double relevanceScoreCosine) {
        this.relevanceScoreCosine = relevanceScoreCosine;
    }
}
