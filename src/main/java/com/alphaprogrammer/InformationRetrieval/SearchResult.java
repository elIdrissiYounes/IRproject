package com.alphaprogrammer.InformationRetrieval;

/**
 * Created by younes on 6/11/17.
 */
public class SearchResult {
	private double ltn;
	private double ltc;

	private double relevanceScore;
    private Object uniqueIdentifier;


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

    public double getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(double relevanceScore) {
        this.relevanceScore = relevanceScore;
    }
}
