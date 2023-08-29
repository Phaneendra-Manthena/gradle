package com.bhargo.user.Java_Beans;

public class SearchItems implements Comparable {

    String searchResult;
    int percentage;
    int postions;

    public SearchItems(int percentage, String searchResult,int postions) {
        this.percentage = percentage;
        this.searchResult = searchResult;
        this.postions=postions;
    }

    public String getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(String searchResult) {
        this.searchResult = searchResult;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getPostions() {
        return postions;
    }

    public void setPostions(int postions) {
        this.postions = postions;
    }

    @Override
    public String toString() {
        return "[ percentage=" + percentage + ", SearchResult Title=" + searchResult + "]";
    }

    @Override
    public int compareTo(Object o) {
        int compareage = ((SearchItems) o).getPercentage();
        /* For Ascending order*/
        // return this.percentage - compareage;

        /* For Descending order do like this */
        return compareage-this.percentage;
    }
}
