
package com.Manoj.framework.search;

import java.util.Map;

public class SearchMap {

    private Map<String, String[]> requestMap;
    private PaginationData pagination;
    private SearchOrder order;

    public SearchMap() {
        
    }
    
    public SearchMap(Map requestMap) {
        this.requestMap = requestMap;
    }

    public SearchMap(Map<String, String[]> requestMap, PaginationData pagination, SearchOrder order) {
        this(requestMap);
        this.pagination = pagination;
        this.order = order;
    }

    public Map<String, String[]> getRequestMap() {
        return requestMap;
    }

    public void setRequestMap(Map<String, String[]> requestMap) {
        this.requestMap = requestMap;
    }

    public PaginationData getPagination() {
        return pagination;
    }

    public void setPagination(PaginationData pagination) {
        this.pagination = pagination;
    }
    
    public SearchOrder getOrder() {
        return order;
    }

    public void setOrder(SearchOrder order) {
        this.order = order;
    }
    
    public boolean isPaginatable() {
        if(pagination == null)
            return false;
        
        return (pagination.getLimit()>0);
    }
    
    public boolean isSearchable() {
        if(requestMap == null)
            return false;
        
        return (requestMap.size() > 0);
    }
}
