package com.nooheat.support;

/**
 * Created by NooHeat on 09/07/2017.
 */
public class DocumentResource implements Comparable<DocumentResource> {
    public String uri;
    public String httpMethod;
    public String category;
    public String summary;
    public String params;
    public String requestBody;
    public String response;
    public String successCode;
    public String failureCode;
    public String etc;

    public DocumentResource(String category, String summary, String uri, String httpMethod, String params,
                            String requestBody, String resposne, String successCode, String failureCode, String etc) {

        this.category = category;
        this.summary = summary;
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.params = params;
        this.requestBody = requestBody;
        this.response = resposne;
        this.successCode = successCode;
        this.failureCode = failureCode;
        this.etc = etc;
    }

    @Override
    public int compareTo(DocumentResource o) {
        return this.category.compareTo(o.category);
    }
}
