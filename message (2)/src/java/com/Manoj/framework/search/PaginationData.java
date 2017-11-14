

package com.Manoj.framework.search;


public class PaginationData {

    int startNumber;
    int limit;

    public PaginationData(int startNumber, int limit) {
        this.startNumber = startNumber;
        this.limit = limit;
    }
    
    public PaginationData() {
        
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    
}