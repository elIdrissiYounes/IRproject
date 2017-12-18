package com.alphaprogrammer.InformationRetrieval;

/**
 * Created by younes on 6/11/17.
 */
public class Document {

    private String rawText;
    private Object uniqueIdentifier;
    private String path;

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
     *
     * @param rawText - the raw text for this document.
     * @param uniqueIdentifier - (optional) a unique ID for this document. Used in search results to refer back
     *                         to original data
     */
    public Document(String rawText, Object uniqueIdentifier) {
        this.rawText = rawText;
        this.uniqueIdentifier = uniqueIdentifier;
    }
    public Document(String rawText, Object uniqueIdentifier,String path) {
        this.rawText = rawText;
        this.uniqueIdentifier = uniqueIdentifier;
        this.path=path;
    }

    public String getRawText() {
        return rawText;
    }

    public Object getUniqueIdentifier() {
        return uniqueIdentifier;
    }
}
