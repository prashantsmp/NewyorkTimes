package com.nytimes.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("num_results")
    private int numOfResults;

    @SerializedName("results")
    private List<Article> results;

    public ArticleResponse(String status, int num_results, List<Article> results) {
        this.status = status;
        this.numOfResults = num_results;
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumOfResults() {
        return numOfResults;
    }

    public void setNumOfResults(int numOfResults) {
        this.numOfResults = numOfResults;
    }

    public List<Article> getResults() {
        return results;
    }

    public void setResults(List<Article> results) {
        this.results = results;
    }
}
