

package com.Manoj.framework.utilities;

import java.util.List;


public class ResultList implements Result{

    private List list;
    private long filteredCount;
    private long fullCount;

    public ResultList() {
    }

    public ResultList(List list) {
        this.list = list;
    }

    public ResultList(List list, long filteredCount) {
        this.list = list;
        this.filteredCount = filteredCount;
    }
    
    public ResultList(List list, long filteredCount, long fullCount) {
        this.list = list;
        this.filteredCount = filteredCount;
        this.fullCount = fullCount;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public long getFilteredCount() {
        return filteredCount;
    }

    public void setFilteredCount(long filteredCount) {
        this.filteredCount = filteredCount;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
    
}
